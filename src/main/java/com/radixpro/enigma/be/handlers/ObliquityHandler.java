/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.handlers.CaHandlersFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.SE_ECL_NUT;

/**
 * Obliquity of the earth-axis.
 */
public class ObliquityHandler {

   private final SeFrontend seFrontend;

   /**
    * Instantiate via Factory.
    *
    * @param seFrontend
    * @see CaHandlersFactory
    */
   public ObliquityHandler(final SeFrontend seFrontend) {
      this.seFrontend = checkNotNull(seFrontend);
   }

   /**
    * Calculate true obliquityy.
    *
    * @param jdUt julian day in UT.
    * @return calculated epsilon (obliquity).
    */
   public double calcTrueObliquity(final double jdUt) {
      return performCalculation(jdUt);
   }

   private double performCalculation(final double jdUt) {
      int flags = 0;
      final SePositionResultCelObjects calculatedPos = seFrontend.getPositionsForEpsilon(jdUt, SE_ECL_NUT, flags);
      return calculatedPos.getAllPositions()[0];
   }

}
