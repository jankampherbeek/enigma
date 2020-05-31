/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class AnalyzablePointTest {

   private final double position = 123.456;
   private IChartPoints chartPoint;
   private AnalyzablePoint analyzablePoint;

   @Before
   public void setUp() throws Exception {
      chartPoint = new IChartPointsFake();
      analyzablePoint = new AnalyzablePoint(chartPoint, position);
   }

   @Test
   public void getPosition() {
      assertEquals(position, analyzablePoint.getPosition(), DELTA_8_POS);
   }

   @Test
   public void getChartPoint() {
      assertEquals(chartPoint, analyzablePoint.getChartPoint());
   }
}