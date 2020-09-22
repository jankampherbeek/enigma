/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency;

import com.opencsv.CSVReader;
import com.radixpro.enigma.shared.exceptions.DatabaseException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;

// TODO make this class a useful parent for DAO's.
public abstract class DaoParent {

   protected static final Logger LOG = Logger.getLogger(DaoParent.class);
   protected static final String DB_LOCATION = "c:/enigma-data/db/";    // TODO read from properties


   // TODO temporary solution to read files from 2020.1 , replace
   protected CSVReader createReader(@NotNull final String filename) throws DatabaseException {
      CSVReader reader;
      try {
         reader = new CSVReader(new FileReader(filename));
      } catch (FileNotFoundException e) {
         throw new DatabaseException("File not found: " + e.getMessage());
      }
      return reader;
   }


}
