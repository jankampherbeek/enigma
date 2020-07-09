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
 * All positions for cusps and sepcific points (asc, vertex etc.).
 */
public class AllMundanePositions {

   private final List<MundanePosition> cusps;
   private final List<MundanePosition> specPoints;

   /**
    * Constructor defines all properties.
    *
    * @param cusps      cusps from 1 -- number of cusps for housesystem. PRE: not null.
    * @param specPoints specific points (Mc, Ascendant, Vertex, Eastpoint). PRE: not null.
    */
   public AllMundanePositions(final List<MundanePosition> cusps, final List<MundanePosition> specPoints) {
      this.cusps = checkNotNull(cusps);
      this.specPoints = checkNotNull(specPoints);
   }

   public List<MundanePosition> getCusps() {
      return cusps;
   }

   public List<MundanePosition> getSpecPoints() {
      return specPoints;
   }
}
