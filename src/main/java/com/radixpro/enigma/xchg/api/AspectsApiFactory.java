/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.AspectsHandler;
import com.radixpro.enigma.be.analysis.AspectsHandlerFactory;

public class AspectsApiFactory {

   public AspectsApi createApi() {
      AspectsHandler handler = new AspectsHandlerFactory().createHandler();
      return new AspectsApi(handler);
   }

}
