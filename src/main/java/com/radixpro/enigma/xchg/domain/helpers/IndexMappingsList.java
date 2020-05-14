/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.helpers;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Collection of all IndexMappings for a specific enum.
 */
public class IndexMappingsList {

   final List<IndexMapping> allIndexMappings;

   public IndexMappingsList(final List<IndexMapping> allIndexMappings) {
      this.allIndexMappings = checkNotNull(allIndexMappings);
   }

   public List<IndexMapping> getAllIndexMappings() {
      return allIndexMappings;
   }

   /**
    * Find sequenceId for a given enumId.
    *
    * @param enumId the enumId to be searched.
    * @return if found: the sequenceId, otherwise: -1 .
    */
   public int getSequenceIdForEnumId(final int enumId) {
      for (IndexMapping indexMapping : allIndexMappings) {
         if (indexMapping.getEnumId() == enumId) return indexMapping.getSequenceId();
      }
      return -1;
   }

   /**
    * Find enumId for a given sequenceId.
    *
    * @param sequenceId the sequenceId to be searched.
    * @return if found: the enumId, otherwise: -1.
    */
   public int getEnumIdForSequenceId(final int sequenceId) {
      for (IndexMapping indexMapping : allIndexMappings) {
         if (indexMapping.getSequenceId() == sequenceId) return indexMapping.getEnumId();
      }
      return -1;
   }
}
