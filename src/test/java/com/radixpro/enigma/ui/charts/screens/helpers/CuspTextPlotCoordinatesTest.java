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
public class CuspTextPlotCoordinatesTest {

   private static final double DELTA = 0.00000001;
   private final double[] coords = new double[]{1.0, 2.0, 3.0};
   @Mock
   private ChartIDrawMetrics metricsMock;
   @Mock
   private RectTriangleAbsolute rectTriangleMock;
   private CuspTextPlotCoordinates cuspTextCoordinates;

   @Before
   public void setUp() {
      when(rectTriangleMock.getCoordinates(anyDouble())).thenReturn(coords);
      cuspTextCoordinates = new CuspTextPlotCoordinates(rectTriangleMock);
   }

   @Test
   public void defineCoordinates() {   // use alle possible angles to assure coveragfe
      assertEquals(coords[1], cuspTextCoordinates.defineCoordinates(2.0, metricsMock)[1], DELTA);
      assertEquals(coords[1], cuspTextCoordinates.defineCoordinates(47.0, metricsMock)[1], DELTA);
      assertEquals(coords[1], cuspTextCoordinates.defineCoordinates(139.0, metricsMock)[1], DELTA);
      assertEquals(coords[1], cuspTextCoordinates.defineCoordinates(230.0, metricsMock)[1], DELTA);
      assertEquals(coords[1], cuspTextCoordinates.defineCoordinates(320.0, metricsMock)[1], DELTA);
   }
}