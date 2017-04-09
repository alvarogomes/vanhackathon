package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jobposition database table.
 * 
 */
@Entity
@NamedQuery(name="Jobposition.findAll", query="SELECT j FROM Jobposition j")
public class Jobposition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobposition;

	private String position;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="jobposition")
	private List<Job> jobs;

	public Jobposition() {
	}

	public Integer getIdjobposition() {
		return this.idjobposition;
	}

	public void setIdjobposition(Integer idjobposition) {
		this.idjobposition = idjobposition;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Job addJob(Job job) {
		getJobs().add(job);
		job.setJobposition(this);

		return job;
	}

	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setJobposition(null);

		return job;
	}

}