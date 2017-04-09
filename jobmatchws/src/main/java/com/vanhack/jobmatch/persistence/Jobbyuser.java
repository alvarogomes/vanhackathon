package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the jobbyuser database table.
 * 
 */
@Entity
@NamedQuery(name="Jobbyuser.findAll", query="SELECT j FROM Jobbyuser j")
public class Jobbyuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobbyuser;

	private Integer applicationstatus;

	private byte applied;

	private String areyougoodforthisjob;

	private byte contacted;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	private byte favorite;

	private Integer idjob;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	//bi-directional many-to-one association to Jobbyuserlog
	@OneToMany(mappedBy="jobbyuser")
	private List<Jobbyuserlog> jobbyuserlogs;

	public Jobbyuser() {
	}

	public Integer getIdjobbyuser() {
		return this.idjobbyuser;
	}

	public void setIdjobbyuser(Integer idjobbyuser) {
		this.idjobbyuser = idjobbyuser;
	}

	public Integer getApplicationstatus() {
		return this.applicationstatus;
	}

	public void setApplicationstatus(Integer applicationstatus) {
		this.applicationstatus = applicationstatus;
	}

	public byte getApplied() {
		return this.applied;
	}

	public void setApplied(byte applied) {
		this.applied = applied;
	}

	public String getAreyougoodforthisjob() {
		return this.areyougoodforthisjob;
	}

	public void setAreyougoodforthisjob(String areyougoodforthisjob) {
		this.areyougoodforthisjob = areyougoodforthisjob;
	}

	public byte getContacted() {
		return this.contacted;
	}

	public void setContacted(byte contacted) {
		this.contacted = contacted;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public byte getFavorite() {
		return this.favorite;
	}

	public void setFavorite(byte favorite) {
		this.favorite = favorite;
	}

	public Integer getIdjob() {
		return this.idjob;
	}

	public void setIdjob(Integer idjob) {
		this.idjob = idjob;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Jobbyuserlog> getJobbyuserlogs() {
		return this.jobbyuserlogs;
	}

	public void setJobbyuserlogs(List<Jobbyuserlog> jobbyuserlogs) {
		this.jobbyuserlogs = jobbyuserlogs;
	}

	public Jobbyuserlog addJobbyuserlog(Jobbyuserlog jobbyuserlog) {
		getJobbyuserlogs().add(jobbyuserlog);
		jobbyuserlog.setJobbyuser(this);

		return jobbyuserlog;
	}

	public Jobbyuserlog removeJobbyuserlog(Jobbyuserlog jobbyuserlog) {
		getJobbyuserlogs().remove(jobbyuserlog);
		jobbyuserlog.setJobbyuser(null);

		return jobbyuserlog;
	}

}