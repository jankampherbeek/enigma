/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.be.persistency.mappers.InputDataSetMapper;
import com.radixpro.enigma.share.persistency.JsonReader;
import com.radixpro.enigma.statistics.core.DataFileDescription;
import com.radixpro.enigma.statistics.core.InputDataSet;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Dao for datafiles in Json format.
 */
public class DataFileDao {

   private final JsonReader jsonReader;
   private final InputDataSetMapper mapper;

   public DataFileDao(@NotNull final JsonReader jsonReader,
                      @NotNull final InputDataSetMapper mapper) {
      this.jsonReader = jsonReader;
      this.mapper = mapper;
   }

   public List<DataFileDescription> readDataFileList(@NotNull final File projDataFolder) {
      List<DataFileDescription> descriptions = new ArrayList<>();
      List<String> fileNames = getFilenames(projDataFolder);
      InputDataSet inputDataSet;
      for (String fileName : fileNames) {
         inputDataSet = readData(projDataFolder, fileName);
         descriptions.add(new DataFileDescription(inputDataSet.getName(), inputDataSet.getDescription(), inputDataSet.getInputData().size()));
      }
      return descriptions;
   }

   private List<String> getFilenames(final File projDataFolder) {
      final List<String> filenames = new ArrayList<>();
      for (final File fileEntry : projDataFolder.listFiles()) {
         if (!fileEntry.isDirectory()) filenames.add(fileEntry.getName());
      }
      return filenames;
   }

   private InputDataSet readData(final File projDataFolder, final String filename) {
      File dataFile = new File(projDataFolder + File.separator + filename);
      final JSONObject inputDataJson = jsonReader.readObjectFromFile(dataFile);
      return mapper.jsonToInputDataSet(inputDataJson);
   }


}
