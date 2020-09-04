/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.analysis.AspectsForRadix;
import com.radixpro.enigma.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.config.AspectConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

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
   public AspectsHandler(@NotNull final AspectsForRadix analyzer) {
      this.analyzer = analyzer;
   }

   /**
    * Find active aspects.
    *
    * @param celBodies     Celestial bodies to analyze. PRE: size >= 2.
    * @param mundaneValues MundaneValues, uses MC and Asc.
    * @param config        The configuration for aspects.
    * @return actual aspects.
    */
   public List<IAnalyzedPair> retrieveAspects(@NotNull final List<IPosition> celBodies,
                                              @NotNull final List<IPosition> mundaneValues,
                                              @NotNull final AspectConfiguration config) {
      checkArgument(2 <= celBodies.size());
      List<AnalyzablePoint> candidates = createCandidates(celBodies, mundaneValues);
      return analyzer.analyze(candidates, config);

   }

   private List<AnalyzablePoint> createCandidates(List<IPosition> celBodies, List<IPosition> mundaneValues) {
      List<AnalyzablePoint> candidates = new ArrayList<>();
      for (IPosition pos : celBodies) {
         candidates.add(new AnalyzablePoint(pos.getChartPoint(), pos.getLongitude()));
      }
      for (IPosition pos : mundaneValues) {
         candidates.add(new AnalyzablePoint(pos.getChartPoint(), pos.getLongitude()));
      }
      return candidates;
   }


}
