package com.war.controller;

import com.war.model.Battalion;
import com.war.util.InputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Falcornia {
  private final ArrayList<Battalion> totalArmies;

  public Falcornia(ArrayList<Battalion> totalArmies) {
    this.totalArmies = totalArmies;
  }

  /** Return a battalion unit for war move. */
  private Battalion getBattalion(String armyType, int count) {
    for (Battalion unit : totalArmies) {
      if (armyType.equals(unit.getArmy().getType())) {
        if (count < 0 || count > unit.getCount()) {
          return null;
        }
        Battalion battalion = unit.copy();
        battalion.setCount(count);
        return battalion;
      }
    }
    return null;
  }

  /**
   * Returns a list of battalion units for war move.
   *
   * @throws InputException
   */
  public ArrayList<Battalion> constructArmiesMove(LinkedHashMap<String, Integer> armies)
      throws InputException {
    ArrayList<Battalion> armiesToMove = new ArrayList<>();
    for (Entry<String, Integer> army : armies.entrySet()) {
      String armyType = army.getKey();
      int count = army.getValue();
      Battalion battalion = getBattalion(armyType, count);
      if (battalion == null) {
        throw new InputException("Invalide move..");
      }
      armiesToMove.add(battalion);
    }
    return armiesToMove;
  }
}
