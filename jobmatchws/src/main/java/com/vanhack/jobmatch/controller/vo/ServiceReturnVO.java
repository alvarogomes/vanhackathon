package com.vanhack.jobmatch.controller.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ServiceReturnVO {
	
	private String code;
	private String description;
	private Collection<JobVO> listJobs;
	private Collection<ProfessionalVO> listProfessionals;
	
	public ServiceReturnVO(String code, String description) {
		super();
		this.code = code;
		this.description = description;
		this.listJobs = new ArrayList<JobVO>();
		this.listProfessionals = new ArrayList<>(); 
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Collection<JobVO> getListJobs() {
		return listJobs;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<ProfessionalVO> getListProfessionals() {
		return listProfessionals;
	}
	
}
