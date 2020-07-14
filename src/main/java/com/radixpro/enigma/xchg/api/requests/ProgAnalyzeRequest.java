/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.ProgAnalysisType;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Request for analyzing aspects between progressive positions and radix.
 */
public class ProgAnalyzeRequest {

   private final ProgAnalysisType type;
   private final List<IPosition> progPositions;
   private final CalculatedChart calculatedChart;
   private final List<AspectTypes> aspects;
   private final double orb;

   /**
    * Constructor defines all properties.
    *
    * @param type            typeof analysis. PRE: not null.
    * @param progPositions   progressive positions that need to be analyzed. PRE: not null and not empty.
    * @param calculatedChart positions of the chart. PRE: not null.
    * @param aspects         aspects that need to be used. PRE: not null and not empty.
    * @param orb             the orb to use during analysis. PRE: orb > 0.0 .
    */
   public ProgAnalyzeRequest(final ProgAnalysisType type, final List<IPosition> progPositions, final CalculatedChart calculatedChart,
                             final List<AspectTypes> aspects, final double orb) {
      checkArgument(orb > 0.0);
      checkArgument(null != progPositions && !progPositions.isEmpty());
      checkArgument(null != aspects && !aspects.isEmpty());
      this.type = checkNotNull(type);
      this.progPositions = progPositions;
      this.calculatedChart = checkNotNull(calculatedChart);
      this.aspects = aspects;
      this.orb = orb;
   }

   public ProgAnalysisType getType() {
      return type;
   }

   public List<IPosition> getProgPositions() {
      return progPositions;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }

   public List<AspectTypes> getAspects() {
      return aspects;
   }

   public double getOrb() {
      return orb;
   }
}
