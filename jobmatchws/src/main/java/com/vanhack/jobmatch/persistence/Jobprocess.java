package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the jobprocess database table.
 * 
 */
@Entity
@NamedQuery(name="Jobprocess.findAll", query="SELECT j FROM Jobprocess j")
public class Jobprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idjob;

	@Temporal(TemporalType.DATE)
	private Date dtend;

	@Temporal(TemporalType.DATE)
	private Date dtstart;

	private Integer status;

	//bi-directional one-to-one association to Job
	@OneToOne
	@JoinColumn(name="IDJOB")
	private Job job;

	public Jobprocess() {
	}

	public Integer getIdjob() {
		return this.idjob;
	}

	public void setIdjob(Integer idjob) {
		this.idjob = idjob;
	}

	public Date getDtend() {
		return this.dtend;
	}

	public void setDtend(Date dtend) {
		this.dtend = dtend;
	}

	public Date getDtstart() {
		return this.dtstart;
	}

	public void setDtstart(Date dtstart) {
		this.dtstart = dtstart;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}