/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.common;

/**
 * Dictionary with static constants that can be used globally.
 */
public class EnigmaDictionary {

   public static final String VERSION = "2020.2";
   public static final String SE_LOCATION = "c:/enigma-data/se";                             // TODO release 2020.2: replace with property
   public static final String DEGREESIGN = "\u00B0";
   public static final String MINUTESIGN = "\u2032";
   public static final String SECONDSIGN = "\u2033";
   public static final String GLYPH_FONTNAME = "EnigmaAstrology";
   public static final String TEXT_FONTNAME = "Arial";
   public static final double NAIBOD_KEY = 0.98564733;
   public static final double TROPICAL_YEAR = 365.24219;


   private EnigmaDictionary() {
      // prevent instantiation
   }
}
