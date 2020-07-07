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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FullPointPositionTest {

   @Mock
   private FullPointCoordinate eclPosMock;
   @Mock
   private FullPointCoordinate eqPosMock;
   @Mock
   private FullPointCoordinate horPosMock;
   private CelestialObjects celObject = CelestialObjects.SUN;
   private FullPointPosition fullPointPosition;

   @Before
   public void setUp() {
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
}