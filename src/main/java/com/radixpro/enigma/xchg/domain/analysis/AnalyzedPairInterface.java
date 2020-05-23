/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

/**
 * Interface for analyzed results for a pair of points
 */
public interface AnalyzedPairInterface {

   AnalyzablePoint getFirst();

   AnalyzablePoint getSecond();

   double getActualOrb();

   double getMaxOrb();

   double getPercOrb();

}
