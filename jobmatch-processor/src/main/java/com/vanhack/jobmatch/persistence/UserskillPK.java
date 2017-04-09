package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the userskill database table.
 * 
 */
@Embeddable
public class UserskillPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer idskill;

	@Column(insertable=false, updatable=false)
	private Integer iduser;

	public UserskillPK() {
	}
	public Integer getIdskill() {
		return this.idskill;
	}
	public void setIdskill(Integer idskill) {
		this.idskill = idskill;
	}
	public Integer getIduser() {
		return this.iduser;
	}
	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserskillPK)) {
			return false;
		}
		UserskillPK castOther = (UserskillPK)other;
		return 
			(this.idskill == castOther.idskill)
			&& (this.iduser == castOther.iduser);
	}

	public int hashCode() {
		final Integer prime = 31;
		int hash = 17;
		hash = hash * prime + this.idskill;
		hash = hash * prime + this.iduser;
		
		return hash;
	}
}