/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis.handlers;

import com.radixpro.enigma.be.analysis.ProgRadixAspects;
import com.radixpro.enigma.xchg.api.requests.TransitsAnalyzeRequest;
import com.radixpro.enigma.xchg.api.responses.TransitsAspectResponse;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.calculatedcharts.ChartPositionsVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handler for analysing aspects from transits.
 */
public class TransitsAspectHandler {

   private final ProgRadixAspects progRadixAspects;

   public TransitsAspectHandler(final ProgRadixAspects progRadixAspects) {
      this.progRadixAspects = checkNotNull(progRadixAspects);
   }

   public TransitsAspectResponse analyzeAspects(final TransitsAnalyzeRequest request) {
      return analyze(request.getChartPositions(), request.getTransitPositions(), request.getAspects(), request.getOrb());
   }

   private TransitsAspectResponse analyze(ChartPositionsVo chartPositions, List<SimplePosVo> transitPositions, List<AspectTypes> aspectTypes, double orb) {
      long chartId = 1L;   // FIXME use real chartId
      List<IAnalyzedPair> aspects = new ArrayList<>();
      for (SimplePosVo trPos : transitPositions) {
         for (SimplePosVo rxBodyPos : chartPositions.getCelestialPoints()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxBodyPos, orb));
         }
         for (SimplePosVo rxMundPos : chartPositions.getMundanePositions()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxMundPos, orb));
         }
      }
      return new TransitsAspectResponse(chartId, aspects);
   }


}
