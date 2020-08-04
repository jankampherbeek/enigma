/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ChartDataTest {

   private final int id = 123;
   @Mock
   private FullDateTime fullDateTimeMock;
   @Mock
   private Location locationMock;
   @Mock
   private ChartMetaData chartMetaDataMock;
   private ChartData chartData;

   @Before
   public void setUp() {
      chartData = new ChartData(id, fullDateTimeMock, locationMock, chartMetaDataMock);
   }

   @Test
   public void getSimpleDateTime() {
      assertEquals(fullDateTimeMock, chartData.getFullDateTime());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, chartData.getLocation());
   }

   @Test
   public void getId() {
      assertEquals(id, chartData.getId());
   }

   @Test
   public void getChartMetaData() {
      assertEquals(chartMetaDataMock, chartData.getChartMetaData());
   }

   @Test
   public void testToString() {
      assertEquals("ChartData(id=123, fullDateTime=fullDateTimeMock, location=locationMock, chartMetaData=chartMetaDataMock)", chartData.toString());
   }
}