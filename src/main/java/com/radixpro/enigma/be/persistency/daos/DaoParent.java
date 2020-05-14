/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.be.persistency.daos;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.radixpro.enigma.be.exceptions.DatabaseException;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public abstract class DaoParent {

   protected static final Logger LOG = Logger.getLogger(DaoParent.class);
   protected static final String DB_LOCATION = "c:/enigma-data/db/";


   protected CSVReader createReader(String filename) throws DatabaseException {
      CSVReader reader = null;
      try {
         reader = new CSVReader(new FileReader(filename));
      } catch (FileNotFoundException e) {
         throw new DatabaseException("File not found when reading all properties : " + e.getMessage());
      }
      return reader;
   }

   protected void writeData(List<String[]> data2Write, String filename) throws DatabaseException {
      try (FileOutputStream fos = new FileOutputStream(filename);
           OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
           CSVWriter writer = new CSVWriter(osw)) {
         writer.writeAll(data2Write);
      } catch (IOException e) {
         LOG.error("Could not create a CSVWriter when accessing file : " + filename);
         throw new DatabaseException("Could not create a CSVWriter : " + e.getMessage());
      }
   }

   protected void updateFiles(String filenameOld, String filenameCurrent, String filenameNew) {
      File bakFile = new File(filenameOld);
      File currentFile = new File(filenameCurrent);
      File newFile = new File(filenameNew);
      try {
         Files.copy(currentFile.toPath(), bakFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
         Files.copy(newFile.toPath(), currentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
