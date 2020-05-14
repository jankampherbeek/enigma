/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HousePositionTest {

   private static final double DELTA = 0.00000001;
   private final double longitude = 123.456;
   @Mock
   private EquatorialPosition equatorialPositionMock;
   @Mock
   private HorizontalPosition horizontalPositionMock;
   private HousePosition housePosition;

   @Before
   public void setUp() {
      housePosition = new HousePosition(longitude, equatorialPositionMock, horizontalPositionMock);
   }

   @Test
   public void getEquatorialPosition() {
      assertEquals(equatorialPositionMock, housePosition.getEquatorialPosition());
   }

   @Test
   public void getLongitude() {
      assertEquals(longitude, housePosition.getLongitude(), DELTA);
   }

   @Test
   public void getHorizontalPosition() {
      assertEquals(horizontalPositionMock, housePosition.getHorizontalPosition());
   }
}