/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis.handlers;

import com.radixpro.enigma.be.analysis.AspectsForRadix;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.calculatedobjects.IObjectVo;
import com.radixpro.enigma.xchg.domain.config.AspectConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Prepares the analysis of aspects.
 */
public class AspectsHandler {

   private final AspectsForRadix analyzer;

   /**
    * Constructor should be called by AnalysisHandlerFactory.createAspectsHandler() .
    *
    * @param analyzer performs the real analysis.
    */
   public AspectsHandler(final AspectsForRadix analyzer) {
      this.analyzer = checkNotNull(analyzer);
   }

   /**
    * Find active aspects.
    *
    * @param celBodies     Celestial bodies to analyze. PRE: not null, size >= 2.
    * @param mundaneValues MundaneValues, uses MC and Asc. PRE: not null.
    * @param config        The configuration for aspects. PRE: not null.
    * @return actual aspects.
    */
   public List<IAnalyzedPair> retrieveAspects(final List<IObjectVo> celBodies,
                                              final List<IObjectVo> mundaneValues,
                                              final AspectConfiguration config) {
      checkArgument(celBodies != null && 2 <= celBodies.size());
      checkNotNull(mundaneValues);
      checkNotNull(config);
      List<AnalyzablePoint> candidates = createCandidates(celBodies, mundaneValues);
      return analyzer.analyze(candidates, config);

   }

   private List<AnalyzablePoint> createCandidates(List<IObjectVo> celBodies, List<IObjectVo> mundaneValues) {
      List<AnalyzablePoint> candidates = new ArrayList<>();
      for (IObjectVo pos : celBodies) {
         candidates.add(new AnalyzablePoint(pos.getChartPoint(), pos.getEclipticCoords().getPosition().getBase()));
      }
      for (IObjectVo pos : mundaneValues) {
         candidates.add(new AnalyzablePoint(pos.getChartPoint(), pos.getEclipticCoords().getPosition().getBase()));
      }
      return candidates;
   }


}
