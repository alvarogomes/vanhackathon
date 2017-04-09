package com.vanhack.jobmatch.service;

import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;

import com.vanhack.jobmatch.infra.common.Settings;
import com.vanhack.jobmatch.presentation.vo.JobVO;
import com.vanhack.jobmatch.presentation.vo.LoginVO;
import com.vanhack.jobmatch.presentation.vo.ProfessionalVO;
import com.vanhack.jobmatch.presentation.vo.ServiceReturnVO;

public class JobMatchService {

	
	public JobVO detailJob(JobVO job) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = Jsoup.connect(Settings.URL_BACKEND + "job/detail/"+job.getCode()+"/")
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r.getListJobs().get(0);
	}
	
	public Collection<JobVO> searchJobs(JobVO job) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = Jsoup.connect(Settings.URL_BACKEND + "job/search/"+job.getDescription()+"/")
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r.getListJobs();
	}
	
	public Collection<ProfessionalVO> searchProfessionals(ProfessionalVO pro) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = Jsoup.connect(Settings.URL_BACKEND + "professional/search/"+pro.getName()+"/")
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r.getListProfessionals();
	}
	
	public void indexJob(String jobs) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String json = Jsoup.connect(Settings.URL_BACKEND + "job/index/"+jobs)
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.post().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
	}
	
	
	public ServiceReturnVO login(LoginVO login) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String url = Settings.URL_BACKEND + "login/auth/"+login.getLogin() + "/"+login.getPassword();
		String json = Jsoup.connect(url)
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r;
		
	}
	
	public ServiceReturnVO detectSkills(String codigos) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String url = Settings.URL_BACKEND + "professional/detectskills/"+codigos;
		String json = Jsoup.connect(url)
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r;
		
	}
	
	public ServiceReturnVO findCVs(String codigos) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();
		String url = Settings.URL_BACKEND + "professional/professionalcvs/"+codigos;
		String json = Jsoup.connect(url)
		.userAgent("Mozilla").timeout(10000)
		.ignoreContentType(true)
		.get().body().text();
		
		ServiceReturnVO r = jsonMapper.readValue(json, ServiceReturnVO.class);
		
		return r;
	}
}
