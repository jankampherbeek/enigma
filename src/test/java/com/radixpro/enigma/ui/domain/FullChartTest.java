/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.domain;

import com.radixpro.enigma.xchg.domain.ChartData;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FullChartTest {

   @Mock
   private ChartData chartDataMock;
   @Mock
   private CalculatedChart calculatedChartMock;
   private FullChart fullChart;

   @Before
   public void setUp() throws Exception {
      fullChart = new FullChart(chartDataMock, calculatedChartMock);
   }

   @Test
   public void getChartData() {
      assertEquals(chartDataMock, fullChart.getChartData());
   }

   @Test
   public void getCalculatedChart() {
      assertEquals(calculatedChartMock, fullChart.getCalculatedChart());
   }
}