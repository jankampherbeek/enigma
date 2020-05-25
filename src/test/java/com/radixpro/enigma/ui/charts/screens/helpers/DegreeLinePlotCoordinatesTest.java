/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DegreeLinePlotCoordinatesTest {

   private static final double DELTA = 0.00000001;
   private final double[] coords = new double[]{1.1, 2.2, 3.3};
   @Mock
   private ChartIDrawMetrics metricsMock;
   @Mock
   private RectTriangleAbsolute rectTriangleMock;
   private DegreeLinePlotCoordinates degreeLineCoordinates;

   @Before
   public void setUp() throws Exception {
      when(metricsMock.getDiameterDegrees5Circle()).thenReturn(100.0);
      when(metricsMock.getDiameterDegreesCircle()).thenReturn(101.0);
      when(metricsMock.getDiameterSignsCircle()).thenReturn(102.0);
      when(rectTriangleMock.getCoordinates(anyDouble())).thenReturn(coords);
      degreeLineCoordinates = new DegreeLinePlotCoordinates(rectTriangleMock);
   }

   @Test
   public void defineCoordinates() {
      assertEquals(coords[2], degreeLineCoordinates.defineCoordinates(1, metricsMock)[2], DELTA);
      assertEquals(coords[1], degreeLineCoordinates.defineCoordinates(5, metricsMock)[1], DELTA);
   }
}