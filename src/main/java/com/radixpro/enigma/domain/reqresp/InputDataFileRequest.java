/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

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

   public InputDataFileRequest(final String dataName, final String description, final File dataFile, final String fullPathProjDir) {
      this.dataFile = dataFile;
      checkArgument(null != dataName && !dataName.isBlank());
      checkArgument(null != description && !description.isBlank());
      checkArgument(null != fullPathProjDir && !fullPathProjDir.isBlank());
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
