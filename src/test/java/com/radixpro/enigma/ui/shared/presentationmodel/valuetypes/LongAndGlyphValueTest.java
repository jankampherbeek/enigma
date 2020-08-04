/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class LongAndGlyphValueTest {

   private final String expectedPos = "14" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;
   private LongAndGlyphValue longAndGlyphValue;

   @Before
   public void setUp() {
      double longitude = 314.5;
      longAndGlyphValue = new LongAndGlyphValue(longitude);
   }

   @Test
   public void getFormattedPosition() {
      assertEquals(expectedPos, longAndGlyphValue.getFormattedPosition());
   }

   @Test
   public void getLongWithGlyph() {
      assertEquals(expectedPos, longAndGlyphValue.getLongWithGlyph().getPosition());
      int expectedSignIndex = 11;
      assertEquals(expectedSignIndex, longAndGlyphValue.getLongWithGlyph().getSignIndex());
   }
}