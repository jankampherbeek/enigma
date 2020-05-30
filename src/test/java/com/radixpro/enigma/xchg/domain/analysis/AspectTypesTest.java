/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import com.radixpro.enigma.xchg.domain.AspectCategory;
import org.junit.Assert;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AspectTypesTest {

   private final AspectTypes aspect = AspectTypes.OPPOSITION;

   @Test
   public void getId() {
      assertEquals(2, aspect.getId());
   }

   @Test
   public void getAngle() {
      assertEquals(180.0, aspect.getAngle(), DELTA_8_POS);
   }

   @Test
   public void getCategory() {
      Assert.assertEquals(AspectCategory.MAJOR, aspect.getAspectCategory());
   }

   @Test
   public void getFullRbId() {
      assertEquals("aspects.opposition", aspect.getFullRbId());
   }

   @Test
   public void getAspectForId() {
      assertEquals(AspectTypes.BINOVILE, AspectTypes.CONJUNCTION.getAspectForId(21));
   }

   @Test
   public void getAspectForIdNotFound() {    // TODO Release 2020.2: do not return NULL, this is a temperary solution / test
      assertNull(AspectTypes.CONJUNCTION.getAspectForId(2000));
   }

   @Test
   public void total() {
      assertEquals(24, AspectTypes.values().length);
   }
}