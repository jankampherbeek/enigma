/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.main;

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.core.SeFrontend;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.SE_ECL_NUT;

/**
 * Obliquity of the earth-axis.
 */
public class Obliquity {

   private double trueObliquity;
   private double meanObliquity;

   public Obliquity(final SeFrontend seFrontend, final double jdUt) {
      checkNotNull(seFrontend);
      performCalculation(checkNotNull(seFrontend), jdUt);
   }

   /**
    * Constructor defines all members.
    *
    * @param seFrontend Instance (singleton) of SeFrontend.
    * @param jdUt       Julian day number for UT.
    */
   private void performCalculation(final SeFrontend seFrontend, final double jdUt) {
      int flags = 0;
      SePositionResultCelObjects calculatedPos = seFrontend.getPositionsForEpsilon(jdUt, SE_ECL_NUT, flags);
      trueObliquity = calculatedPos.getAllPositions()[0];
      meanObliquity = calculatedPos.getAllPositions()[1];
   }

   public double getTrueObliquity() {
      return this.trueObliquity;
   }

   public double getMeanObliquity() {
      return this.meanObliquity;
   }
}
