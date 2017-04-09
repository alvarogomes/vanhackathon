package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the userprocess database table.
 * 
 */
@Entity
@NamedQuery(name="Userprocess.findAll", query="SELECT u FROM Userprocess u")
public class Userprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer iduser;

	@Temporal(TemporalType.DATE)
	private Date dtend;

	@Temporal(TemporalType.DATE)
	private Date dtstart;

	private Integer status;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Userprocess() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}