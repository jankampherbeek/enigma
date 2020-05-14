/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CelObjectCategoryTest {

   private final CelObjectCategory category = CelObjectCategory.EXTRA_PLUT;

   @Test
   public void getId() {
      assertEquals(3, category.getId());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("celobjectcat.extraplut", category.getNameForRB());
   }

}