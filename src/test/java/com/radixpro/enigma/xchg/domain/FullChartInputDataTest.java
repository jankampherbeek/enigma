/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FullChartInputDataTest {

   private final int id = 123;
   @Mock
   private FullDateTime fullDateTimeMock;
   @Mock
   private Location locationMock;
   @Mock
   private ChartMetaData chartMetaDataMock;
   private FullChartInputData fullChartInputData;

   @Before
   public void setUp() {
      fullChartInputData = new FullChartInputData(id, fullDateTimeMock, locationMock, chartMetaDataMock);
   }

   @Test
   public void getSimpleDateTime() {
      assertEquals(fullDateTimeMock, fullChartInputData.getFullDateTime());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, fullChartInputData.getLocation());
   }

   @Test
   public void getId() {
      assertEquals(id, fullChartInputData.getId());
   }

   @Test
   public void getChartMetaData() {
      assertEquals(chartMetaDataMock, fullChartInputData.getChartMetaData());
   }

   @Test
   public void testToString() {
      assertEquals("ChartData(id=123, fullDateTime=fullDateTimeMock, location=locationMock, chartMetaData=chartMetaDataMock)", fullChartInputData.toString());
   }
}