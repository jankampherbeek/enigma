/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

/**
 * Dictionary with static constants that can be used globally.
 */
public class EnigmaDictionary {

   public static final String VERSION = "2020.1";
   public static final String DATABASE_LOCATION = "c:/enigma-data/db/enigma.db";             // TODO release 2020.2: replace with property
   public static final String DEV_DATABASE_LOCATION = "c:/enigma-data/db/dev_enigma.db";     // TODO release 2020.2: replace with property
   public static final String TEST_DATABASE_LOCATION = "c:/enigma-data/db/test_enigma.db";   // TODO release 2020.2: replace with property
   public static final String SE_LOCATION = "c:/enigma-data/se";                             // TODO release 2020.2: replace with property
   public static final String DEGREESIGN = "\u00B0";
   public static final String MINUTESIGN = "\u2032";
   public static final String SECONDSIGN = "\u2033";

   private EnigmaDictionary() {
      // prevent instantiation
   }
}
