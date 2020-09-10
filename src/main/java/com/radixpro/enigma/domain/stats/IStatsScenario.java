/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.StatsScenarioTypes;

import java.util.List;

/**
 * Interface for statistical scenario's
 */
public interface IStatsScenario {

   String getRbName();

   String getRbDescription();

   List<Player> getPlayers();

   StatsScenarioTypes getScenarioType();

}