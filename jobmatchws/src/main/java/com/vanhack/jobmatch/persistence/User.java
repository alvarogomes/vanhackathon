package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer iduser;

	@Lob
	private String bio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date bornDate;

	private String currentEmployer;

	private byte cvchecked;

	private String email;

	private BigDecimal englishLevel;

	private String firstname;

	private String gitHub;

	private String lastname;

	private String linkedIn;

	private String position;

	private Integer principalskill;

	private String skills;

	private String webSite;

	private Integer yearsOfExperience;

	//bi-directional one-to-one association to Authentication
	@OneToOne(mappedBy="user")
	private Authentication authentication;

	//bi-directional many-to-one association to Education
	@OneToMany(mappedBy="user")
	private List<Education> educations;

	//bi-directional many-to-one association to Jobbyuser
	@OneToMany(mappedBy="user")
	private List<Jobbyuser> jobbyusers;

	//bi-directional many-to-one association to Jobexperience
	@OneToMany(mappedBy="user")
	private List<Jobexperience> jobexperiences;

	//bi-directional many-to-one association to Jobmatch
	@OneToMany(mappedBy="user")
	private List<Jobmatch> jobmatches;

	//bi-directional one-to-one association to Userprocess
	@OneToOne(mappedBy="user")
	private Userprocess userprocess;

	//bi-directional many-to-one association to Userskill
	@OneToMany(mappedBy="user")
	private List<Userskill> userskills;

	//bi-directional many-to-one association to Userskillcalculated
	@OneToMany(mappedBy="user")
	private List<Userskillcalculated> userskillcalculateds;

	public User() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Date getBornDate() {
		return this.bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getCurrentEmployer() {
		return this.currentEmployer;
	}

	public void setCurrentEmployer(String currentEmployer) {
		this.currentEmployer = currentEmployer;
	}

	public byte getCvchecked() {
		return this.cvchecked;
	}

	public void setCvchecked(byte cvchecked) {
		this.cvchecked = cvchecked;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getEnglishLevel() {
		return this.englishLevel;
	}

	public void setEnglishLevel(BigDecimal englishLevel) {
		this.englishLevel = englishLevel;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGitHub() {
		return this.gitHub;
	}

	public void setGitHub(String gitHub) {
		this.gitHub = gitHub;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLinkedIn() {
		return this.linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getPrincipalskill() {
		return this.principalskill;
	}

	public void setPrincipalskill(Integer principalskill) {
		this.principalskill = principalskill;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Integer getYearsOfExperience() {
		return this.yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public Authentication getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public List<Education> getEducations() {
		return this.educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public Education addEducation(Education education) {
		getEducations().add(education);
		education.setUser(this);

		return education;
	}

	public Education removeEducation(Education education) {
		getEducations().remove(education);
		education.setUser(null);

		return education;
	}

	public List<Jobbyuser> getJobbyusers() {
		return this.jobbyusers;
	}

	public void setJobbyusers(List<Jobbyuser> jobbyusers) {
		this.jobbyusers = jobbyusers;
	}

	public Jobbyuser addJobbyuser(Jobbyuser jobbyuser) {
		getJobbyusers().add(jobbyuser);
		jobbyuser.setUser(this);

		return jobbyuser;
	}

	public Jobbyuser removeJobbyuser(Jobbyuser jobbyuser) {
		getJobbyusers().remove(jobbyuser);
		jobbyuser.setUser(null);

		return jobbyuser;
	}

	public List<Jobexperience> getJobexperiences() {
		return this.jobexperiences;
	}

	public void setJobexperiences(List<Jobexperience> jobexperiences) {
		this.jobexperiences = jobexperiences;
	}

	public Jobexperience addJobexperience(Jobexperience jobexperience) {
		getJobexperiences().add(jobexperience);
		jobexperience.setUser(this);

		return jobexperience;
	}

	public Jobexperience removeJobexperience(Jobexperience jobexperience) {
		getJobexperiences().remove(jobexperience);
		jobexperience.setUser(null);

		return jobexperience;
	}

	public List<Jobmatch> getJobmatches() {
		return this.jobmatches;
	}

	public void setJobmatches(List<Jobmatch> jobmatches) {
		this.jobmatches = jobmatches;
	}

	public Jobmatch addJobmatch(Jobmatch jobmatch) {
		getJobmatches().add(jobmatch);
		jobmatch.setUser(this);

		return jobmatch;
	}

	public Jobmatch removeJobmatch(Jobmatch jobmatch) {
		getJobmatches().remove(jobmatch);
		jobmatch.setUser(null);

		return jobmatch;
	}

	public Userprocess getUserprocess() {
		return this.userprocess;
	}

	public void setUserprocess(Userprocess userprocess) {
		this.userprocess = userprocess;
	}

	public List<Userskill> getUserskills() {
		return this.userskills;
	}

	public void setUserskills(List<Userskill> userskills) {
		this.userskills = userskills;
	}

	public Userskill addUserskill(Userskill userskill) {
		getUserskills().add(userskill);
		userskill.setUser(this);

		return userskill;
	}

	public Userskill removeUserskill(Userskill userskill) {
		getUserskills().remove(userskill);
		userskill.setUser(null);

		return userskill;
	}

	public List<Userskillcalculated> getUserskillcalculateds() {
		return this.userskillcalculateds;
	}

	public void setUserskillcalculateds(List<Userskillcalculated> userskillcalculateds) {
		this.userskillcalculateds = userskillcalculateds;
	}

	public Userskillcalculated addUserskillcalculated(Userskillcalculated userskillcalculated) {
		getUserskillcalculateds().add(userskillcalculated);
		userskillcalculated.setUser(this);

		return userskillcalculated;
	}

	public Userskillcalculated removeUserskillcalculated(Userskillcalculated userskillcalculated) {
		getUserskillcalculateds().remove(userskillcalculated);
		userskillcalculated.setUser(null);

		return userskillcalculated;
	}

}