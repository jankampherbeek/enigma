/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis;

import com.radixpro.enigma.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.references.MidpointTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Calculate midpoints for radix.
 */
public class MidpointsForRadix {

   private List<IAnalyzedPair> results;

   public List<IAnalyzedPair> analyze(@NotNull final List<AnalyzablePoint> candidates) {
      checkArgument(1 < candidates.size());
      return performAnalysis(candidates);
   }

   private List<IAnalyzedPair> performAnalysis(List<AnalyzablePoint> candidates) {
      results = new ArrayList<>();
      int nrOfCandidates = candidates.size();
      for (int i = 0; i < nrOfCandidates; i++) {
         for (int j = i + 1; j < nrOfCandidates; j++) {
            AnalyzablePoint candidateFirst = candidates.get(i);
            AnalyzablePoint candidateSecond = candidates.get(j);
            checkCandidates(candidates, candidateFirst, candidateSecond);
         }
      }
      return results;
   }

   private void checkCandidates(List<AnalyzablePoint> candidates, AnalyzablePoint candidateFirst, AnalyzablePoint candidateSecond) {
      for (AnalyzablePoint centerCandidate : candidates) {
         boolean matchFound = false;
         for (MidpointTypes midpointType : MidpointTypes.values()) {
            if (!matchFound) {
               matchFound = checkForMatch(candidateFirst, candidateSecond, centerCandidate, midpointType);
            }
         }
      }
   }

   private boolean checkForMatch(AnalyzablePoint candidateFirst, AnalyzablePoint candidateSecond, AnalyzablePoint centerCandidate, MidpointTypes midpointType) {
      double maxOrbis = 1.6;
      double nrOfAnglesFirst = (int) (candidateFirst.getPosition() / midpointType.getAngle());
      double nrOfAnglesSecond = (int) (candidateSecond.getPosition() / midpointType.getAngle());
      double nrOfAnglesCenter = (int) (centerCandidate.getPosition() / midpointType.getAngle());
      double pos1 = candidateFirst.getPosition() - (nrOfAnglesFirst * midpointType.getAngle());
      double pos2 = candidateSecond.getPosition() - (nrOfAnglesSecond * midpointType.getAngle());
      double posCenter = centerCandidate.getPosition() - (nrOfAnglesCenter * midpointType.getAngle());
      double posNorm = (pos1 + pos2) / 2.0;
      if (posNorm > (midpointType.getAngle() / 2.0)) posNorm -= (midpointType.getAngle() / 2.0);
      double actOrbis = Math.abs(posCenter - posNorm);
      if (actOrbis >= midpointType.getAngle() / 4.0) actOrbis -= (midpointType.getAngle() / 2.0);
      actOrbis = Math.abs(actOrbis);
      if (actOrbis <= maxOrbis) {    // midpoint found
         results.add(new AnalyzedMidpoint(candidateFirst, candidateSecond, centerCandidate, midpointType, actOrbis, maxOrbis));
         return true;
      }
      return false;
   }

}

