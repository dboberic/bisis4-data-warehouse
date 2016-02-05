package com.gint.app.bisis4.client.circ.model;

// Generated Nov 15, 2006 4:24:31 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Lending generated by hbm2java
 */
public class Lending implements java.io.Serializable {

	// Fields    

	private int id;

	private Location location;

	private Users users;

	private String librarianLend;

	private String librarianReturn;

	private String librarianResume;

	private String ctlgNo;

	private Date lendDate;

	private Date returnDate;

	private Date resumeDate;
  
  private Date deadline;
  
  private Set warningses = new HashSet(0);


	// Constructors

	/** default constructor */
	public Lending() {
	}

	/** minimal constructor */
	public Lending(int id, Location location, Users users,
			String librarianLend, String ctlgNo, Date lendDate) {
		this.id = id;
		this.location = location;
		this.users = users;
		this.librarianLend = librarianLend;
		this.ctlgNo = ctlgNo;
		this.lendDate = lendDate;
    
	}

	/** full constructor */
	public Lending(int id, Location location, Users users,
			String librarianLend,
			String librarianReturn,
			String librarianResume, String ctlgNo, Date lendDate,
			Date returnDate, Date resumeDate, Date deadline, Set warningses) {
		this.id = id;
		this.location = location;
		this.users = users;
		this.librarianLend = librarianLend;
		this.librarianReturn = librarianReturn;
		this.librarianResume = librarianResume;
		this.ctlgNo = ctlgNo;
		this.lendDate = lendDate;
		this.returnDate = returnDate;
		this.resumeDate = resumeDate;
    this.deadline = deadline;
    this.warningses = warningses;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getLibrarianLend() {
		return this.librarianLend;
	}

	public void setLibrarianLend(String librarianLend) {
		this.librarianLend = librarianLend;
	}

	public String getLibrarianReturn() {
		return this.librarianReturn;
	}

	public void setLibrarianReturn(String librarianReturn) {
		this.librarianReturn = librarianReturn;
	}

	public String getLibrarianResume() {
		return this.librarianResume;
	}

	public void setLibrarianResume(String librarianResume) {
		this.librarianResume = librarianResume;
	}

	public String getCtlgNo() {
		return this.ctlgNo;
	}

	public void setCtlgNo(String ctlgNo) {
		this.ctlgNo = ctlgNo;
	}

	public Date getLendDate() {
		return this.lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getResumeDate() {
		return this.resumeDate;
	}

	public void setResumeDate(Date resumeDate) {
		this.resumeDate = resumeDate;
	}
  
  public Date getDeadline() {
    return this.deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }
  
  public Set getWarningses() {
    return this.warningses;
  }

  public void setWarningses(Set warningses) {
    this.warningses = warningses;
//    Iterator it = this.warningses.iterator();
//    while (it.hasNext()){
//      ((Warnings)it.next()).setUsers(this);
//    }
  }
  
  public void addWarnings(Warnings warn){
    this.warningses.add(warn);
    warn.setLending(this);
  }
  
  public void removeWarnings(Warnings warn){
    this.warningses.remove(warn);
    warn.setLending(null);
  }

}
