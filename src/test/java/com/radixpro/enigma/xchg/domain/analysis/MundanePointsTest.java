/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MundanePointsTest {

   private MundanePoints mundanePoint;

   @Before
   public void setUp() {
      mundanePoint = MundanePoints.MC;
   }

   @Test
   public void getItemForId() {
      assertEquals(MundanePoints.VERTEX, mundanePoint.getItemForId(3));
   }

   @Test(expected = IllegalArgumentException.class)
   public void getItemForIdOutOfRange() {
      mundanePoint.getItemForId(1000);
   }

   @Test
   public void getPointType() {
      assertEquals(ChartPointTypes.MUNDANE_POINTS, mundanePoint.getPointType());
   }

   @Test
   public void getId() {
      assertEquals(1, mundanePoint.getId());
   }

   @Test
   public void getRbKey() {
      assertEquals("ui.shared.mc", mundanePoint.getRbKey());
   }
}