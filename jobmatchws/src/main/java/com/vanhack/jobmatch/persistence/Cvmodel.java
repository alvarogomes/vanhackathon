package com.vanhack.jobmatch.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cvmodel database table.
 * 
 */
@Entity
@NamedQuery(name="Cvmodel.findAll", query="SELECT c FROM Cvmodel c")
public class Cvmodel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idmodel;

	private String description;

	@Lob
	private byte[] file;

	public Cvmodel() {
	}

	public int getIdmodel() {
		return this.idmodel;
	}

	public void setIdmodel(int idmodel) {
		this.idmodel = idmodel;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getFile() {
		return this.file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}