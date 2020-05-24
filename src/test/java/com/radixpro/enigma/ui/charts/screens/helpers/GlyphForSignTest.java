/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GlyphForSignTest {

   private GlyphForSign glyphForSign;

   @Test
   public void getGlyph() {
      glyphForSign = new GlyphForSign();
      assertEquals("3", glyphForSign.getGlyph(3));

   }
}