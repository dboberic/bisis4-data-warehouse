package com.gint.app.bisis4.client.circ.model;

// Generated Jan 11, 2007 11:36:46 AM by Hibernate Tools 3.2.0.beta8

/**
 * Counters generated by hbm2java
 */
public class Counters implements java.io.Serializable {

  // Fields    

  private String counterName;

  private int counterValue;

  // Constructors

  /** default constructor */
  public Counters() {
  }

  /** full constructor */
  public Counters(String counterName, int counterValue) {
    this.counterName = counterName;
    this.counterValue = counterValue;
  }

  // Property accessors
  public String getCounterName() {
    return this.counterName;
  }

  public void setCounterName(String counterName) {
    this.counterName = counterName;
  }

  public int getCounterValue() {
    return this.counterValue;
  }

  public void setCounterValue(int counterValue) {
    this.counterValue = counterValue;
  }

}
