package com.vanhack.jobmatch.presentation.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceReturnVO {
	
	private String code;
	private String description;
	private List<JobVO> listJobs;
	private List<ProfessionalVO> listProfessionals;
	
	public ServiceReturnVO() {
		super();
		this.listJobs = new ArrayList<JobVO>();
		this.listProfessionals = new ArrayList<>();
	}
	
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

	public List<JobVO> getListJobs() {
		return listJobs;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProfessionalVO> getListProfessionals() {
		return listProfessionals;
	}
	
}
