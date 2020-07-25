/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.prog;

import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.shared.EnigmaDictionary.NAIBOD_KEY;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;
import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeKeyHandlerTest {

   private final double jd1 = 2420531.5;
   private final double jd2 = jd1 + TROPICAL_YEAR;
   @Mock
   private SimpleDate dateMock;
   @Mock
   private SimpleDateTime dateTimeMock;
   @Mock
   private FullDateTime birthDateTimeMock;
   @Mock
   private FullDateTime eventDateTimeMock;
   @Mock
   private Location locationMock;
   @Mock
   private ProgSettings settingsMock;
   private TimeKeyHandler handler;

   @Before
   public void setUp() throws Exception {
      when(dateMock.isGregorian()).thenReturn(true);
      when(dateTimeMock.getDate()).thenReturn(dateMock);
      when(birthDateTimeMock.getSimpleDateTime()).thenReturn(dateTimeMock);
      when(birthDateTimeMock.getJdUt()).thenReturn(jd1);
      when(eventDateTimeMock.getJdUt()).thenReturn(jd2);
      when(locationMock.getGeoLat()).thenReturn(52.0);
      when(settingsMock.getAyamsha()).thenReturn(Ayanamshas.NONE);
      when(settingsMock.isSidereal()).thenReturn(false);
      when(settingsMock.isTopocentric()).thenReturn(false);
      handler = new HandlerProgFactory().getTimeKeyHandler();
   }

   @Test
   public void retrieveTimeSpanNaibod() throws UnknownTimeKeyException {
      final double timeSpan = handler.retrieveTimeSpan(birthDateTimeMock, eventDateTimeMock, TimeKeys.NAIBOD, locationMock, settingsMock);
      assertEquals(NAIBOD_KEY, timeSpan, DELTA_8_POS);
   }

   @Test
   public void retrieveTimesSpanRealSecSun() throws UnknownTimeKeyException {
      final double timeSpan = handler.retrieveTimeSpan(birthDateTimeMock, eventDateTimeMock, TimeKeys.REAL_SECUNDARY_SUN, locationMock, settingsMock);
      assertEquals(1.0139742637144877, timeSpan, DELTA_8_POS);
   }
}