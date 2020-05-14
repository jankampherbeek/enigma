/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

/**
 * Structure of an orb for an aspect.
 */
public enum AspectOrbStructure {
   ASPECT(1),
   CELBODY(2),
   COMBINED(3),
   CATEGORY(4);

   private final int id;

   AspectOrbStructure(final int id) {
      this.id = id;
   }


   /**
    * Retrieve aspect orb structure for a given id.
    *
    * @param id The id of the structure to return.
    * @return If id is found the resulting structure, otherwise null.
    */
   public AspectOrbStructure getStructureForId(int id) {
      for (AspectOrbStructure structure : AspectOrbStructure.values()) {
         if (structure.getId() == id) {
            return structure;
         }
      }
      return null;
      // TODO Release 2020.2: throw exception if aspect orb structure is not found
   }

   public int getId() {
      return id;
   }

}
