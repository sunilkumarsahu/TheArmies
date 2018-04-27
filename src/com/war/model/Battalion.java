package com.war.model;

/** Model class to represent a particular type of army with its count. */
public class Battalion {
  private final Army army;
  private int count;

  public Battalion(Army army, int count) {
    this.army = army;
    this.count = count;
  }

  public Army getArmy() {
    return army;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Battalion)) {
      return false;
    }
    Battalion other = (Battalion) obj;
    // if type match considering same object
    return this.army.getType().equals(other.army.getType());
  }

  public Battalion copy() {
    return new Battalion(this.army.copy(), this.count);
  }
}
