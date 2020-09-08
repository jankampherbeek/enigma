/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.DistanceTypes;
import com.radixpro.enigma.references.MinMaxType;

/**
 * Interface for definition of minimum and maximum positions to be used in statistics.
 */
public interface IStatsMinMaxRelations {

   MinMaxType getMinMaxType();

   DistanceTypes getDistanceType();

   double getSeparation();

}
