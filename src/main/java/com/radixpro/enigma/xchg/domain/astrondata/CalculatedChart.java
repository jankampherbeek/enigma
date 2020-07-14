/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Positions for a calculated chart.
 */
public class CalculatedChart {

   private final List<IPosition> celPoints;
   private final AllMundanePositions mundPoints;

   /**
    * Constructor defines all properties.
    *
    * @param celPoints  positions for celestial bodies. PRE: not null.
    * @param mundPoints positions for mundane points. PRE: not null.
    */
   public CalculatedChart(final List<IPosition> celPoints, final AllMundanePositions mundPoints) {
      this.celPoints = checkNotNull(celPoints);
      this.mundPoints = checkNotNull(mundPoints);
   }

   public List<IPosition> getCelPoints() {
      return celPoints;
   }

   public AllMundanePositions getMundPoints() {
      return mundPoints;
   }
}
