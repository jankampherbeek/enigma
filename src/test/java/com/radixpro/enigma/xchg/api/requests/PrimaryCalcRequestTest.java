/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.TimeKeys;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PrimaryCalcRequestTest {

   @Mock
   private FullDateTime fullDateTimeProgMock;
   @Mock
   private FullDateTime fullDateTimeRadixMock;
   @Mock
   private ICalcSettings settingsMock;
   @Mock
   private Location locationMock;
   @Mock
   private CalculatedChart calculatedChartMock;
   private TimeKeys timeKey = TimeKeys.NAIBOD;
   private PrimaryCalcRequest request;

   @Before
   public void setUp() throws Exception {
      request = new PrimaryCalcRequest(fullDateTimeProgMock, fullDateTimeRadixMock, settingsMock, timeKey, locationMock, calculatedChartMock);
   }

   @Test
   public void getDateTime() {
      assertEquals(fullDateTimeProgMock, request.getDateTime());
   }

   @Test
   public void getSettings() {
      assertEquals(settingsMock, request.getSettings());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, request.getLocation());
   }

   @Test
   public void getDateTimeRadix() {
      assertEquals(fullDateTimeRadixMock, request.getDateTimeRadix());
   }

   @Test
   public void getTimeKey() {
      assertEquals(timeKey, request.getTimeKey());
   }

   @Test
   public void getCalculatedChart() {
      assertEquals(calculatedChartMock, request.getCalculatedChart());
   }
}