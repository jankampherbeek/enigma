/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Response for analyzed transitary aspects.
 */
public class EphProgAspectResponse {

   private final long chartId;
   private final List<IAnalyzedPair> analyzedAspects;

   /**
    * Constructor defines all properies.
    *
    * @param chartId         Id for chart. PRE: >= 0.
    * @param analyzedAspects all aspects from transits. PRE: not null.
    */
   public EphProgAspectResponse(final long chartId, @NotNull final List<IAnalyzedPair> analyzedAspects) {
      checkArgument(chartId >= 0);
      this.chartId = chartId;
      this.analyzedAspects = analyzedAspects;
   }

   public long getChartId() {
      return chartId;
   }

   public List<IAnalyzedPair> getAnalyzedAspects() {
      return analyzedAspects;
   }
}
