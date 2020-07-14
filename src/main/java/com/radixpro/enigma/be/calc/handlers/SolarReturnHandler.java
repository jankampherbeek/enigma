/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.be.calc.assist.JdFromPosCalc;
import com.radixpro.enigma.be.calc.factories.RadixCalcFactory;
import com.radixpro.enigma.be.exceptions.NoPositionFoundException;
import com.radixpro.enigma.xchg.api.CalculatedChartApi;
import com.radixpro.enigma.xchg.api.requests.CalculatedChartRequest;
import com.radixpro.enigma.xchg.api.responses.CalculatedChartResponse;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Handler for the calculation of a solar return.
 */
public class SolarReturnHandler {

   private final JdFromPosCalc jdFromPosCalc;
   private final CalculatedChartApi calculatedChartApi;

   /**
    * Initialisation via ProgCalcFactory.
    *
    * @param jdFromPosCalc Instance of JdPosFromCalc
    * @see com.radixpro.enigma.be.calc.factories.ProgCalcFactory
    */
   public SolarReturnHandler(final JdFromPosCalc jdFromPosCalc, final CalculatedChartApi calculatedChartApi) {
      this.jdFromPosCalc = checkNotNull(jdFromPosCalc);
      this.calculatedChartApi = calculatedChartApi;
   }

   /**
    * Create a solar chart.
    *
    * @param longSun       Longitude of the Sun at birth. PRE: 0.0 <= longSun < 360.0
    * @param birthDateTime Date and time at birth. PRE: not null.
    * @param yearForReturn Year for which to find the solar return.
    * @param location      Location at birth or a relocated Location. PRE: not null.
    * @param settings      Settings for the calculation. PRE: not null.
    * @return The calculated solar return chart.
    * @throws NoPositionFoundException if the position of longSun cold not be found within 3 days bewfore and after the birthdate in yearForReturn.
    */
   public CalculatedChart getSolarReturnChart(final double longSun, final FullDateTime birthDateTime, final int yearForReturn, final Location location,
                                              final ChartCalcSettings settings) throws NoPositionFoundException {
      checkNotNull(birthDateTime);
      checkNotNull(location);
      checkNotNull(settings);
      checkArgument(longSun >= 0.0 && longSun < 360.0);
      return createSolarReturnChart(longSun, birthDateTime, yearForReturn, location, settings);
   }

   private CalculatedChart createSolarReturnChart(double longSun, FullDateTime birthDateTime, int yearForReturn, Location location, ChartCalcSettings settings) throws NoPositionFoundException {
      final int age = yearForReturn - birthDateTime.getSimpleDateTime().getDate().getYear();
      final double startJd = birthDateTime.getJdUt() + (age * TROPICAL_YEAR) - 3.0;
      final double endJd = birthDateTime.getJdUt() + (age * TROPICAL_YEAR) + 3.0;
      final int flags = defineFlags(settings);
      double jdActual = jdFromPosCalc.findJd(startJd, endJd, longSun, CelestialObjects.SUN, flags, location);
      boolean gregCal = birthDateTime.getSimpleDateTime().getDate().isGregorian();
      final SimpleDateTime actualSimpleDateTime = new RadixCalcFactory().getJulianDayHandler().dateTimeFromJd(jdActual, gregCal, "ut");
      final FullDateTime actualFullDateTime = new FullDateTime(actualSimpleDateTime, TimeZones.UT, birthDateTime.isDst(), birthDateTime.getOffsetForLmt());
      CalculatedChartRequest request = new CalculatedChartRequest(settings, actualFullDateTime, location);
      final CalculatedChartResponse response = calculatedChartApi.calcChart(request);
      if (!"OK".equals(response.getResultMsg())) {
         throw new NoPositionFoundException(response.getResultMsg());
      }
      return response.getCalculatedChart();
   }

   private int defineFlags(final ChartCalcSettings settings) {
      List<SeFlags> allFlags = new ArrayList<>();
      allFlags.add(SeFlags.SWISSEPH);
      if (EclipticProjections.SIDEREAL == settings.getEclProj()) {
         allFlags.add(SeFlags.SIDEREAL);
      }
      if (ObserverPositions.TOPOCENTRIC == settings.getObsPos()) {
         allFlags.add(SeFlags.TOPOCENTRIC);
      }
      return (int) new CombinedFlags().getCombinedValue(allFlags);
   }

}
