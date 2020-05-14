/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.be.astron.assist.HorizontalPosition;
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

   private final double azimuth = 99.99;
   private final double altitude = -13.13;
   private CelestialObjects celObject = CelestialObjects.MOON;
   @Mock
   private HorizontalPosition horizontalPositionMock;
   private PresentableHorizontalPosition pos;

   @Before
   public void setUp() throws Exception {
      when(horizontalPositionMock.getAzimuth()).thenReturn(azimuth);
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