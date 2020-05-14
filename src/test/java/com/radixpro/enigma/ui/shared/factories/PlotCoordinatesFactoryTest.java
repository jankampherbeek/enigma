/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.ui.charts.screens.helpers.ChartDrawMetrics;
import com.radixpro.enigma.ui.charts.screens.helpers.CuspLinePlotCoordinates;
import com.radixpro.enigma.ui.charts.screens.helpers.CuspTextPlotCoordinates;
import com.radixpro.enigma.ui.charts.screens.helpers.DegreeLinePlotCoordinates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlotCoordinatesFactoryTest {

   @Mock
   private ChartDrawMetrics metricsMock;

   @Before
   public void startUp() {
      when(metricsMock.getCorrForXY()).thenReturn(2.2);
   }

   @Test
   public void createCuspLinePlotCoordinates() {
      Object result = PlotCoordinatesFactory.createCuspLinePlotCoordinates(13.0, metricsMock);
      assertTrue(result instanceof CuspLinePlotCoordinates);
   }

   @Test
   public void createCuspTextPlotCoordinates() {
      Object result = PlotCoordinatesFactory.createCuspTextPlotCoordinates(22.2, metricsMock);
      assertTrue(result instanceof CuspTextPlotCoordinates);
   }

   @Test
   public void createDegreeLinePlotCoordinates() {
      Object result = PlotCoordinatesFactory.createDegreeLinePlotCoordinates(3.0, metricsMock);
      assertTrue(result instanceof DegreeLinePlotCoordinates);
   }
}