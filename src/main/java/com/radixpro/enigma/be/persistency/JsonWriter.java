/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Writes an object to file using Json format.
 * Based on an example at: http://www.studytrails.com/java/json/jackson-create-json.jsp
 */
public class JsonWriter {

   public void write2File(@NotNull final String pathFilename,
                          @NotNull final Object object2Write,
                          final boolean useIndent) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.INDENT_OUTPUT, useIndent);
      mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
      try {
         File jsonFile = new File(pathFilename);
         mapper.writeValue(jsonFile, object2Write);
      } catch (IOException e) {
         throw new RuntimeException("Could not write to file, using path and filename :" + pathFilename + " . Original message: " + e.getMessage());
      }
   }

}
