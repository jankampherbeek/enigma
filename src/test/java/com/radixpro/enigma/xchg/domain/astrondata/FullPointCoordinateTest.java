/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FullPointCoordinateTest {

   @Mock
   private CoordinateSet3D positionMock;
   @Mock
   private CoordinateSet3D speedMock;
   private FullPointCoordinate fullPointCoordinate;

   @Before
   public void setUp() {
      fullPointCoordinate = new FullPointCoordinate(positionMock, speedMock);
   }

   @Test
   public void getPosition() {
      assertEquals(positionMock, fullPointCoordinate.getPosition());
   }

   @Test
   public void getSpeed() {
      assertEquals(speedMock, fullPointCoordinate.getSpeed());
   }
}