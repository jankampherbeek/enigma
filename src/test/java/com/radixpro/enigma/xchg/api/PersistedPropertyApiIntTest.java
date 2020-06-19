/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.shared.AppDb;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.testsupport.DbTestSupport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersistedPropertyApiIntTest {

   private AppDb appDb;
   private PersistedPropertyApi pPropApi;

   @Before
   public void setUp() {
      new DbTestSupport();
      appDb = AppDb.getInstance();
      pPropApi = new PersistedPropertyApi();
   }

   @Test
   public void updateAndRead() {
      pPropApi.update(new Property("config", "2"));
      Property result = pPropApi.read("config").get(0);
      assertEquals("2", result.getValue());
      pPropApi.update(new Property("config", "3"));
      result = pPropApi.read("config").get(0);
      assertEquals("3", result.getValue());
   }

}