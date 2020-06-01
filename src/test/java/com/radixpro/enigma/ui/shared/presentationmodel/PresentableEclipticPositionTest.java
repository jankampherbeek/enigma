/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.CelObjectSinglePosition;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
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
   private final double mainPos = 90.9;
   private final double devPos = -3.3;
   private final double mainSpeed = 0.5;
   private final double devSpeed = 0.02;
   @Mock
   private CelObjectSinglePosition celObjectSinglePositionMock;
   private PresentableEclipticPosition position;

   @Before
   public void setUp() {
      when(celObjectSinglePositionMock.getMainPosition()).thenReturn(mainPos);
      when(celObjectSinglePositionMock.getDeviationPosition()).thenReturn(devPos);
      when(celObjectSinglePositionMock.getMainSpeed()).thenReturn(mainSpeed);
      when(celObjectSinglePositionMock.getDeviationSpeed()).thenReturn(devSpeed);
      position = new PresentableEclipticPosition(celestialObject, celObjectSinglePositionMock);
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