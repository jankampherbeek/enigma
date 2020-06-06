/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GlyphForCelObjectTest {

   @Test
   public void getGlyph() {
      GlyphForCelObject glyphForCelObject = new GlyphForCelObject();
      assertEquals("d", glyphForCelObject.getGlyph(4));
   }
}