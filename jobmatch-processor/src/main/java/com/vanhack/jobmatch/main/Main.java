package com.vanhack.jobmatch.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.CoordinateMatrix;
import org.apache.spark.mllib.linalg.distributed.MatrixEntry;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.sql.SparkSession;
import org.jsoup.Jsoup;

import com.vanhack.jobmatch.persistence.Job;
import com.vanhack.jobmatch.persistence.Jobmatch;
import com.vanhack.jobmatch.persistence.Jobprocess;

import com.vanhack.jobmatch.persistence.Jobskillcalculated;

import com.vanhack.jobmatch.persistence.Skill;
import com.vanhack.jobmatch.persistence.User;
import com.vanhack.jobmatch.persistence.Userskill;
import com.vanhack.jobmatch.persistence.Userskillcalculated;
import com.vanhack.jobmatch.useful.CommonDAO;
import com.vanhack.jobmatch.useful.EntityManagerHelper;
import com.vanhack.jobmatch.useful.StringUtils;

import scala.Tuple2;

import java.lang.Iterable;
import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		SparkSession spark = SparkSession
				  .builder().appName("App RH") //Nome genérico
				  .master("local")	 //Indica que o Spark está executando localmente (ambiente de desenvolvimento)
				  .getOrCreate();
		
		JavaSparkContext jsc = JavaSparkContext.fromSparkContext(spark.sparkContext());
		
		final Pattern SPACE = Pattern.compile(" ");
		
		
		
		while (true) {
			
			System.out.println("Lets verify the rockstars... ");
			com.vanhack.jobmatch.useful.EntityManagerHelper.getEntityManager().clear();
			Thread.sleep(4000);
			
			CommonDAO<Job> daoJob = new CommonDAO<Job>();
			daoJob.set(Job.class);
			
			CommonDAO<Jobprocess> daoJobProcess = new CommonDAO<Jobprocess>();
			daoJobProcess.set(Jobprocess.class);
			
			CommonDAO<User> daoProfessional = new CommonDAO<User>();
			daoProfessional.set(User.class);
			
			CommonDAO<Skill> daoSkill = new CommonDAO<Skill>();
			daoSkill.set(Skill.class);
			

		    CommonDAO<Jobmatch> daoJobmatch = new CommonDAO<Jobmatch>();
		    daoJobmatch.set(Jobmatch.class);
		    CommonDAO<Jobskillcalculated> daojobskills = new CommonDAO<Jobskillcalculated>();
		    daojobskills.set(Jobskillcalculated.class);
		    
			
			List<Job> list = daoJob.query("Select p from Job p where p.jobprocess.status = 1");
			List<Skill> listSkills = daoSkill.findAll();
			HashMap<String, String> hashSkills = new HashMap<>();
			for (Skill ss: listSkills) {
				hashSkills.put(ss.getSkillname().toLowerCase(), String.valueOf(ss.getIdskill()));
			}
			
			for (Job j: list) {
				//1 - Change the status to "processing..."
				
				EntityManagerHelper.beginTransaction();
				Jobprocess jx = daoJobProcess.findById(j.getIdjob());
				jx.setStatus(2);
				daoJobProcess.save(jx);
				
				jx = null;
				EntityManagerHelper.commit();
				
				//2 - Verifing the Skills that job needs (WordCount)...
				String about = "";
				String resp = "";
				String qualifications = "";
				
				if (j.getAboutThisRole() != null) about = Jsoup.parse(j.getAboutThisRole()).body().text();
				if (j.getResponsibilities() != null) resp = Jsoup.parse(j.getResponsibilities()).body().text();
				if (j.getQualifications() != null) qualifications = Jsoup.parse(j.getQualifications()).body().text();
				
				JavaRDD<String> input = jsc.parallelize(Arrays.asList(
						about,
						resp,
						qualifications), 3);
				JavaRDD<String> words = input.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

			    JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
			    JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
			    List<Tuple2<String, Integer>> output = counts.collect();
			    
			    HashMap<String, Integer> hashSkillsJobOffer = new HashMap<>();
			    
			    Pattern p1 = Pattern.compile("\\d+\\s+(?:years?)"); 
		    	Matcher m = p1.matcher(about + " " + resp + " " + qualifications);
		    	boolean c = false;
		    	
		    	double d3 = 0;
		    	int countExp = 0;
		    	while(m.find()){
		    		String exp = m.group();
		    		exp = exp.replace("years","").replace(" ","").replace("+","");
		    	    //matches.add(m.group());
		    		
		    		if (StringUtils.isNumeric(exp)) {
			    			d3 = d3 + Integer.valueOf(exp);
			    			countExp++;
			    			c = true;
		    		}
		    	}
		    	
			    Integer levelJob = 7;
			    
			    HashMap<String, Integer> qualifyears = new HashMap<String, Integer>(); 
			    if (c) {
			    	Integer avg = (int) (d3/countExp);
			    	levelJob = avg;
			    } else {
			    	qualifyears.put("strong", 5);
			    	qualifyears.put("advanced", 7);
			    	qualifyears.put("expert",9);
			    }
			    
			    for (Tuple2<?,?> tuple : output) {
			    	//Verifiy if the word is a skill
			    	String s = ((String)tuple._1()).toLowerCase();
			    	s = s.replace(",", "").replace("(", "").replace(")", "").trim();
			    	
			    	if (!c) {
			    		if (qualifyears.containsKey(s)) {
			    			levelJob = qualifyears.get(s);
			    		}
			    	}
			    	if (hashSkills.containsKey(s) ) {
			    		hashSkillsJobOffer.put(s, levelJob);
			    	}
			    }
			    
			    List<String> listSkillsJobOffer = new ArrayList<>();
			    List<String> listSkillsJobOfferId = new ArrayList<>();
			    
			    for (String ss: hashSkillsJobOffer.keySet()) {
			    	listSkillsJobOffer.add(ss);
			    	listSkillsJobOfferId.add(hashSkills.get(ss));
			    }
			    listSkillsJobOffer.sort(Comparator.comparing(obj -> obj));
			    
			    //3 - Get just the list of professional that have more than 50% of skills that job needs... (creating the matrix)
			    
			    int count = listSkillsJobOffer.size()/2;
			    
			    String items = listSkillsJobOfferId.toString();
			    
			    items = items.replace("[", "").replace("]","");
			    
			    String sql ="Select p.IDUSER from `user` p, userskillcalculated ps"+
				    	" where ps.IDUSER = p.IDUSER"+
				    	" and ps.IDSKILL in ("+ items +")"+
				    	" group by p.IDUSER"+
				    	" having count(ps.IDSKILL) >= " + count;
			    
			    Query q = com.vanhack.jobmatch.useful.EntityManagerHelper.getEntityManager().createNativeQuery(sql);
			    
			    List<Integer> listPro = q.getResultList();
			    // When we create the matrix... we have to respect the order of each professionals...
			    
			    HashMap<Integer, HashMap<String, Integer>> professionals = new HashMap<>();
			    for (Integer p: listPro) {
			    	User x = daoProfessional.findById(p);
			    	
			    	professionals.put(x.getIduser(), 
			    					  new HashMap<String, Integer>());
			    	
			    	for (Userskillcalculated ps: x.getUserskillcalculateds()) {
			    		
			    		Integer level = 10;
			    		//TODO - DEFINE LEVEL...
			    		level = ps.getLevel();
			    		
			    		professionals.get(x.getIduser()).put(ps.getSkill().getSkillname(), level);
			    	}
			    }
			    
			    List<Vector> listVectors = new ArrayList<>();
			    int totalPositionsArray = professionals.size()+1;
			    
			    List<String> colPro = new ArrayList<String>();
			    
			    for (Integer idPro: professionals.keySet()) {
			    	colPro.add(String.valueOf(idPro));
			    }
			    
			    colPro.sort(Comparator.comparing(obj -> obj));
			  
			    //4 - Calculate the similarity...
			    EntityManagerHelper.beginTransaction();
			    
			    if (colPro.size() > 0 ) {
					    
					    for (String sk: listSkillsJobOffer) {
					    	double[] line = new double[totalPositionsArray];
						    
					    	int countLine = 0;
					    	for (String idPro: colPro) {
					    		
					    		int idPro2 = Integer.valueOf(idPro);
					    		
					    		double v = 0;
					    		if (professionals.get(idPro2).containsKey(sk)) {
					    			v = professionals.get(idPro2).get(sk);
					    		}
					    		line[countLine] = v;
					    		countLine++;
					    	}
					    	//The value about the company needs...
					    	line[countLine] = hashSkillsJobOffer.get(sk);
					    	listVectors.add(Vectors.dense(line));
					    }
					    
					    JavaRDD<Vector> rows = jsc.parallelize(listVectors);
					    RowMatrix matrix = new RowMatrix(rows.rdd());
					    CoordinateMatrix similarities = matrix.columnSimilarities();
					    long colunaVaga = matrix.numCols() - 1;
					    
					    List<MatrixEntry> listEntries = similarities.entries()
		                        .toJavaRDD()
		                        .collect()
		                        .stream()
		                        .filter((MatrixEntry entry) -> entry.j() == colunaVaga)
		                        .sorted((MatrixEntry e1, MatrixEntry e2) -> Double.compare(e2.value(), e1.value()))
		                        .collect(Collectors.toList());
					    
					    
					    for (MatrixEntry entry : listEntries) {
							long i = entry.i();
							double v = entry.value();
							
							String idProfessional = colPro.get((int)i);
							System.out.println(String.format("Candidato %s: %s", idProfessional, v));
							
							Jobmatch jobMatch = new Jobmatch();
							
							Job jz = daoJob.findById(j.getIdjob());
							
							User prof = daoProfessional.findById(Integer.valueOf(idProfessional));
							jobMatch.setUser(prof);
							jobMatch.setJob(jz);
							jobMatch.setSimilarity(v);
							
							daoJobmatch.save(jobMatch);
							jz = null;
							prof = null;
							jobMatch = null;
						}
					    
					    //5 - Save the results in the table...
					    
					    for (String sk: listSkillsJobOffer) {
					    	Integer skillCode = Integer.valueOf(hashSkills.get(sk));
					    	
					    	Job jz = daoJob.findById(j.getIdjob());
					    	Skill skill =  daoSkill.findById(skillCode);
					    	Jobskillcalculated js = new Jobskillcalculated();
					    	js.setSkill(skill);
					    	js.setJob(jz);
					    	js.setLevel(hashSkillsJobOffer.get(sk));
					    	//js.setType(1);
					    	daojobskills.save(js);
					    	
					    	jz = null;
					    	skill = null;
					    	js = null;
					    }
			    
					Jobprocess jy = daoJobProcess.findById(j.getIdjob());
					jy.setStatus(3);
					daoJobProcess.save(jy);
					jy = null;
					
			    } else {
			    	Jobprocess jy = daoJobProcess.findById(j.getIdjob());
					jy.setStatus(4);
					daoJobProcess.save(jy);
					
					jy = null;
			    }
				//6 - Change the status to "Done."
			    
				EntityManagerHelper.commit();
				
			}
			
			System.out.println("We process ("+list.size()+") job offers... ");
			
		}
		
	}

}
