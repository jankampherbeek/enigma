/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedcharts;

import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * VO with simple chart positions.
 * TODO remove obsolete class
 */
public class ChartPositionsVo {

   private final long chartId;
   private final List<SimplePosVo> celestialPoints;
   private final List<SimplePosVo> mundanePositions;

   /**
    * Constructor defines all properties.
    *
    * @param chartId          Id for the chart. PRE: id > 0. Chart with this id should also exist but this is not checked.
    * @param celestialPoints  positions for celestial popints. PRE: not null. May be empty if mundanePositions is no empty.
    * @param mundanePositions positions for house related points. PRE: not null. May be empty if celestialPOints is not empty.
    */
   public ChartPositionsVo(final long chartId, final List<SimplePosVo> celestialPoints, final List<SimplePosVo> mundanePositions) {
      checkArgument(chartId > 0L);
      checkArgument(!(celestialPoints.isEmpty() && mundanePositions.isEmpty()));
      this.chartId = chartId;
      this.celestialPoints = checkNotNull(celestialPoints);
      this.mundanePositions = checkNotNull(mundanePositions);
   }

   public long getChartId() {
      return chartId;
   }

   public List<SimplePosVo> getCelestialPoints() {
      return celestialPoints;
   }

   public List<SimplePosVo> getMundanePositions() {
      return mundanePositions;
   }
}
