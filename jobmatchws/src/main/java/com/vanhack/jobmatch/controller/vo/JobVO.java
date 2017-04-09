package com.vanhack.jobmatch.controller.vo;

import java.util.ArrayList;
import java.util.Collection;

public class JobVO {

	private String code;
	private String description;
	private String company;
	private String jobtype;
	private String responsabilities;
	private String qualifications;
	private Collection<SkillVO> list;
	private Collection<MatchVO> listMatchs;
	private String status;
	private String date;
	
	public JobVO() {
		this.list = new ArrayList<SkillVO>();
		this.listMatchs= new ArrayList<MatchVO>();
	}

	
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(String responsabilities) {
		this.responsabilities = responsabilities;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public Collection<SkillVO> getList() {
		return list;
	}

	public void setList(Collection<SkillVO> list) {
		this.list = list;
	}

	public Collection<MatchVO> getListMatchs() {
		return listMatchs;
	}

	public void setListMatchs(Collection<MatchVO> listMatchs) {
		this.listMatchs = listMatchs;
	}
	
	
	
}
