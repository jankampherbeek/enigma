/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import org.jetbrains.annotations.NotNull;

/**
 * Short description for a data file.
 */
public class DataFileDescription {

   private final String name;
   private final String description;
   private final int nrOfRecords;

   public DataFileDescription(@NotNull String name, @NotNull String description, int nrOfRecords) {
      this.name = name;
      this.description = description;
      this.nrOfRecords = nrOfRecords;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public int getNrOfRecords() {
      return nrOfRecords;
   }
}
