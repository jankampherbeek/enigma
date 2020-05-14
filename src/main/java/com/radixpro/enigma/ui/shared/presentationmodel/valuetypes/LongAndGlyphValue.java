/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

/**
 * Creates a formatted string and an index for an ecliptical position.
 * The string represents a decimal value and uses two positions for the integer part, if necessaryn prefixed with spaces.
 * The index indicates the zodiacal sign: 1 = Aries ....12 = Pisces.
 */
public class LongAndGlyphValue extends AbstractSexagValue {

   private LongWithGlyph longWithGlyph;

   public LongAndGlyphValue(final double value) {
      super(value);
      lengthOfIntegerPart = 2;
      performFormatting();
   }

   @Override
   protected void performFormatting() {
      int signIndex = defineSignIndex(value);
      final double workValue = limitLongitudeWithin30(value, signIndex);
      formattedPosition = performSexagFormatting(workValue);
      longWithGlyph = new LongWithGlyph(formattedPosition, signIndex);
   }

   private int defineSignIndex(final double workValue) {
      return (int) (workValue / 30) + 1;
   }

   private double limitLongitudeWithin30(final double value, final int signIndex) {
      return (value - 30 * (signIndex - 1));
   }

   public LongWithGlyph getLongWithGlyph() {
      return longWithGlyph;
   }
}
