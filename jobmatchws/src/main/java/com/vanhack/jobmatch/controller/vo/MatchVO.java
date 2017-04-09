package com.vanhack.jobmatch.controller.vo;

public class MatchVO {

	private ProfessionalVO professional;
	private String similarity;
	public ProfessionalVO getProfessional() {
		return professional;
	}
	public void setProfessional(ProfessionalVO professional) {
		this.professional = professional;
	}
	public String getSimilarity() {
		return similarity;
	}
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}
	
	
}
