/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.be.calc.assist.HorizontalPosition;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentableHorizontalPositionTest {

   private final CelestialObjects celObject = CelestialObjects.MOON;
   @Mock
   private HorizontalPosition horizontalPositionMock;
   private PresentableHorizontalPosition pos;

   @Before
   public void setUp() {
      double azimuth = 99.99;
      when(horizontalPositionMock.getAzimuth()).thenReturn(azimuth);
      double altitude = -13.13;
      when(horizontalPositionMock.getAltitude()).thenReturn(altitude);
      pos = new PresentableHorizontalPosition(celObject, horizontalPositionMock);
   }

   @Test
   public void getFormattedAzimuth() {
      assertEquals(" 99°59′23″", pos.getFormattedAzimuth());
   }

   @Test
   public void getFormattedAltitude() {
      assertEquals("-13°07′48″", pos.getFormattedAltitude());
   }

   @Test
   public void getCelBodyGlyph() {
      assertEquals("b", pos.getCelBodyGlyph());
   }
}