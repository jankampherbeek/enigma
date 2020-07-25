/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.assist.CoordSetForDateTimeCalc;
import com.radixpro.enigma.be.calc.assist.JdFromPosCalc;
import com.radixpro.enigma.be.calc.converters.CalcConvertersFactory;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.api.ApiFactory;
import swisseph.SweDate;

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

   public static ObliquityHandler getObliquityHandler() {
      return new ObliquityHandler(SeFrontend.getFrontend());
   }

   public static MundanePositionsHandler getMundanePositionsHandler() {
      return new MundanePositionsHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticalEquatorialConversions(), getObliquityHandler());
   }

   public static CalculatedChartHandler getCalculatedChartHandler() {
      return new CalculatedChartHandler(getFullPointPositionHandler(), getMundanePositionsHandler());
   }

   public static CoordSetForDateTimeCalc getCoordSetForDateTimeCalc() {
      return new CoordSetForDateTimeCalc(SeFrontend.getFrontend());
   }

   public static JdFromPosCalc getJdFromPosCalc() {
      return new JdFromPosCalc(getCoordSetForDateTimeCalc());
   }

   public static JulianDayHandler getJulianDayHandler() {
      return new JulianDayHandler(new SweDate());
   }

   public static EphProgCalcHandler getTransitsCalcHandler() {
      return new EphProgCalcHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticHorizontalConverter());
   }

   public static SolarReturnHandler getSolarReturnHandler() {
      return new SolarReturnHandler(getJdFromPosCalc(), ApiFactory.getCalculatedChartApi());
   }

   public static TetenburgHandler getTetenburgHandler() {
      return new TetenburgHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticalEquatorialConversions());
   }

   public static TimeKeyHandler getTimeKeyHandler() {
      return new TimeKeyHandler(new SecundaryDateHandler(), new CaHandlersFactory().getFullPointPositionHandler());   // TODO use factory for SecundaryDateHandler
   }

   public static PrimaryPositionsHandler getPrimaryPositionsHandler() {
      return new PrimaryPositionsHandler();
   }

   public static PrimaryHandler getPrimaryHandler() {
      return new PrimaryHandler(getPrimaryPositionsHandler(), getTimeKeyHandler());
   }
}
