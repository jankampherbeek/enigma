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
public class PresentableEquatorialPositionTest {

   private final CelestialObjects celestialObject = CelestialObjects.SATURN;
   private final double mainPos = 100.1000001;
   private final double devPos = -2.4000001;
   private final double mainSpeed = 0.8;
   private final double devSpeed = -.03;
   @Mock
   private CelObjectSinglePosition celObjectSinglePositionMock;
   private PresentableEquatorialPosition position;

   @Before
   public void setUp() throws Exception {
      when(celObjectSinglePositionMock.getMainPosition()).thenReturn(mainPos);
      when(celObjectSinglePositionMock.getDeviationPosition()).thenReturn(devPos);
      when(celObjectSinglePositionMock.getMainSpeed()).thenReturn(mainSpeed);
      when(celObjectSinglePositionMock.getDeviationSpeed()).thenReturn(devSpeed);
      position = new PresentableEquatorialPosition(celestialObject, celObjectSinglePositionMock);
   }

   @Test
   public void getFormattedRightAscension() {
      assertEquals("100°06′00″", position.getFormattedRightAscension());
   }

   @Test
   public void getFormattedRaSpeed() {
      assertEquals(" +0°48′00″", position.getFormattedRaSpeed());
   }

   @Test
   public void getFormattedDeclination() {
      assertEquals(" -2°24′00″", position.getFormattedDeclination());
   }

   @Test
   public void getFormattedDeclSpeed() {
      assertEquals(" -0°01′47″", position.getFormattedDeclSpeed());
   }

   @Test
   public void getCelBodyGlyph() {
      assertEquals("h", position.getCelBodyGlyph());
   }
}