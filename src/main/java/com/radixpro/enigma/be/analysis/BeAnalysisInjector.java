/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis;

import com.radixpro.enigma.AppScope;

public class BeAnalysisInjector {

   public static AspectsForRadix injectAspectsForRadix(AppScope scope) {
      return new AspectsForRadix();
   }

   public static MidpointsForRadix injectMidpointsForRadix(AppScope scope) {
      return new MidpointsForRadix();
   }

   public static ProgRadixAspects injectProgRadixAspects(AppScope scope) {
      return new ProgRadixAspects();
   }

}
