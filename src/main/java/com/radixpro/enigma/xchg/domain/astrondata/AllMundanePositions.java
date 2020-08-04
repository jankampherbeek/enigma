/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import com.radixpro.enigma.domain.astronpos.IPosition;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * All positions for cusps and sepcific points (asc, vertex etc.).
 */
public class AllMundanePositions {

   private final List<IPosition> cusps;
   private final List<IPosition> specPoints;

   /**
    * Constructor defines all properties.
    *
    * @param cusps      cusps from 1 -- number of cusps for housesystem. PRE: not null.
    * @param specPoints specific points (Mc, Ascendant, Vertex, Eastpoint). PRE: not null.
    */
   public AllMundanePositions(final List<IPosition> cusps, final List<IPosition> specPoints) {
      this.cusps = checkNotNull(cusps);
      this.specPoints = checkNotNull(specPoints);
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
