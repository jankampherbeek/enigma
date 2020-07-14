/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis.handlers;

import com.radixpro.enigma.be.analysis.ProgRadixAspects;
import com.radixpro.enigma.xchg.api.requests.ProgAnalyzeRequest;
import com.radixpro.enigma.xchg.api.responses.EphProgAspectResponse;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handler for analysing aspects from progressive positions.
 * TODO: replace chartPositions with calculatedChart and progPositions with List for iPosition.
 */
public class ProgAspectHandler {

   private final ProgRadixAspects progRadixAspects;

   public ProgAspectHandler(final ProgRadixAspects progRadixAspects) {
      this.progRadixAspects = checkNotNull(progRadixAspects);
   }

   public EphProgAspectResponse analyzeAspects(final ProgAnalyzeRequest request) {
      return analyze(request.getCalculatedChart(), request.getProgPositions(), request.getAspects(), request.getOrb());
   }

   private EphProgAspectResponse analyze(CalculatedChart calcChart, List<IPosition> progPositions, List<AspectTypes> aspectTypes, double orb) {
      long chartId = 1L;   // FIXME use real chartId
      List<IAnalyzedPair> aspects = new ArrayList<>();
      for (IPosition trPos : progPositions) {
         for (IPosition rxBodyPos : calcChart.getCelPoints()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxBodyPos, orb));
         }
         for (IPosition rxMundPos : calcChart.getMundPoints().getSpecPoints()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxMundPos, orb));
         }
      }
      return new EphProgAspectResponse(chartId, aspects);
   }


}
