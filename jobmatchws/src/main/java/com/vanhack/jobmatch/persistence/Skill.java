package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the skill database table.
 * 
 */
@Entity
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idskill;

	private Byte active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date approvaldate;

	private String skillname;

	//bi-directional many-to-many association to Job
	@ManyToMany(mappedBy="skills")
	private List<Job> jobs;

	//bi-directional many-to-one association to Jobskillcalculated
	@OneToMany(mappedBy="skill")
	private List<Jobskillcalculated> jobskillcalculateds;

	//bi-directional many-to-one association to Userskill
	@OneToMany(mappedBy="skill")
	private List<Userskill> userskills;

	//bi-directional many-to-one association to Userskillcalculated
	@OneToMany(mappedBy="skill")
	private List<Userskillcalculated> userskillcalculateds;

	public Skill() {
	}

	public Integer getIdskill() {
		return this.idskill;
	}

	public void setIdskill(Integer idskill) {
		this.idskill = idskill;
	}

	public Byte getActive() {
		return this.active;
	}

	public void setActive(Byte active) {
		this.active = active;
	}

	public Date getApprovaldate() {
		return this.approvaldate;
	}

	public void setApprovaldate(Date approvaldate) {
		this.approvaldate = approvaldate;
	}

	public String getSkillname() {
		return this.skillname;
	}

	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public List<Jobskillcalculated> getJobskillcalculateds() {
		return this.jobskillcalculateds;
	}

	public void setJobskillcalculateds(List<Jobskillcalculated> jobskillcalculateds) {
		this.jobskillcalculateds = jobskillcalculateds;
	}

	public Jobskillcalculated addJobskillcalculated(Jobskillcalculated jobskillcalculated) {
		getJobskillcalculateds().add(jobskillcalculated);
		jobskillcalculated.setSkill(this);

		return jobskillcalculated;
	}

	public Jobskillcalculated removeJobskillcalculated(Jobskillcalculated jobskillcalculated) {
		getJobskillcalculateds().remove(jobskillcalculated);
		jobskillcalculated.setSkill(null);

		return jobskillcalculated;
	}

	public List<Userskill> getUserskills() {
		return this.userskills;
	}

	public void setUserskills(List<Userskill> userskills) {
		this.userskills = userskills;
	}

	public Userskill addUserskill(Userskill userskill) {
		getUserskills().add(userskill);
		userskill.setSkill(this);

		return userskill;
	}

	public Userskill removeUserskill(Userskill userskill) {
		getUserskills().remove(userskill);
		userskill.setSkill(null);

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
		userskillcalculated.setSkill(this);

		return userskillcalculated;
	}

	public Userskillcalculated removeUserskillcalculated(Userskillcalculated userskillcalculated) {
		getUserskillcalculateds().remove(userskillcalculated);
		userskillcalculated.setSkill(null);

		return userskillcalculated;
	}

}