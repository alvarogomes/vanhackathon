package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the jobbyuserlog database table.
 * 
 */
@Entity
@NamedQuery(name="Jobbyuserlog.findAll", query="SELECT j FROM Jobbyuserlog j")
public class Jobbyuserlog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobbyuserlog;

	private Integer applicationStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private Integer iduserapplication;

	//bi-directional many-to-one association to Jobbyuser
	@ManyToOne
	@JoinColumn(name="IDJOBBYUSER")
	private Jobbyuser jobbyuser;

	public Jobbyuserlog() {
	}

	public Integer getIdjobbyuserlog() {
		return this.idjobbyuserlog;
	}

	public void setIdjobbyuserlog(Integer idjobbyuserlog) {
		this.idjobbyuserlog = idjobbyuserlog;
	}

	public Integer getApplicationStatus() {
		return this.applicationStatus;
	}

	public void setApplicationStatus(Integer applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getIduserapplication() {
		return this.iduserapplication;
	}

	public void setIduserapplication(Integer iduserapplication) {
		this.iduserapplication = iduserapplication;
	}

	public Jobbyuser getJobbyuser() {
		return this.jobbyuser;
	}

	public void setJobbyuser(Jobbyuser jobbyuser) {
		this.jobbyuser = jobbyuser;
	}

}