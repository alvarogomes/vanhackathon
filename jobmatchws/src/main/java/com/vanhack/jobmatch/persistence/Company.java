package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idcompany;

	private String city;

	private String country;

	private Integer favourite;

	private String image;

	private String name;

	private String phone;

	private String province;

	private String sumary;

	private String website;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="company")
	private List<Job> jobs;

	public Company() {
	}

	public Integer getIdcompany() {
		return this.idcompany;
	}

	public void setIdcompany(Integer idcompany) {
		this.idcompany = idcompany;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getFavourite() {
		return this.favourite;
	}

	public void setFavourite(Integer favourite) {
		this.favourite = favourite;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSumary() {
		return this.sumary;
	}

	public void setSumary(String sumary) {
		this.sumary = sumary;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Job addJob(Job job) {
		getJobs().add(job);
		job.setCompany(this);

		return job;
	}

	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setCompany(null);

		return job;
	}

}