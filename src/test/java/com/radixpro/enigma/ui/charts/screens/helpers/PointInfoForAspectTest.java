/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class PointInfoForAspectTest {

   private final double angleFromAsc = 33.5;
   private IChartPoints point;
   private PointInfoForAspect pointInfo;

   @Before
   public void setUp() {
      point = CelestialObjects.SUN;
      pointInfo = new PointInfoForAspect(point, angleFromAsc);
   }

   @Test
   public void getPoint() {
      assertEquals(point, pointInfo.getPoint());
   }

   @Test
   public void getAngleFromAsc() {
      assertEquals(angleFromAsc, pointInfo.getAngleFromAsc(), DELTA_8_POS);
   }
}