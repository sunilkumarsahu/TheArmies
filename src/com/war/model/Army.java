package com.war.model;

/** Holds the information of army type and it's power */
public class Army {
  private final String type;
  private final int power;

  public Army(String type, int power) {
    this.type = type;
    this.power = power;
  }

  public String getType() {
    return type;
  }

  public int getPower() {
    return power;
  }

  public Army copy() {
    return new Army(this.type, this.power);
  }
}
