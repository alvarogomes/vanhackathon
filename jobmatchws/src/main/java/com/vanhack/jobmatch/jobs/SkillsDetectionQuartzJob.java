package com.vanhack.jobmatch.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.vanhack.jobmatch.persistence.Jobexperience;
import com.vanhack.jobmatch.persistence.Skill;
import com.vanhack.jobmatch.persistence.Userprocess;
import com.vanhack.jobmatch.persistence.Userskill;
import com.vanhack.jobmatch.persistence.Userskillcalculated;
import com.vanhack.jobmatch.useful.CommonDAO;
import com.vanhack.jobmatch.useful.DateFormatter;
import com.vanhack.jobmatch.useful.EntityManagerHelper;
import com.vanhack.jobmatch.useful.StringUtils;

public class SkillsDetectionQuartzJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    System.out.println("Skills detection... executing...");
	    
	    EntityManagerHelper.getEntityManager().clear();
	    
	    CommonDAO<Userprocess> daoUserProcess = new CommonDAO<Userprocess>();
	    daoUserProcess.set(Userprocess.class);
	    CommonDAO<Skill> daoSkills = new CommonDAO<Skill>();
	    daoSkills.set(Skill.class);
	    
	    List<Skill> listSkills = daoSkills.findAll();
	    
	    HashMap<String, Integer> skills = new HashMap<String, Integer>();
	    
	    for (Skill s: listSkills) {
	    	skills.put(s.getSkillname().toLowerCase(), s.getIdskill());
	    }
	    
	    EntityManagerHelper.getEntityManager().clear();
	    List<Userprocess> list  = daoUserProcess.query("Select p from Userprocess p where p.status = 1");
	    
	    for (Userprocess up: list) {
	    	
	    	EntityManagerHelper.beginTransaction();
	    	Userprocess x = daoUserProcess.findById(up.getIduser());
	    	x.setStatus(2);
	    	daoUserProcess.save(x);
	    	EntityManagerHelper.commit();
	    	
	    	EntityManagerHelper.beginTransaction();
	    	
	    	HashMap<String, Integer> words = new HashMap<String, Integer>();
	    	
	    	String expression = "";
	    	
	    	//Time of experience...
	    	Integer time = 0;
	    	int count = 0;
	    	double d = 0;
	    	if (up.getUser().getBio() != null) expression = expression + up.getUser().getBio() + " | ";
	    	if (up.getUser().getSkills() != null) expression = expression + up.getUser().getSkills() + " | ";
	    	// Getting time and description about job experiences...
	    	for (Jobexperience je: up.getUser().getJobexperiences()) {
	    		
	    		Date yearBegin = up.getDtend();
	    		Date yearEnd = up.getDtend();
	    		
	    		if ((yearBegin != null ) || (yearEnd != null)) {
	    			if (yearEnd  == null) {
	    				yearEnd = new Date();
	    			}
	    			d = d + DateFormatter.getDiffYears(yearBegin, yearEnd);
	    		} else {
	    			d = d + 1.5;
	    		}
	    		if (je.getDescription() != null) expression = expression + je.getDescription() + " | ";
	    	}
	    	count++;
	    	
	    	
	    	double d2 = 0;
	    	double d3 = 0;
	    	if (up.getUser().getYearsOfExperience() > time) {
	    		d2 = up.getUser().getYearsOfExperience();
	    		count++;
	    	}
	    	
	    	//Getting years of experience in expression...
	    	Pattern p = Pattern.compile("\\d+\\s+(?:years?)"); 
	    	Matcher m = p.matcher(expression);
	    	List<String> matches = new ArrayList<String>();
	    	boolean c = false;
	    	while(m.find()){
	    		String exp = m.group();
	    		exp = exp.replace("years","").replace(" ","").replace("+","");
	    	    //matches.add(m.group());
	    		
	    		if (StringUtils.isNumeric(exp)) {
		    		if (Integer.valueOf(exp) > d3) {
		    			//put some metric about developer saying...
		    			d3 =Integer.valueOf(exp) / 1.2;
		    			c = true;
		    		}
	    		}
	    	}
	    	if (c) { count++; }
	    	
	    	
	    	double d4 = up.getUser().getUserskills().size() * 0.2;
	    	count++;
	    	
	    	//AVG...
	    	time = ((int)(d + d2+ d3 + d4)) / count;
	    	
	    	if (time > 10) {
	    		time = 10;
	    	}
	    	
	    	//Minimum = 3...
	    	if (time == 0) {
	    		time = 3;
	    	}
	    	
	    	StringTokenizer st = new StringTokenizer(expression.toLowerCase());
	    	while ( st.hasMoreElements() ) { 
	    		String actualElement = st.nextToken(); 
	    		actualElement = actualElement.replace(",", "").replace("(", "").replace(")", "").trim();
	    		if (skills.containsKey(actualElement)) {
	    			words.put(actualElement, time); 
	    		}
	    	}
	    	
	    	//Get skills selected by own user...
	    	for(Userskill aa: up.getUser().getUserskills()) {
	    		
	    		Integer score = time;
	    		if (aa.getYearsofexperience() != null) {
	    			if (score != aa.getYearsofexperience()) {
	    				if (aa.getYearsofexperience() >0 )
	    					score = aa.getYearsofexperience();
	    			}
	    		}
	    		words.put(aa.getSkill().getSkillname(), score);
	    	}
	    	
	    	//Insert user skills...
	    	CommonDAO<Userskillcalculated> daoUserSkillCalc = new CommonDAO<>();
	    	daoUserSkillCalc.set(Userskillcalculated.class);
	    	
	    	for (String key: words.keySet()) {
	    		
	    		if (skills.containsKey(key)) {
		    		Skill sx = daoSkills.findById(skills.get(key));
		    		//TODO - ADICIONAR AUTOINCREMENTO NA TABELA
		    		Userskillcalculated us = new Userskillcalculated();
		    		us.setLevel(time);
		    		us.setSkill(sx);
		    		us.setUser(up.getUser());
		    		
		    		//If find skill
		    		daoUserSkillCalc.save(us);
	    		}
	    	}
	    	
	    	Userprocess z = daoUserProcess.findById(up.getIduser());
	    	z.setStatus(3);
	    	daoUserProcess.save(z);
	    	
	    	EntityManagerHelper.commit();
	    }
	    System.out.println("Skills detection... Done ...");
	}
}
