/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Positions for a calculated chart.
 */
public class CalculatedChart {

   private final List<IPosition> celPoints;
   private final AllMundanePositions mundPoints;

   /**
    * Constructor defines all properties.
    *
    * @param celPoints  positions for celestial bodies.
    * @param mundPoints positions for mundane points.
    */
   public CalculatedChart(@NotNull final List<IPosition> celPoints,
                          @NotNull final AllMundanePositions mundPoints) {
      this.celPoints = celPoints;
      this.mundPoints = mundPoints;
   }

   public List<IPosition> getCelPoints() {
      return celPoints;
   }

   public AllMundanePositions getMundPoints() {
      return mundPoints;
   }
}
