/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis;

public class BeAnalysisInjector {

   public static AspectsForRadix injectAspectsForRadix() {
      return new AspectsForRadix();
   }

   public static MidpointsForRadix injectMidpointsForRadix() {
      return new MidpointsForRadix();
   }

   public static ProgRadixAspects injectProgRadixAspects() {
      return new ProgRadixAspects();
   }

}
