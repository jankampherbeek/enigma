/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.JdFromPosCalc;
import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.CalculatedChartRequest;
import com.radixpro.enigma.domain.reqresp.CalculatedChartResponse;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.ObserverPositions;
import com.radixpro.enigma.references.SeFlags;
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException;
import com.radixpro.enigma.xchg.api.CalculatedChartApi;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Handler for the calculation of a solar return.
 */
public class SolarReturnHandler {

   private final JdFromPosCalc jdFromPosCalc;
   private final CalculatedChartApi calculatedChartApi;

   public SolarReturnHandler(@NotNull final JdFromPosCalc jdFromPosCalc,
                             @NotNull final CalculatedChartApi calculatedChartApi) {
      this.jdFromPosCalc = jdFromPosCalc;
      this.calculatedChartApi = calculatedChartApi;
   }

   /**
    * Create a solar chart.
    *
    * @param longSun       Longitude of the Sun at birth. PRE: 0.0 <= longSun < 360.0
    * @param birthDateTime Date and time at birth.
    * @param yearForReturn Year for which to find the solar return.
    * @param location      Location at birth or a relocated Location.
    * @param settings      Settings for the calculation.
    * @return The calculated solar return chart.
    * @throws NoPositionFoundException if the position of longSun cold not be found within 3 days bewfore and after the birthdate in yearForReturn.
    */
   public CalculatedChart getSolarReturnChart(final double longSun,
                                              @NotNull final DateTimeJulian birthDateTime,
                                              final int yearForReturn,
                                              @NotNull final Location location,
                                              @NotNull final ChartCalcSettings settings) throws NoPositionFoundException {
      checkArgument(longSun >= 0.0 && longSun < 360.0);
      return createSolarReturnChart(longSun, birthDateTime, yearForReturn, location, settings);
   }

   private CalculatedChart createSolarReturnChart(double longSun, DateTimeJulian birthDateTime, int age, Location location, ChartCalcSettings settings) throws NoPositionFoundException {
      final double startJd = birthDateTime.getJd() + (age * TROPICAL_YEAR) - 3.0;
      final double endJd = birthDateTime.getJd() + (age * TROPICAL_YEAR) + 3.0;
      final int flags = defineFlags(settings);
      double jdActual = jdFromPosCalc.findJd(startJd, endJd, longSun, CelestialObjects.SUN, flags, location);
      String cal = birthDateTime.getCalendar();
      DateTimeJulian actualDateTime = new DateTimeJulian(jdActual, cal);
      CalculatedChartRequest request = new CalculatedChartRequest(settings, actualDateTime, location);
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
