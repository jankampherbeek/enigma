/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AspectsTest {

   private static final double MARGIN = 0.00000001;
   private Aspects aspect;

   @Before
   public void setUp() {
      aspect = Aspects.OPPOSITION;
   }

   @Test
   public void getId() {
      assertEquals(2, aspect.getId());
   }

   @Test
   public void getAngle() {
      assertEquals(180.0, aspect.getAngle(), MARGIN);
   }

   @Test
   public void getCategory() {
      assertEquals(AspectCategory.MAJOR, aspect.getAspectCategory());
   }

   @Test
   public void getFullRbId() {
      assertEquals("aspects.opposition", aspect.getFullRbId());
   }

   @Test
   public void getAspectForId() {
      assertEquals(Aspects.BINOVILE, Aspects.CONJUNCTION.getAspectForId(21));
   }

   @Test
   public void getAspectForIdNotFound() {    // TODO Release 2020.2: do not return NULL, this is a temperary solution / test
      assertNull(Aspects.CONJUNCTION.getAspectForId(2000));
   }

   @Test
   public void total() {
      assertEquals(24, Aspects.values().length);
   }
}