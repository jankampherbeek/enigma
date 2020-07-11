/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts;

import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ChartsSessionStateTest {

   @Mock
   private FullChart selectedChartMock1;
   @Mock
   private FullChart selectedChartMock2;
   @Mock
   private Configuration selectedConfigMock1;
   @Mock
   private Configuration selectedConfigMock2;
   private ChartsSessionState chartsSessionState;

   @Before
   public void setUp() throws Exception {
      chartsSessionState = ChartsSessionState.getInstance();
   }
   
   @Test
   public void selectedChart() {
      assertFalse(chartsSessionState.selectedChartIsSet());
      chartsSessionState.setSelectedChart(selectedChartMock1);
      assertTrue(chartsSessionState.selectedChartIsSet());
      assertEquals(selectedChartMock1, chartsSessionState.getSelectedChart());
      chartsSessionState.setSelectedChart(selectedChartMock2);
      assertTrue(chartsSessionState.selectedChartIsSet());
      assertEquals(selectedChartMock2, chartsSessionState.getSelectedChart());
   }

   @Test
   public void selectedConfig() {
      assertFalse(chartsSessionState.selectedConfigIsSet());
      chartsSessionState.setSelectedConfig(selectedConfigMock1);
      assertTrue(chartsSessionState.selectedConfigIsSet());
      assertEquals(selectedConfigMock1, chartsSessionState.getSelectedConfig());
      chartsSessionState.setSelectedConfig(selectedConfigMock2);
      assertTrue(chartsSessionState.selectedConfigIsSet());
      assertEquals(selectedConfigMock2, chartsSessionState.getSelectedConfig());
   }

}