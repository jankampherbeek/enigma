/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts;

import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.astronpos.FullChart;
import com.radixpro.enigma.domain.config.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionStateTest {

   @Mock
   private FullChart selectedChartMock1;
   @Mock
   private FullChart selectedChartMock2;
   @Mock
   private Configuration selectedConfigMock1;
   @Mock
   private Configuration selectedConfigMock2;
   private SessionState sessionState;

   @Before
   public void setUp() throws Exception {
      sessionState = SessionState.INSTANCE;
   }
   
   @Test
   public void selectedChart() {
      assertFalse(sessionState.selectedChartIsSet());
      sessionState.setSelectedChart(selectedChartMock1);
      assertTrue(sessionState.selectedChartIsSet());
      assertEquals(selectedChartMock1, sessionState.getSelectedChart());
      sessionState.setSelectedChart(selectedChartMock2);
      assertTrue(sessionState.selectedChartIsSet());
      assertEquals(selectedChartMock2, sessionState.getSelectedChart());
   }

   @Test
   public void selectedConfig() {
      assertFalse(sessionState.selectedConfigIsSet());
      sessionState.setSelectedConfig(selectedConfigMock1);
      assertTrue(sessionState.selectedConfigIsSet());
      assertEquals(selectedConfigMock1, sessionState.getSelectedConfig());
      sessionState.setSelectedConfig(selectedConfigMock2);
      assertTrue(sessionState.selectedConfigIsSet());
      assertEquals(selectedConfigMock2, sessionState.getSelectedConfig());
   }

}