package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userskillcalculated database table.
 * 
 */
@Entity
@NamedQuery(name="Userskillcalculated.findAll", query="SELECT u FROM Userskillcalculated u")
public class Userskillcalculated implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer iduserskillcalculated;

	private Integer level;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="IDSKILL")
	private Skill skill;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Userskillcalculated() {
	}

	public Integer getIduserskillcalculated() {
		return this.iduserskillcalculated;
	}

	public void setIduserskillcalculated(Integer iduserskillcalculated) {
		this.iduserskillcalculated = iduserskillcalculated;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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