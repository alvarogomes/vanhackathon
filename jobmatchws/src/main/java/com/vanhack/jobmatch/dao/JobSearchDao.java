package com.vanhack.jobmatch.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Query;

import org.jsoup.Jsoup;

import com.vanhack.jobmatch.controller.vo.JobVO;
import com.vanhack.jobmatch.controller.vo.MatchVO;
import com.vanhack.jobmatch.controller.vo.ServiceReturnVO;
import com.vanhack.jobmatch.controller.vo.SkillVO;
import com.vanhack.jobmatch.persistence.Job;
import com.vanhack.jobmatch.persistence.Jobmatch;
import com.vanhack.jobmatch.persistence.Jobprocess;
import com.vanhack.jobmatch.persistence.Jobskillcalculated;
import com.vanhack.jobmatch.useful.CommonDAO;
import com.vanhack.jobmatch.useful.DateFormatter;

public class JobSearchDao extends CommonDAO<Job>{

	public JobSearchDao() {
		this.set(Job.class);
	}
	public ServiceReturnVO detail(String code) throws Exception {
		super.getEntityManager().clear();
		Job a = super.findById(Integer.valueOf(code));
		List<Job> list = new ArrayList<Job>(); 
		list.add(a);
		
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		s.getListJobs().addAll(this.jobEntityConverterVO(list, true));
		
		return s;
	}
	
	public ServiceReturnVO search(String description) throws Exception {
		super.getEntityManager().clear();
		
		List<Job> list = super.query("Select p from Job p where p.aboutThisRole like '%" + description + "%' order by p.postdate desc ");
		
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		s.getListJobs().addAll(this.jobEntityConverterVO(list, false));
		
		return s;
	}
	
	public ServiceReturnVO index(String codes) {
		
		ServiceReturnVO s = new ServiceReturnVO("1", "Success");
		try {
			super.getEntityManager().getTransaction().begin();
		
			StringTokenizer st = new StringTokenizer(codes,",");
			while ( st.hasMoreElements() ) { 
	    		String code = st.nextToken();
		    	
				Jobprocess j = (Jobprocess) super.findById(Integer.valueOf(code), Jobprocess.class);
				
				if (j == null) {
					
					Job jx = this.findById(Integer.valueOf(code));
					
					Query q = this.getEntityManager().createNativeQuery("INSERT INTO Jobprocess (idjob, status) VALUES ("+
							jx.getIdjob()+ ",1)");
	    			q.executeUpdate();	
				} else {
					
					Query q = this.getEntityManager().createNativeQuery("delete from Jobmatch where idjob =" + j.getIdjob());
	    			q.executeUpdate();	
	    			
					j.setStatus(1);
					this.saveObject(j);
				}
				
			}
			
			super.getEntityManager().getTransaction().commit();
			
		} catch (Exception ex) {
			super.getEntityManager().getTransaction().rollback();
			s.setCode("2");
			s.setDescription(ex.getMessage());
		}
		return s;
	}
	
	
	private List<JobVO> jobEntityConverterVO(List<Job> list, boolean match ) throws Exception  {
		
		List<JobVO> aux = new ArrayList<>();
		
		HashMap<Integer, String> h = new HashMap<>();
		h.put(null, "Pending");
		h.put(1, "Waiting for process");
		h.put(2, "In process");
		h.put(3, "Done");
		h.put(4, "No professional found");
		
		for (Job j: list) {
			JobVO jvo = new JobVO();
			jvo.setCode(String.valueOf( j.getIdjob() ));
			jvo.setCompany(j.getCompany().getName());
			//jvo.setJobtype(j.getJobtype());
			
			jvo.setDescription("");
			jvo.setQualifications("");
			jvo.setResponsabilities("");
			jvo.setJobtype(j.getPosition());
			if (j.getAboutThisRole() != null) jvo.setDescription( Jsoup.parse(j.getAboutThisRole()).body().text());
			if (j.getQualifications() != null) jvo.setQualifications(Jsoup.parse(j.getQualifications()).body().text());
			if (j.getResponsibilities() != null) jvo.setResponsabilities(Jsoup.parse(j.getResponsibilities()).body().text());
			
			jvo.setList(this.skillEntityConverterVO(j.getJobskillcalculateds()));
			if (match)
				jvo.setListMatchs(this.jobmatchEntityConverterVO(j.getJobmatches()));
			
			if (j.getJobprocess() != null) {
				jvo.setStatus(h.get(j.getJobprocess().getStatus()));
			} else {
				jvo.setStatus(h.get(null));
			}
			jvo.setDate(DateFormatter.format(j.getPostdate()));
			aux.add(jvo);
		}
		
		return aux;
	}
	
	private List<SkillVO> skillEntityConverterVO(List<Jobskillcalculated> list) {
		
		List<SkillVO> aux = new ArrayList<>();
		
		for (Jobskillcalculated ps : list) {
			SkillVO svo = new SkillVO();
			svo.setCode(String.valueOf(ps.getSkill().getIdskill()));
			svo.setDescription(ps.getSkill().getSkillname());
			svo.setLevel(String.valueOf(ps.getLevel()));
			
			aux.add(svo);
		}
		return aux;
	}
	
	private List<MatchVO> jobmatchEntityConverterVO(List<Jobmatch> list) throws Exception {
		List<MatchVO> aux = new ArrayList<>();
		ProfessionalDao dao = new ProfessionalDao();
		
		for (Jobmatch jm : list) {
			MatchVO m = new MatchVO();
			String similarity = String.valueOf(jm.getSimilarity() * 100) ;
			
			m.setSimilarity(similarity);
			m.setProfessional(
					dao.professionalEntityConverterVO(Arrays.asList(jm.getUser())).get(0)
			);
			
			aux.add(m);
		}
		
		return aux;
	}
}