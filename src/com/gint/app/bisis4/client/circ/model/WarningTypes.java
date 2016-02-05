package com.gint.app.bisis4.client.circ.model;

// Generated Nov 15, 2006 4:24:31 PM by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * WarningTypes generated by hbm2java
 */
public class WarningTypes implements java.io.Serializable {

	// Fields    

	private int id;

	private String name;

	private String wtext;

	private Set warnCounterses = new HashSet(0);

	private Set warningses = new HashSet(0);

	// Constructors

	/** default constructor */
	public WarningTypes() {
	}

	/** minimal constructor */
	public WarningTypes(int id) {
		this.id = id;
	}

	/** full constructor */
	public WarningTypes(int id, String name, String wtext, Set warnCounterses,
			Set warningses) {
		this.id = id;
		this.name = name;
		this.wtext = wtext;
		this.warnCounterses = warnCounterses;
		this.warningses = warningses;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWtext() {
		return this.wtext;
	}

	public void setWtext(String wtext) {
		this.wtext = wtext;
	}

	public Set getWarnCounterses() {
		return this.warnCounterses;
	}

	public void setWarnCounterses(Set warnCounterses) {
		this.warnCounterses = warnCounterses;
	}

	public Set getWarningses() {
		return this.warningses;
	}

	public void setWarningses(Set warningses) {
		this.warningses = warningses;
	}

}