package com.war.controller;

import com.war.model.Battalion;
import com.war.util.BattalionNotFoundException;
import java.util.ArrayList;

public class Lengaburu {
  private final ArrayList<Battalion> totalArmies;
  private boolean win = true;

  public Lengaburu(ArrayList<Battalion> totalArmies) {
    this.totalArmies = totalArmies;
  }

  /**
   * Returns the deployed armies for a given armies of enemy.
   *
   * @throws BattalionNotFoundException
   */
  public ArrayList<Battalion> deployArmies(ArrayList<Battalion> enemyArmies)
      throws BattalionNotFoundException {
    ArrayList<Battalion> deployedArmies = new ArrayList<>();
    for (Battalion enemyBattalion : enemyArmies) {
      int index = totalArmies.indexOf(enemyBattalion);
      if (index == -1) {
        throw new BattalionNotFoundException(
            "The enemy added a new battalion, Lengaburu don't have such type of battalion");
      }

      int enemyPower = getTotalPower(enemyBattalion);
      enemyPower = computeBattalion(deployedArmies, enemyPower, index);

      // Look for adjacent low power battalion if not the first item
      if (enemyPower > 0 && index != 0) {
        enemyPower = computeBattalion(deployedArmies, enemyPower, index - 1);
      }

      // Look for adjacent high power battalion if not the last item
      if (enemyPower > 0 && index != totalArmies.size() - 1) {
        enemyPower = computeBattalion(deployedArmies, enemyPower, index + 1);
      }

      // if still enemy power is greater than zero sure loses.
      if (enemyPower > 0) {
        win = false;
      }
    }
    return deployedArmies;
  }

  /**
   * Create a battalion of given index which will be added to the given deployed
   * armies and return the extra enemy power after computing the defense power.
   *
   * @param deployedArmies
   *          The list represent the deployed armies.
   * @param enemyPower
   *          the power of enemy.
   * @param index
   *          represent the index of battalion inside totalArmies list.
   * @return the enemy power after deduction the defense power.
   */
  private int computeBattalion(ArrayList<Battalion> deployedArmies, int enemyPower, int index) {
    Battalion currentBattalion = totalArmies.get(index);
    int currentBattalionPower = getTotalPower(currentBattalion);

    if (enemyPower > currentBattalionPower) {
      // Add all available battalion
      Battalion deployedBattalion = currentBattalion.copy();
      addBatalionToFightingArmies(deployedArmies, deployedBattalion);
      currentBattalion.setCount(0);
      enemyPower -= currentBattalionPower;
    } else {
      // Add required number of battalion.
      int deployedCount = (int) Math
          .ceil((double) enemyPower / currentBattalion.getArmy().getPower());
      Battalion deployedBattalion = currentBattalion.copy();
      deployedBattalion.setCount(deployedCount);
      addBatalionToFightingArmies(deployedArmies, deployedBattalion);
      enemyPower = 0;
      currentBattalion.setCount(currentBattalion.getCount() - deployedCount);
    }
    return enemyPower;
  }

  /**
   * Add the given battalion to fighting list. It will add the battalion to the
   * given list if not present. if present, just increase the count.
   *
   * @param deployedArmies
   *          the fighting list.
   * @param deployedBattalion
   *          the battalian to add
   */
  private void addBatalionToFightingArmies(ArrayList<Battalion> deployedArmies,
      Battalion deployedBattalion) {
    if (deployedArmies.contains(deployedBattalion)) {
      int existingIndex = deployedArmies.indexOf(deployedBattalion);
      int existingCount = deployedArmies.get(existingIndex).getCount();
      int totalCount = existingCount + deployedBattalion.getCount();
      deployedBattalion.setCount(totalCount);
      deployedArmies.set(existingIndex, deployedBattalion);
    } else {
      deployedArmies.add(deployedBattalion);
    }
  }

  /** Returns the total power of the given battalion. */
  private int getTotalPower(Battalion battalion) {
    return battalion.getCount() * battalion.getArmy().getPower();
  }

  public boolean isWin() {
    return win;
  }
}
