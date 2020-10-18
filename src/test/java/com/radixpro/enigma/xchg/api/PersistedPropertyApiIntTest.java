/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.share.api.PropertyApi;

public class PersistedPropertyApiIntTest {

   private AppDb appDb;
   private PropertyApi pPropApi;

   // TODO enable and fix integration tests for PersistedPropertyApi
//   @Before
//   public void setUp() {
//      appDb = DbTestSupport.useDb();
//      pPropApi = XchgApiInjector.injectPersistedPropertyApi(new AppScope());
//   }
//
//   @Test
//   public void updateAndRead() {
//      pPropApi.update(new Property("config", "2"));
//      Property result = pPropApi.read("config").get(0);
//      assertEquals("2", result.getValue());
//      pPropApi.update(new Property("config", "3"));
//      result = pPropApi.read("config").get(0);
//      assertEquals("3", result.getValue());
//   }

}