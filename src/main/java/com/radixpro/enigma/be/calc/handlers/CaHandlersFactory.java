/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.CoordSetForDateTimeCalc;
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.handlers.FullPointPositionHandler;

/**
 * Factory for handlers that take care of calculations.
 */
public class CaHandlersFactory {

   private CaHandlersFactory() {
      // prevent instantiation
   }

   public static FullPointPositionHandler getFullPointPositionHandler() {
      return new FullPointPositionHandler(SeFrontend.getFrontend());
   }

   public static CoordSetForDateTimeCalc getCoordSetForDateTimeCalc() {
      return new CoordSetForDateTimeCalc(SeFrontend.getFrontend());
   }


}
