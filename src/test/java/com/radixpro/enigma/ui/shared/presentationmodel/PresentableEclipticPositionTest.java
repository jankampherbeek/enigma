/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointCoordinate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentableEclipticPositionTest {

   private final CelestialObjects celestialObject = CelestialObjects.SUN;
   @Mock
   private FullPointCoordinate fpCoordMock;
   private PresentableEclipticPosition position;

   @Before
   public void setUp() {
      double mainPos = 90.9;
      when(fpCoordMock.getPosition().getMainCoord()).thenReturn(mainPos);
      double devPos = -3.3;
      when(fpCoordMock.getPosition().getDeviation()).thenReturn(devPos);
      double mainSpeed = 0.5;
      when(fpCoordMock.getSpeed().getMainCoord()).thenReturn(mainSpeed);
      double devSpeed = 0.02;
      when(fpCoordMock.getSpeed().getDeviation()).thenReturn(devSpeed);
      position = new PresentableEclipticPosition(celestialObject, fpCoordMock);
   }

   @Test
   public void getFormattedLongitude() {
      assertEquals(" 0°54′00″", position.getFormattedLongitude());
   }

   @Test
   public void getFormattedLongSpeed() {
      assertEquals(" +0°30′00″", position.getFormattedLongSpeed());
   }

   @Test
   public void getFormattedLatitude() {
      assertEquals(" -3°17′59″", position.getFormattedLatitude());
   }

   @Test
   public void getFormattedLatSpeed() {
      assertEquals(" +0°01′11″", position.getFormattedLatSpeed());
   }

   @Test
   public void getSignGlyph() {
      assertEquals("4", position.getSignGlyph());
   }

   @Test
   public void getCelBodyGlyph() {
      assertEquals("a", position.getCelBodyGlyph());
   }
}