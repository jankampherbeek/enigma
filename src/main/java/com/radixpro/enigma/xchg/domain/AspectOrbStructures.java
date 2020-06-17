/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;

/**
 * Structure of an orb for an aspect.
 */
public enum AspectOrbStructures {
   ASPECT(1),
   CELBODY(2),
   COMBINED(3),
   CATEGORY(4);

   private final int id;

   AspectOrbStructures(final int id) {
      this.id = id;
   }


   /**
    * Retrieve aspect orb structure for a given id.
    *
    * @param id The id of the structure to return.
    * @return If id is found the resulting structure, otherwise null.
    */
   public static AspectOrbStructures getStructureForId(int id) throws UnknownIdException {
      for (AspectOrbStructures structure : AspectOrbStructures.values()) {
         if (structure.getId() == id) {
            return structure;
         }
      }
      throw new UnknownIdException("Could not find AspectORbStructure for id : " + id);
   }

   public int getId() {
      return id;
   }

}
