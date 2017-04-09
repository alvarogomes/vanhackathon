package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the education database table.
 * 
 */
@Entity
@NamedQuery(name="Education.findAll", query="SELECT e FROM Education e")
public class Education implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer ideducation;

	private String country;

	private String course;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	private String location;

	private String name;

	@Column(name="`ORDER`")
	private Integer order;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	private String stateprovince;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Education() {
	}

	public Integer getIdeducation() {
		return this.ideducation;
	}

	public void setIdeducation(Integer ideducation) {
		this.ideducation = ideducation;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCourse() {
		return this.course;
	}

	public void setCourse(String course) {
		this.course = course;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}