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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlotBodyInfoComparatorTest {

   @Mock
   private PlotBodyInfo plotBodyInfoMock1;
   @Mock
   private PlotBodyInfo plotBodyInfoMock2;
   private final PlotBodyInfoComparator comparator = new PlotBodyInfoComparator();

   @Before
   public void setUp() {
      when(plotBodyInfoMock1.getAngleFromAsc()).thenReturn(124.0);
      when(plotBodyInfoMock2.getAngleFromAsc()).thenReturn(123.0);
   }

   @Test
   public void compareFirstGreater() {
      assertTrue(comparator.compare(plotBodyInfoMock1, plotBodyInfoMock2) > 0);
   }

   @Test
   public void compareSecondGreater() {
      assertTrue(comparator.compare(plotBodyInfoMock2, plotBodyInfoMock1) < 0);
   }

}