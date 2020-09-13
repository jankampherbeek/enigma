/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AyanamshasTest {

   private Ayanamshas ayanamsha;

   @Before
   public void setUp() {
      ayanamsha = Ayanamshas.KUGLER_2;
   }

   @Test
   public void getSeId() {
      assertEquals(10, ayanamsha.getSeId());
   }

   @Test
   public void getId() {
      assertEquals(10, ayanamsha.getId());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("ayanamshas.kugler2", ayanamsha.getNameForRB());
   }

   @Test
   public void getAyanamshaForId() {
      assertEquals(Ayanamshas.RAMAN, Ayanamshas.getAyanamshaForId(3));
   }

   @Test
   public void getAyanamshaForIdNotFound() {
      assertEquals(Ayanamshas.NONE, Ayanamshas.getAyanamshaForId(1000));
   }

   @Test
   public void total() {
      assertEquals(41, Ayanamshas.values().length);
   }

   @Test
   public void getObservableList() {
      assertEquals(41, Ayanamshas.getObservableList().size());
   }

   @Test
   public void getIndexMappings() {
      assertEquals(41, Ayanamshas.getIndexMappings().getAllIndexMappings().size());
   }
}