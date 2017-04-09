package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jobmatch database table.
 * 
 */
@Entity
@NamedQuery(name="Jobmatch.findAll", query="SELECT j FROM Jobmatch j")
public class Jobmatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobmatch;

	private Double similarity;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="IDJOB")
	private Job job;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Jobmatch() {
	}

	public Integer getIdjobmatch() {
		return this.idjobmatch;
	}

	public void setIdjobmatch(Integer idjobmatch) {
		this.idjobmatch = idjobmatch;
	}

	public Double getSimilarity() {
		return this.similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}