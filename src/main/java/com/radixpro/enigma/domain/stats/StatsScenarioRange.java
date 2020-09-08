/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.StatsRangeTypes;
import com.radixpro.enigma.references.StatsScenarioTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Scenario for statistical research into ranges (areas).
 */
public class StatsScenarioRange implements IStatsScenario {

   final String rbName;
   final String rbDescription;
   final List<Player> players;
   final StatsScenarioTypes scenarioType;
   final StatsRangeTypes rangeType;

   public StatsScenarioRange(@NotNull final String rbName,
                             @NotNull final String rbDescription,
                             @NotNull final List<Player> players,
                             @NotNull final StatsScenarioTypes scenarioType,
                             @NotNull final StatsRangeTypes rangeType) {
      this.rbName = rbName;
      this.rbDescription = rbDescription;
      this.players = players;
      this.scenarioType = scenarioType;
      this.rangeType = rangeType;
   }

   @Override
   public String getRbName() {
      return rbName;
   }

   @Override
   public String getRbDescription() {
      return rbDescription;
   }

   @Override
   public List<Player> getPlayers() {
      return players;
   }

   @Override
   public StatsScenarioTypes getScenarioType() {
      return scenarioType;
   }

   public StatsRangeTypes getRangeType() {
      return rangeType;
   }
}
