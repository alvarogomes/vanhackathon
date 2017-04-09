package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userskill database table.
 * 
 */
@Entity
@NamedQuery(name="Userskill.findAll", query="SELECT u FROM Userskill u")
public class Userskill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserskillPK id;

	private Integer yearsofexperience;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="IDSKILL")
	private Skill skill;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Userskill() {
	}

	public UserskillPK getId() {
		return this.id;
	}

	public void setId(UserskillPK id) {
		this.id = id;
	}

	public Integer getYearsofexperience() {
		return this.yearsofexperience;
	}

	public void setYearsofexperience(Integer yearsofexperience) {
		this.yearsofexperience = yearsofexperience;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}