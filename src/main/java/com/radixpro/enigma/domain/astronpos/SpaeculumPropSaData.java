/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import java.util.List;

/**
 * Spaeculum data for primary directions based on proportional semi-arcs.
 */
public class SpaeculumPropSaData {

   private final double raMcRx;
   private final List<SpaeculumPropSaItem> items;

   public SpaeculumPropSaData(final double raMcRx, final List<SpaeculumPropSaItem> items) {
      this.raMcRx = raMcRx;
      this.items = items;
   }

   public double getRaMcRx() {
      return raMcRx;
   }

   public List<SpaeculumPropSaItem> getItems() {
      return items;
   }
}
