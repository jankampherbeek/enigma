/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FullPointPositionTest {

   @Mock
   private FullPointCoordinate eclPosMock;
   @Mock
   private FullPointCoordinate eqPosMock;
   private final CelestialObjects celObject = CelestialObjects.SUN;
   @Mock
   private CoordinateSet horPosMock;
   private final double longitude = 123.456;
   private final double declination = -2.22;
   @Mock
   private CoordinateSet3D eclPos3DMock;
   @Mock
   private CoordinateSet3D eqPos3DMock;
   @Mock
   private FullPointPosition fullPointPositionMock;
   private FullPointPosition fullPointPosition;

   @Before
   public void setUp() {
      when(eclPos3DMock.getMainCoord()).thenReturn(longitude);
      when(eclPosMock.getPosition()).thenReturn(eclPos3DMock);
      when(eqPos3DMock.getDeviation()).thenReturn(declination);
      when(eqPosMock.getPosition()).thenReturn(eqPos3DMock);
      fullPointPosition = new FullPointPosition(celObject, eclPosMock, eqPosMock, horPosMock);
   }

   @Test
   public void getEclPos() {
      assertEquals(eclPosMock, fullPointPosition.getEclPos());
   }

   @Test
   public void getEqPos() {
      assertEquals(eqPosMock, fullPointPosition.getEqPos());
   }

   @Test
   public void getHorPos() {
      assertEquals(horPosMock, fullPointPosition.getHorPos());
   }

   @Test
   public void getCelObject() {
      assertEquals(celObject, fullPointPosition.getCelObject());
   }

   @Test
   public void getLongitude() {
      assertEquals(longitude, fullPointPosition.getLongitude(), DELTA_8_POS);
   }

   @Test
   public void getDeclination() {
      assertEquals(declination, fullPointPosition.getDeclination(), DELTA_8_POS);
   }
}