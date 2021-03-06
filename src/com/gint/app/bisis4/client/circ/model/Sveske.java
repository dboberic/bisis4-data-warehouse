package com.gint.app.bisis4.client.circ.model;

// Generated Jan 11, 2007 11:36:46 AM by Hibernate Tools 3.2.0.beta8

import java.math.BigDecimal;

/**
 * Sveske generated by hbm2java
 */
public class Sveske implements java.io.Serializable {

  // Fields    

  private int sveskaId;

  private int version;

  private StatusPrimerka statusPrimerka;

  private Godine godine;

  private String invBr;

  private String signatura;

  private String brojSveske;

  private BigDecimal cena;
  
  private int stanje;

  // Constructors

  
	/** default constructor */
  public Sveske() {
  }

  /** minimal constructor */
  public Sveske(int sveskaId, StatusPrimerka statusPrimerka, String invBr) {
    this.sveskaId = sveskaId;
    this.statusPrimerka = statusPrimerka;
    this.invBr = invBr;
  }

  /** full constructor */
  public Sveske(int sveskaId, StatusPrimerka statusPrimerka, Godine godine,
      String invBr, String signatura, String brojSveske, BigDecimal cena, int stanje) {
    this.sveskaId = sveskaId;
    this.statusPrimerka = statusPrimerka;
    this.godine = godine;
    this.invBr = invBr;
    this.signatura = signatura;
    this.brojSveske = brojSveske;
    this.cena = cena;
    this.stanje = stanje;
  }

  // Property accessors
  public int getSveskaId() {
    return this.sveskaId;
  }

  public void setSveskaId(int sveskaId) {
    this.sveskaId = sveskaId;
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public StatusPrimerka getStatusPrimerka() {
    return this.statusPrimerka;
  }

  public void setStatusPrimerka(StatusPrimerka statusPrimerka) {
    this.statusPrimerka = statusPrimerka;
  }

  public Godine getGodine() {
    return this.godine;
  }

  public void setGodine(Godine godine) {
    this.godine = godine;
  }

  public String getInvBr() {
    return this.invBr;
  }

  public void setInvBr(String invBr) {
    this.invBr = invBr;
  }

  public String getSignatura() {
    return this.signatura;
  }

  public void setSignatura(String signatura) {
    this.signatura = signatura;
  }

  public String getBrojSveske() {
    return this.brojSveske;
  }

  public void setBrojSveske(String brojSveske) {
    this.brojSveske = brojSveske;
  }

  public BigDecimal getCena() {
    return this.cena;
  }

  public void setCena(BigDecimal cena) {
    this.cena = cena;
  }
  
  public int getStanje() {
		return stanje;
	}

	public void setStanje(int stanje) {
		this.stanje = stanje;
	}


}
