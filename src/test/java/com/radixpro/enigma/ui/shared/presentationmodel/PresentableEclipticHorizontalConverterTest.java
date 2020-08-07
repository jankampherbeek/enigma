/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.references.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PresentableEclipticHorizontalConverterTest {

   private final CelestialObjects celObject = CelestialObjects.MOON;
   private double[] azAlt = {99.99, -13.13};
   private PresentableHorizontalPosition pos;

   @Before
   public void setUp() {
      pos = new PresentableHorizontalPosition(celObject, azAlt);
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