/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.AppScope;

public class BePersistencyInjector {

   public static VersionDao injectVersionDao(AppScope scope) {
      return new VersionDao();
   }

}
