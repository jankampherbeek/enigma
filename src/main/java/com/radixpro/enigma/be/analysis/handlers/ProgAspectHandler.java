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
import com.radixpro.enigma.xchg.domain.calculatedcharts.ChartPositionsVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handler for analysing aspects from progressive positions.
 */
public class ProgAspectHandler {

   private final ProgRadixAspects progRadixAspects;

   public ProgAspectHandler(final ProgRadixAspects progRadixAspects) {
      this.progRadixAspects = checkNotNull(progRadixAspects);
   }

   public EphProgAspectResponse analyzeAspects(final ProgAnalyzeRequest request) {
      return analyze(request.getChartPositions(), request.getProgPositions(), request.getAspects(), request.getOrb());
   }

   private EphProgAspectResponse analyze(ChartPositionsVo chartPositions, List<SimplePosVo> progPositions, List<AspectTypes> aspectTypes, double orb) {
      long chartId = 1L;   // FIXME use real chartId
      List<IAnalyzedPair> aspects = new ArrayList<>();
      for (SimplePosVo trPos : progPositions) {
         for (SimplePosVo rxBodyPos : chartPositions.getCelestialPoints()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxBodyPos, orb));
         }
         for (SimplePosVo rxMundPos : chartPositions.getMundanePositions()) {
            aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxMundPos, orb));
         }
      }
      return new EphProgAspectResponse(chartId, aspects);
   }


}
