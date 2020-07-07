/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;


import com.radixpro.enigma.xchg.domain.analysis.ChartPointTypes;

import static com.google.common.base.Preconditions.checkNotNull;

public enum MundanePoints implements IChartPoints {
   MC(1, "ui.shared.mc"),
   ASC(2, "ui.shared.asc"),
   VERTEX(3, "ui.shared.vertex"),
   ANTI_VERTEX(4, "ui.shared.antivertex"),
   EAST_POINT(5, "ui.shared.eastpoint"),
   CUSP(6, "ui.shared.cusps");

   private final int id;
   private final String rbKey;
   private final ChartPointTypes pointType;

   MundanePoints(final int id, final String rbKey) {
      this.id = id;
      this.rbKey = checkNotNull(rbKey);
      this.pointType = ChartPointTypes.MUNDANE_POINTS;
   }

   @Override
   public IChartPoints getItemForId(final int id) {
      for (MundanePoints point : MundanePoints.values()) {
         if (point.getId() == id) {
            return point;
         }
      }
      throw new IllegalArgumentException("Could not find MundanePoint for id : " + id);
   }

   @Override
   public ChartPointTypes getPointType() {
      return pointType;
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public String getRbKey() {
      return rbKey;
   }


}
