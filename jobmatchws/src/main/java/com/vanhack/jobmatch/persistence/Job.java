package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the job database table.
 * 
 */
@Entity
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjob;

	@Lob
	private String aboutThisRole;

	@Lob
	private String aboutUs;

	@Lob
	private String aboutYourTeam;

	private byte applied;

	@Lob
	private String benefits;

	private String city;

	private Double commission;

	private String country;

	private byte deleted;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	private Integer iduser;

	private Integer iduserrecruiter;

	private Integer jobtype;

	private Integer numberOfPosition;

	private String position;

	@Temporal(TemporalType.TIMESTAMP)
	private Date postdate;

	private Integer principalskill;

	@Column(name="PRIVATE")
	private byte private_;

	@Lob
	private String qualifications;

	private byte remotejob;

	@Lob
	private String responsibilities;

	private Double salary;

	private Integer yearsOfExperience;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="IDCOMPANY")
	private Company company;

	//bi-directional many-to-one association to Jobposition
	@ManyToOne
	@JoinColumn(name="IDJOBPOSITION")
	private Jobposition jobposition;

	//bi-directional many-to-many association to Skill
	@ManyToMany
	@JoinTable(
		name="jobskill"
		, joinColumns={
			@JoinColumn(name="IDJOB")
			}
		, inverseJoinColumns={
			@JoinColumn(name="IDSKILL")
			}
		)
	private List<Skill> skills;

	//bi-directional many-to-one association to Jobmatch
	@OneToMany(mappedBy="job")
	private List<Jobmatch> jobmatches;

	//bi-directional one-to-one association to Jobprocess
	@OneToOne(mappedBy="job")
	private Jobprocess jobprocess;

	//bi-directional many-to-one association to Jobskillcalculated
	@OneToMany(mappedBy="job")
	private List<Jobskillcalculated> jobskillcalculateds;

	public Job() {
	}

	public Integer getIdjob() {
		return this.idjob;
	}

	public void setIdjob(Integer idjob) {
		this.idjob = idjob;
	}

	public String getAboutThisRole() {
		return this.aboutThisRole;
	}

	public void setAboutThisRole(String aboutThisRole) {
		this.aboutThisRole = aboutThisRole;
	}

	public String getAboutUs() {
		return this.aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public String getAboutYourTeam() {
		return this.aboutYourTeam;
	}

	public void setAboutYourTeam(String aboutYourTeam) {
		this.aboutYourTeam = aboutYourTeam;
	}

	public byte getApplied() {
		return this.applied;
	}

	public void setApplied(byte applied) {
		this.applied = applied;
	}

	public String getBenefits() {
		return this.benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getCommission() {
		return this.commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public Integer getIduserrecruiter() {
		return this.iduserrecruiter;
	}

	public void setIduserrecruiter(Integer iduserrecruiter) {
		this.iduserrecruiter = iduserrecruiter;
	}

	public Integer getJobtype() {
		return this.jobtype;
	}

	public void setJobtype(Integer jobtype) {
		this.jobtype = jobtype;
	}

	public Integer getNumberOfPosition() {
		return this.numberOfPosition;
	}

	public void setNumberOfPosition(Integer numberOfPosition) {
		this.numberOfPosition = numberOfPosition;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getPostdate() {
		return this.postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}

	public Integer getPrincipalskill() {
		return this.principalskill;
	}

	public void setPrincipalskill(Integer principalskill) {
		this.principalskill = principalskill;
	}

	public byte getPrivate_() {
		return this.private_;
	}

	public void setPrivate_(byte private_) {
		this.private_ = private_;
	}

	public String getQualifications() {
		return this.qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public byte getRemotejob() {
		return this.remotejob;
	}

	public void setRemotejob(byte remotejob) {
		this.remotejob = remotejob;
	}

	public String getResponsibilities() {
		return this.responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getYearsOfExperience() {
		return this.yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Jobposition getJobposition() {
		return this.jobposition;
	}

	public void setJobposition(Jobposition jobposition) {
		this.jobposition = jobposition;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Jobmatch> getJobmatches() {
		return this.jobmatches;
	}

	public void setJobmatches(List<Jobmatch> jobmatches) {
		this.jobmatches = jobmatches;
	}

	public Jobmatch addJobmatch(Jobmatch jobmatch) {
		getJobmatches().add(jobmatch);
		jobmatch.setJob(this);

		return jobmatch;
	}

	public Jobmatch removeJobmatch(Jobmatch jobmatch) {
		getJobmatches().remove(jobmatch);
		jobmatch.setJob(null);

		return jobmatch;
	}

	public Jobprocess getJobprocess() {
		return this.jobprocess;
	}

	public void setJobprocess(Jobprocess jobprocess) {
		this.jobprocess = jobprocess;
	}

	public List<Jobskillcalculated> getJobskillcalculateds() {
		return this.jobskillcalculateds;
	}

	public void setJobskillcalculateds(List<Jobskillcalculated> jobskillcalculateds) {
		this.jobskillcalculateds = jobskillcalculateds;
	}

	public Jobskillcalculated addJobskillcalculated(Jobskillcalculated jobskillcalculated) {
		getJobskillcalculateds().add(jobskillcalculated);
		jobskillcalculated.setJob(this);

		return jobskillcalculated;
	}

	public Jobskillcalculated removeJobskillcalculated(Jobskillcalculated jobskillcalculated) {
		getJobskillcalculateds().remove(jobskillcalculated);
		jobskillcalculated.setJob(null);

		return jobskillcalculated;
	}

}