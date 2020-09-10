/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Request for reading a datafile and saving it in Json format in the project folder.
 */
public class InputDataFileRequest {

   private final String dataName;
   private final String description;
   private final File dataFile;
   private final String fullPathProjDir;

   public InputDataFileRequest(@NotNull final String dataName,
                               @NotNull final String description,
                               @NotNull final File dataFile,
                               @NotNull final String fullPathProjDir) {
      this.dataFile = dataFile;
      checkArgument(!dataName.isBlank());
      checkArgument(!description.isBlank());
      checkArgument(!fullPathProjDir.isBlank());
      this.dataName = dataName;
      this.description = description;
      this.fullPathProjDir = fullPathProjDir;
   }

   public String getDataName() {
      return dataName;
   }

   public String getDescription() {
      return description;
   }

   public File getDataFile() {
      return dataFile;
   }

   public String getFullPathProjDir() {
      return fullPathProjDir;
   }
}
