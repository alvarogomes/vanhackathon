package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the jobexperience database table.
 * 
 */
@Entity
@NamedQuery(name="Jobexperience.findAll", query="SELECT j FROM Jobexperience j")
public class Jobexperience implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idjobexperience;

	private String companyname;

	private String country;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	private String location;

	@Column(name="`ORDER`")
	private Integer order;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	private String stateprovince;

	private String title;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Jobexperience() {
	}

	public Integer getIdjobexperience() {
		return this.idjobexperience;
	}

	public void setIdjobexperience(Integer idjobexperience) {
		this.idjobexperience = idjobexperience;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getStateprovince() {
		return this.stateprovince;
	}

	public void setStateprovince(String stateprovince) {
		this.stateprovince = stateprovince;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}