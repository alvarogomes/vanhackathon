package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jobskillcalculated database table.
 * 
 */
@Entity
@NamedQuery(name="Jobskillcalculated.findAll", query="SELECT j FROM Jobskillcalculated j")
public class Jobskillcalculated implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobskillcalculated;

	private Integer level;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="IDJOB")
	private Job job;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="IDSKILL")
	private Skill skill;

	public Jobskillcalculated() {
	}

	public Integer getIdjobskillcalculated() {
		return this.idjobskillcalculated;
	}

	public void setIdjobskillcalculated(Integer idjobskillcalculated) {
		this.idjobskillcalculated = idjobskillcalculated;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

}