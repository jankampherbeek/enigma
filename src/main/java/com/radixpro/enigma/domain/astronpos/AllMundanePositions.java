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
 * All positions for cusps and sepcific points (asc, vertex etc.).
 */
public class AllMundanePositions {

   private final List<IPosition> cusps;
   private final List<IPosition> specPoints;

   /**
    * Constructor defines all properties.
    *
    * @param cusps      cusps from 1 -- number of cusps for housesystem.
    * @param specPoints specific points (Mc, Ascendant, Vertex, Eastpoint).
    */
   public AllMundanePositions(@NotNull final List<IPosition> cusps, @NotNull final List<IPosition> specPoints) {
      this.cusps = cusps;
      this.specPoints = specPoints;
   }

   public List<IPosition> getCusps() {
      return cusps;
   }

   public List<IPosition> getSpecPoints() {
      return specPoints;
   }

   public IPosition getAsc() {
      return specPoints.get(0);
   }

   public IPosition getMc() {
      return specPoints.get(1);
   }

   public IPosition getVertex() {
      return specPoints.get(2);
   }

   public IPosition getEastPoint() {
      return specPoints.get(3);
   }

}
