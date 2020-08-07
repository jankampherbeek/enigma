/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AspectOrbStructuresTest {

   private final AspectOrbStructures structure = AspectOrbStructures.ASPECT;


   @Test
   public void getStructureForId() throws UnknownIdException {
      assertEquals(AspectOrbStructures.CELBODY, structure.getStructureForId(2));
   }

   @Test
   public void getId() {
      assertEquals(1, structure.getId());
   }

   @Test(expected = UnknownIdException.class)
   public void getStructoreForIdDoesNotExist() throws UnknownIdException {
      structure.getStructureForId(1000);
   }
}