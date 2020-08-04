/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.analysis.MidpointsForRadix;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Prepares the analysis of midpoints.
 */
public class MidpointsHandler {

   private final MidpointsForRadix analyzer;

   /**
    * Constructor should be called by AnalysisHandlerFactory.createAspectsHandler() .
    *
    * @param analyzer performs the real analysis.
    */
   public MidpointsHandler(final MidpointsForRadix analyzer) {
      this.analyzer = checkNotNull(analyzer);
   }

   /**
    * Find active midpoints.
    *
    * @param celBodies     Celestial bodies to analyze. PRE: not null, size >= 2.
    * @param mundaneValues MundaneValues, uses MC and Asc. PRE: not null.
    * @return actual midpoints.
    */
   public List<IAnalyzedPair> retrieveMidpoints(final List<IPosition> celBodies, final List<IPosition> mundaneValues) {
      checkArgument(celBodies != null && 2 <= celBodies.size());
      checkNotNull(mundaneValues);
      List<AnalyzablePoint> candidates = createCandidates(celBodies, mundaneValues);
      return analyzer.analyze(candidates);
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

