/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis;

import com.radixpro.enigma.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.domain.analysis.AnalyzedAspect;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.config.AspectConfiguration;
import com.radixpro.enigma.domain.config.ConfiguredAspect;
import com.radixpro.enigma.references.AspectCategory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Calculates aspects for radix positions.
 */
public class AspectsForRadix {

   /**
    * Calculate aspects between multiple points.
    *
    * @param candidates The points where aspects will be defined for. PRE: not null, size > 1.
    * @param config     Configuration for aspects. PRE: not null.
    * @return the calculated aspects.
    */
   public List<IAnalyzedPair> analyze(@NotNull final List<AnalyzablePoint> candidates, @NotNull final AspectConfiguration config) {
      checkArgument(1 < candidates.size());
      return performAnalysis(candidates, config);
   }

   private List<IAnalyzedPair> performAnalysis(List<AnalyzablePoint> candidates, AspectConfiguration config) {
      List<IAnalyzedPair> results = new ArrayList<>();
      int nrOfCandidates = candidates.size();
      for (int i = 0; i < nrOfCandidates; i++) {
         for (int j = i + 1; j < nrOfCandidates; j++) {
            AnalyzablePoint candidateFirst = candidates.get(i);
            AnalyzablePoint candidateSecond = candidates.get(j);
            double pos1 = Math.min(candidateFirst.getPosition(), candidateSecond.getPosition());
            double pos2 = Math.max(candidateFirst.getPosition(), candidateSecond.getPosition());
            double distance1 = pos2 - pos1;
            double distance2 = pos1 - pos2 + 360.0;
            checkCandidates(config, results, candidateFirst, candidateSecond, distance1, distance2);
         }
      }
      return results;
   }

   private void checkCandidates(AspectConfiguration config, List<IAnalyzedPair> results,
                                AnalyzablePoint candidateFirst, AnalyzablePoint candidateSecond,
                                double distance1, double distance2) {
      for (ConfiguredAspect configAspect : config.getAspects()) {
         if (configAspect.getAspect().getAspectCategory() != AspectCategory.DECLINATION) {      // TODO support parallel and contra-parallel
            double effectiveMaxOrb = (configAspect.getOrbPercentage() * config.getBaseOrb()) / 100.0;
            double angle = configAspect.getAspect().getAngle();
            double actualOrb = 360.0;
            boolean found = false;
            if (Math.abs(distance1 - angle) <= effectiveMaxOrb) {
               actualOrb = Math.abs(distance1 - angle);
               found = true;
            } else if (Math.abs(distance2 - angle) < effectiveMaxOrb) {
               actualOrb = Math.abs(distance2 - angle);
               found = true;
            }
            if (found) results.add(new AnalyzedAspect(candidateFirst, candidateSecond, configAspect.getAspect(),
                  actualOrb, effectiveMaxOrb));
         }
      }
   }

}
