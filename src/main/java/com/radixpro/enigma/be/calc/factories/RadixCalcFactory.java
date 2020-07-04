/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.factories;

import com.radixpro.enigma.be.calc.handlers.JulianDayHandler;
import swisseph.SweDate;

/**
 * Factory for handlers and assists that take care of calculations for radix postions.
 */
public class RadixCalcFactory {

   public JulianDayHandler getJulianDayHandler() {
      return new JulianDayHandler(new SweDate());
   }

}
