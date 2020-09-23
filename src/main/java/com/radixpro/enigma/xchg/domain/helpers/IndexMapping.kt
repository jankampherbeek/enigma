/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.helpers;

/**
 * Combines the sequence id to be used in an Observable list and the id in an Enum.
 */
public class IndexMapping {

   private final int sequenceId;
   private final int enumId;

   public IndexMapping(final int sequenceId, final int enumId) {
      this.sequenceId = sequenceId;
      this.enumId = enumId;
   }

   public int getSequenceId() {
      return sequenceId;
   }

   public int getEnumId() {
      return enumId;
   }

}
