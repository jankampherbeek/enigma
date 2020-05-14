/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AspectCategoryTest {

   private AspectCategory aspectCategory = AspectCategory.MINOR;

   @Test
   public void getId() {
      assertEquals(1, aspectCategory.getId());
   }

   @Test
   public void getRbName() {
      assertEquals("aspectcat.minor", aspectCategory.getRbName());
   }
}