/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.handlers.BeHandlersInjector;
import com.radixpro.enigma.be.handlers.TimeKeyHandler;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.TimeKeys;
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.NAIBOD_KEY;
import static com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR;
import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TimeKeyHandlerTest {

   private DateTimeJulian birthDateTime;
   private DateTimeJulian eventDateTime;
   private Location location;
   private ProgSettings settings;
   private TimeKeyHandler handler;

   @Before
   public void setUp() {
      final double jd1 = 2420531.5;
      final double jd2 = jd1 + TROPICAL_YEAR;
      birthDateTime = new DateTimeJulian(jd1, "G");
      eventDateTime = new DateTimeJulian(jd2, "G");
      location = new Location(52.0, 7.0);
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.JUPITER);
      settings = new ProgSettings(points, Ayanamshas.NONE, false, false);
      handler = BeHandlersInjector.injectTimeKeyHandler();
   }

   @Test
   public void retrieveTimeSpanNaibod() throws UnknownTimeKeyException {
      final double timeSpan = handler.retrieveTimeSpan(birthDateTime, eventDateTime, TimeKeys.NAIBOD, location, settings);
      assertEquals(NAIBOD_KEY, timeSpan, DELTA_8_POS);
   }

   @Test
   public void retrieveTimesSpanRealSecSun() throws UnknownTimeKeyException {
      final double timeSpan = handler.retrieveTimeSpan(birthDateTime, eventDateTime, TimeKeys.REAL_SECUNDARY_SUN, location, settings);
      assertEquals(1.0139742637144877, timeSpan, DELTA_8_POS);
   }
}