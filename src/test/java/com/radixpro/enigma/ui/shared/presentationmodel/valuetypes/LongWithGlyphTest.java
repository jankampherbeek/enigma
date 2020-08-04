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

public class LongWithGlyphTest {

   private final int signIndex = 3;
   private final String position = " 1" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN;
   private LongWithGlyph longWithGlyph;

   @Before
   public void setUp() {
      longWithGlyph = new LongWithGlyph(position, signIndex);
   }

   @Test
   public void getPosition() {
      assertEquals(position, longWithGlyph.getPosition());
   }

   @Test
   public void getSignIndex() {
      assertEquals(signIndex, longWithGlyph.getSignIndex());
   }
}