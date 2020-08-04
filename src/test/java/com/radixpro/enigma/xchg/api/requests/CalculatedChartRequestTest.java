/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatedChartRequestTest {

   @Mock
   private ChartCalcSettings settingsMock;
   @Mock
   private FullDateTime dateTimeMock;
   @Mock
   private Location locationMock;
   private CalculatedChartRequest request;

   @Before
   public void setUp() throws Exception {
      request = new CalculatedChartRequest(settingsMock, dateTimeMock, locationMock);
   }

   @Test
   public void getSettings() {
      assertEquals(settingsMock, request.getSettings());
   }

   @Test
   public void getDateTime() {
      assertEquals(dateTimeMock, request.getDateTime());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, request.getLocation());
   }

}