package com.war.controller;

import com.war.model.Battalion;
import com.war.model.Army;
import com.war.util.BattalionNotFoundException;
import com.war.util.Constants;
import com.war.util.InputException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WarController {
  private Falcornia falcornia;
  private Lengaburu lengaburu;

  public WarController() {
    initArmies();
  }

  /** Initialized both kingdom's armies with available counts. */
  private void initArmies() {
    ArrayList<Battalion> falCorniaArmies = new ArrayList<>();
    falCorniaArmies.add(
        new Battalion(new Army(Constants.HORSE, Constants.POWER_FALCORNIA_HORSE), 300));
    falCorniaArmies.add(
        new Battalion(new Army(Constants.ELEPHANT, Constants.POWER_FALCORNIA_ELEPHANT), 200));
    falCorniaArmies.add(
        new Battalion(
            new Army(Constants.ARMOURED_TANK, Constants.POWER_FALCORNIA_ARMOURED_TANK), 40));
    falCorniaArmies.add(
        new Battalion(new Army(Constants.SLING_GUN, Constants.POWER_FALCORNIA_SLING_GUN), 20));
    falcornia = new Falcornia(falCorniaArmies);

    ArrayList<Battalion> lengaburuArmies = new ArrayList<>();
    lengaburuArmies.add(
        new Battalion(new Army(Constants.HORSE, Constants.POWER_LENGABURU_HORSE), 100));
    lengaburuArmies.add(
        new Battalion(new Army(Constants.ELEPHANT, Constants.POWER_LENGABURU_ELEPHANT), 50));
    lengaburuArmies.add(
        new Battalion(
            new Army(Constants.ARMOURED_TANK, Constants.POWER_LENGABURU_ARMOURED_TANK), 10));
    lengaburuArmies.add(
        new Battalion(new Army(Constants.SLING_GUN, Constants.POWER_LENGABURU_SLING_GUN), 5));
    lengaburu = new Lengaburu(lengaburuArmies);
  }

  /**
   * Starts the war with given enemy armies units move.
   *
   * @param falcorniaMove
   * @throws InputException
   * @throws BattalionNotFoundException
   */
  private void startsWar(LinkedHashMap<String, Integer> falcorniaMove)
      throws InputException, BattalionNotFoundException {
    ArrayList<Battalion> falcorniaArmies = falcornia.constructArmiesMove(falcorniaMove);
    ArrayList<Battalion> lengaburuDeployedArmies = lengaburu.deployArmies(falcorniaArmies);
    printWarSummary(falcorniaArmies, lengaburuDeployedArmies);
  }

  /**
   * Prints the war summaries for the given armies.
   *
   * @param attackingArmies the attacking armies list.
   * @param defensingArmies the defensing armies list.
   */
  private void printWarSummary(
      ArrayList<Battalion> attackingArmies, ArrayList<Battalion> defensingArmies) {
    StringBuilder warSummary = new StringBuilder("");
    warSummary.append("Falcornia attack with ");
    appendArmySummany(warSummary, attackingArmies);
    warSummary.append("\n");
    warSummary.append("Lengaburu deploys ");
    appendArmySummany(warSummary, defensingArmies);
    warSummary.append("and ");
    if (lengaburu.isWin()) {
      warSummary.append("wins");
    } else {
      warSummary.append("loses");
    }
    System.out.println(warSummary.toString());
  }

  /** A util method to construct the summary of each battalion for displaying result. */
  private void appendArmySummany(StringBuilder warSummary, ArrayList<Battalion> battalions) {
    for (Battalion battalion : battalions) {
      warSummary.append(battalion.getCount() + " " + battalion.getArmy().getType() + ", ");
    }
  }

  public static void main(String[] args) {
    WarController warController = new WarController();

    // Provide the Falcornia move here
    LinkedHashMap<String, Integer> falcorniaMove = new LinkedHashMap<>();
    falcorniaMove.put(Constants.HORSE, 100);
    falcorniaMove.put(Constants.ELEPHANT, 101);
    falcorniaMove.put(Constants.ARMOURED_TANK, 20);
    falcorniaMove.put(Constants.SLING_GUN, 5);

    try {
      warController.startsWar(falcorniaMove);
    } catch (InputException e) {
      System.out.println(e.getMessage());
    } catch (BattalionNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}
