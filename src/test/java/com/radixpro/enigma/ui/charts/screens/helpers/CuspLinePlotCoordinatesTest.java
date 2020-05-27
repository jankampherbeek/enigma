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
public class CuspLinePlotCoordinatesTest {

   private static final double DELTA = 0.00000001;
   private final double[] coords = new double[]{1.0, 2.0, 3.0};
   @Mock
   private ChartDrawMetrics metricsMock;
   @Mock
   private RectTriangleAbsolute rectTriangleMock;
   private CuspLinePlotCoordinates cuspLineCoordinates;

   @Before
   public void setUp() throws Exception {
      when(metricsMock.getDiameterHousesCircle()).thenReturn(2.0);
      when(metricsMock.getDiameterSignsCircle()).thenReturn(3.0);
      when(rectTriangleMock.getCoordinates(anyDouble())).thenReturn(coords);
      cuspLineCoordinates = new CuspLinePlotCoordinates(rectTriangleMock);
   }

   @Test
   public void defineCoordinates() {
      assertEquals(coords[0], cuspLineCoordinates.defineCoordinates(1.0, metricsMock)[0], DELTA);
   }

}