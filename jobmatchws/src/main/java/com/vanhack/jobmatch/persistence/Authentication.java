package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the authentication database table.
 * 
 */
@Entity
@NamedQuery(name="Authentication.findAll", query="SELECT a FROM Authentication a")
public class Authentication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer iduser;

	private String password;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="IDUSER")
	private User user;

	public Authentication() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}