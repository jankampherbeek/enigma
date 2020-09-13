/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.domain.config.BaseAstronConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Project for statistical research.
 */
public class StatsProject {

   private final String name;
   private final String description;
   private final BaseAstronConfig baseAstronConfig;
   private final List<IStatsScenario> scenarios;

   public StatsProject(@NotNull final String name,
                       @NotNull final String description,
                       @NotNull final BaseAstronConfig baseAstronConfig,
                       @NotNull final List<IStatsScenario> scenarios) {
      this.name = name;
      this.description = description;
      this.baseAstronConfig = baseAstronConfig;
      this.scenarios = scenarios;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public BaseAstronConfig getBaseAstronConfig() {
      return baseAstronConfig;
   }

   public List<IStatsScenario> getScenarios() {
      return scenarios;
   }
}
