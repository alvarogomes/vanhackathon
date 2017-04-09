package com.vanhack.jobmatch.presentation.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ProfessionalVO {

	private String code;
	private String name;
	private String email;
	private String title;
	private Collection<SkillVO> listSkills;
	private Collection<EducationVO> listEducation;
	private Collection<ExperienceVO> listProfessionalExperience;
	private String cv;
	private String status;
	private String description;
	
	public ProfessionalVO() {
		this.listSkills = new ArrayList<>();
		this.listEducation = new ArrayList<>();
		this.listProfessionalExperience = new ArrayList<>();
	}
	
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCv() {
		return cv;
	}


	public void setCv(String cv) {
		this.cv = cv;
	}


	public Collection<EducationVO> getListEducation() {
		return listEducation;
	}


	public void setListEducation(Collection<EducationVO> listEducation) {
		this.listEducation = listEducation;
	}


	public Collection<ExperienceVO> getListProfessionalExperience() {
		return listProfessionalExperience;
	}


	public void setListProfessionalExperience(Collection<ExperienceVO> listProfessionalExperience) {
		this.listProfessionalExperience = listProfessionalExperience;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Collection<SkillVO> getListSkills() {
		return listSkills;
	}
	public void setListSkills(Collection<SkillVO> listSkills) {
		this.listSkills = listSkills;
	}
	
}
