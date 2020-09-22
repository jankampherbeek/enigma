/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.be.persistency.DataReaderCsv;
import com.radixpro.enigma.be.persistency.JsonWriter;
import com.radixpro.enigma.domain.astronpos.InputDataSet;
import com.radixpro.enigma.domain.reqresp.InputDataFileRequest;
import com.radixpro.enigma.domain.reqresp.InputDataFileResponse;
import com.radixpro.enigma.shared.exceptions.InputDataException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for reading and converting input data.
 */
public class InputDataFileHandler {

   private static final Logger LOG = Logger.getLogger(InputDataFileHandler.class);
   private final DataReaderCsv dataReaderCsv;
   private final JsonWriter jsonWriter;
   private final Rosetta rosetta;

   public InputDataFileHandler(@NotNull final DataReaderCsv dataReaderCsv,
                               @NotNull final JsonWriter jsonWriter) {
      this.dataReaderCsv = dataReaderCsv;
      this.jsonWriter = jsonWriter;
      this.rosetta = Rosetta.getRosetta();
   }

   public InputDataFileResponse handleDataFile(final InputDataFileRequest request) {
      List<String> errorLines = new ArrayList<>();
      String resultMsg;
      boolean success;
      try {
         String pathFilename = createFullPathJsonFile(request.getDataName(), request.getFullPathProjDir());
         InputDataSet inputDataSet = dataReaderCsv.readCsv(request.getDataName(), request.getDescription(), request.getDataFile().getAbsolutePath());
         errorLines = dataReaderCsv.getErrorLines();
         success = dataReaderCsv.isNoErrors();
         jsonWriter.write2File(pathFilename, inputDataSet, true);
         if (success) resultMsg = rosetta.getText("inputdata.response.resultmsg") + " " + pathFilename;
         else resultMsg = rosetta.getText("inputdata.response.errormsg");
      } catch (InputDataException ide) {
         success = false;
         resultMsg = rosetta.getText("inputdata.response.errormsg");
         LOG.error("Error while writing Json file converted from csv. Result message: " + resultMsg + ". Exception: " + ide.getMessage());
      }
      return new InputDataFileResponse(resultMsg, errorLines, success);
   }

   private String createFullPathJsonFile(final String fileName,
                                         final String fullPathProjDir) throws InputDataException {
      String path = fullPathProjDir;
      if (!path.endsWith(File.separator)) path += File.separator;
      path += "data" + File.separator;
      if (!checkOrCreateFolder(path)) throw new InputDataException("Could not create folder :" + path);
      int pos = fileName.lastIndexOf(File.separator);
      String fileNameNoPath = fileName.substring(pos + 1);
      return path + fileNameNoPath;
   }

   private boolean checkOrCreateFolder(final String fullPathFolder) {
      if (Files.notExists(Path.of(fullPathFolder))) {
         File newFolder = new File(fullPathFolder);
         return newFolder.mkdir();
      }
      return true;
   }

}
