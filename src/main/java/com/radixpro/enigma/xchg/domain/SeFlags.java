/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import static swisseph.SweConst.*;

/**
 * Different parameters that define a calculation.
 * The long are values will be or'ed to define the flag value for the SE.
 */
public enum SeFlags {
   SWISSEPH(SEFLG_SWIEPH),             // 2L
   HELIOCENTRIC(SEFLG_HELCTR),         // 8L
   SPEED(SEFLG_SPEED),                 // 256L
   EQUATORIAL(SEFLG_EQUATORIAL),       // 2048L
   TOPOCENTRIC(SEFLG_TOPOCTR),         // 32*1024L
   SIDEREAL(SEFLG_SIDEREAL),           // 64*1024L
   HORIZONTAL(SE_ECL2HOR);             // 0 int! Not to be combined with other flags

   private final long seValue;

   SeFlags(long seValue) {
      this.seValue = seValue;
   }

   public long getSeValue() {
      return seValue;
   }
}
