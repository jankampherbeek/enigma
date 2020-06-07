/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis;

import com.radixpro.enigma.xchg.domain.AspectCategory;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedAspectTransit;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds aspects between a origressive position and a radix position.
 */
public class ProgRadixAspects {

   public List<IAnalyzedPair> findAspects(List<AspectTypes> aspectTypes, SimplePosVo candidateProg, SimplePosVo candidateRx, double orb) {
      double pos1 = Math.min(candidateProg.getLongitude(), candidateRx.getLongitude());
      double pos2 = Math.max(candidateProg.getLongitude(), candidateRx.getLongitude());
      double distance1 = pos2 - pos1;
      double distance2 = pos1 - pos2 + 360.0;
      List<IAnalyzedPair> aspects = new ArrayList<>();
      for (AspectTypes asp : aspectTypes) {
         double angle = asp.getAngle();
         if (asp.getAspectCategory() != AspectCategory.DECLINATION) {          // TODO support parallel and contra-parallel
            double actualOrb = 360.0;
            boolean found = false;
            if (Math.abs(distance1 - angle) <= orb) {
               actualOrb = Math.abs(distance1 - angle);
               found = true;
            } else if (Math.abs(distance2 - angle) < orb) {
               actualOrb = Math.abs(distance2 - angle);
               found = true;
            }
            if (found) {
               AnalyzablePoint pointTransit = new AnalyzablePoint(candidateProg.getPoint(), candidateProg.getLongitude());
               AnalyzablePoint pointRadix = new AnalyzablePoint(candidateRx.getPoint(), candidateRx.getLongitude());
               aspects.add(new AnalyzedAspectTransit(pointTransit, pointRadix, asp, actualOrb, orb));
            }
         }
      }
      return aspects;
   }

}
