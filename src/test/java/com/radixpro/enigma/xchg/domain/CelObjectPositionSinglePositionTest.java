/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.be.astron.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.astron.core.SeFrontend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CelObjectPositionSinglePositionTest {

   private final CelestialObjects celBodyToCalculate = CelestialObjects.MARS;
   private final double delta = 0.00000001;
   private final double[] positions = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6};
   private final String errorMsg = "Text";
   @Mock
   private Location locationMock;
   @Mock
   private SeFrontend seFrontendMock;
   @Mock
   private SePositionResultCelObjects sePositionResultMock;
   private CelObjectSinglePosition celObjectSinglePosition;

   @Before
   public void setUp() {
      when(sePositionResultMock.getAllPositions()).thenReturn(positions);
      when(sePositionResultMock.getErrorMsg()).thenReturn(errorMsg);
      when(seFrontendMock.getPositionsForCelBody(anyDouble(), anyInt(), anyInt(), any())).thenReturn(sePositionResultMock);
      int flags = 2;
      double jdUt = 123.456;
      celObjectSinglePosition = new CelObjectSinglePosition(seFrontendMock, jdUt, celBodyToCalculate, flags, locationMock);
   }

   @Test
   public void getErrorMsg() {
      assertEquals(errorMsg, celObjectSinglePosition.getErrorMsg());
   }

   @Test
   public void getMainPosition() {
      assertEquals(1.1, celObjectSinglePosition.getMainPosition(), delta);
   }

   @Test
   public void getDeviationPosition() {
      assertEquals(2.2, celObjectSinglePosition.getDeviationPosition(), delta);
   }

   @Test
   public void getDistancePosition() {
      assertEquals(3.3, celObjectSinglePosition.getDistancePosition(), delta);
   }

   @Test
   public void getMainSpeed() {
      assertEquals(4.4, celObjectSinglePosition.getMainSpeed(), delta);
   }

   @Test
   public void getDeviationSpeed() {
      assertEquals(5.5, celObjectSinglePosition.getDeviationSpeed(), delta);
   }

   @Test
   public void getDistanceSpeed() {
      assertEquals(6.6, celObjectSinglePosition.getDistanceSpeed(), delta);
   }
}