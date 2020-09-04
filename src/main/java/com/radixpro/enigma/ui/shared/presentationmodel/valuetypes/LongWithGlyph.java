/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.jetbrains.annotations.NotNull;

/**
 * DTO that holds a formatted String with degrees, minutes and seconds, and the index of the zodiacal sign.
 */
public class LongWithGlyph {

   private final String position;
   private final int signIndex;

   public LongWithGlyph(@NotNull final String position, final int signIndex) {
      this.position = position;
      this.signIndex = signIndex;
   }

   public String getPosition() {
      return position;
   }

   public int getSignIndex() {
      return signIndex;
   }
}
