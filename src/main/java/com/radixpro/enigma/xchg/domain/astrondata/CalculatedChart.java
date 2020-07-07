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

   private final List<FullPointPosition> celPoints;
   private final List<MundanePosition> mundPoints;

   /**
    * Constructor defines all properties.
    *
    * @param celPoints  positions for celestial bodies. PRE: not null.
    * @param mundPoints positions for mundane points. PRE: not null.
    */
   public CalculatedChart(final List<FullPointPosition> celPoints, final List<MundanePosition> mundPoints) {
      this.celPoints = checkNotNull(celPoints);
      this.mundPoints = checkNotNull(mundPoints);
   }

   public List<FullPointPosition> getCelPoints() {
      return celPoints;
   }

   public List<MundanePosition> getMundPoints() {
      return mundPoints;
   }
}
