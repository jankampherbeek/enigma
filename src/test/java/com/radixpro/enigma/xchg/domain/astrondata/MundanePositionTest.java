/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import com.radixpro.enigma.xchg.domain.MundanePoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MundanePositionTest {

   private final MundanePoints mundanePoint = MundanePoints.EAST_POINT;
   private final double longitude = 99.999;
   @Mock
   private CoordinateSet eqPosMock;
   @Mock
   private CoordinateSet horPosMock;
   private MundanePosition mundanePosition;

   @Before
   public void setUp() {
      mundanePosition = new MundanePosition(mundanePoint, longitude, eqPosMock, horPosMock);
   }

   @Test
   public void getMundanePoint() {
      assertEquals(mundanePoint, mundanePosition.getMundanePoint());
   }

   @Test
   public void getLongitude() {
      assertEquals(longitude, mundanePosition.getLongitude(), DELTA_8_POS);
   }

   @Test
   public void getEqPos() {
      assertEquals(eqPosMock, mundanePosition.getEqPos());
   }

   @Test
   public void getHorPos() {
      assertEquals(horPosMock, mundanePosition.getHorPos());
   }
}