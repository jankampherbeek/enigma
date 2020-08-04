/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis.handlers;

import com.radixpro.enigma.be.analysis.AspectsForRadix;
import com.radixpro.enigma.be.analysis.MidpointsForRadix;
import com.radixpro.enigma.be.analysis.ProgRadixAspects;
import com.radixpro.enigma.be.handlers.AspectsHandler;
import com.radixpro.enigma.be.handlers.MidpointsHandler;
import com.radixpro.enigma.be.handlers.ProgAspectHandler;

public class AnHandlersFactory {

   private AnHandlersFactory() {
      // prevent instantiation
   }

   public static AspectsHandler createAspectsHandler() {
      return new AspectsHandler(new AspectsForRadix());
   }

   public static MidpointsHandler createMidpointsHandler() {
      return new MidpointsHandler(new MidpointsForRadix());
   }

   public static ProgAspectHandler createTransitsAspectHandler() {
      return new ProgAspectHandler(new ProgRadixAspects());
   }

}
