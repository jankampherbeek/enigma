/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.persistency.daos;

import com.opencsv.CSVReader;
import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.be.persistency.mappers.ConfigurationCsvMapper;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationDao extends DaoParent {

   private static final Logger LOG = Logger.getLogger(ConfigurationDao.class);
   private static final String CONF_FILE = DB_LOCATION + "configurations.csv";
   private static final String NEW_CONF_FILE = DB_LOCATION + "new_configurations.csv";
   private static final String OLD_CONF_FILE = DB_LOCATION + "old_configurations.csv";
   private static final int MIN_ID_NEW_CONFIGS = 1000;


   public void insert(final Configuration insertConfig) throws DatabaseException {
      List<Configuration> allConfigs = readAll();
      allConfigs.add(insertConfig);
      List<String[]> allLines = new ArrayList<>();
      for (Configuration config : allConfigs) {
         String[] csvData = new ConfigurationCsvMapper().csvFromConfig(config);
         allLines.add(csvData);
      }
      writeData(allLines, CONF_FILE);
   }

   public void update(final Configuration updateConfig) throws DatabaseException {
      checkNotNull(updateConfig);
      List<Configuration> allConfigs = readAll();
      List<String[]> updatedConfigs = new ArrayList<>();
      ConfigurationCsvMapper mapper = new ConfigurationCsvMapper();
      long updateConfigId = updateConfig.getId();
      for (Configuration config : allConfigs) {
         Configuration effectiveConfig = (config.getId() == updateConfigId ? updateConfig : config);
         String[] configLine = mapper.csvFromConfig(effectiveConfig);
         updatedConfigs.add(configLine);
      }
      writeData(updatedConfigs, NEW_CONF_FILE);
      updateFiles(OLD_CONF_FILE, CONF_FILE, NEW_CONF_FILE);
   }

   public void delete(final Configuration delConfig) throws DatabaseException {
      checkNotNull(delConfig);
      List<Configuration> allConfigs = readAll();
      List<String[]> remainingConfigs = new ArrayList<>();
      ConfigurationCsvMapper mapper = new ConfigurationCsvMapper();
      for (Configuration config : allConfigs) {
         if (config.getId() != delConfig.getId()) {
            String[] remainingConfig = mapper.csvFromConfig(config);
            remainingConfigs.add(remainingConfig);
         }
      }
      writeData(remainingConfigs, NEW_CONF_FILE);
      updateFiles(OLD_CONF_FILE, CONF_FILE, NEW_CONF_FILE);
   }

   /**
    * Retreive configuration for specific id.
    *
    * @param id the di for the configuration to retrieve.
    * @return A list with one or zero configurations.
    * @throws DatabaseException
    */
   public List<Configuration> read(final long id) throws DatabaseException {
      List<Configuration> allConfigs = readAll();
      List<Configuration> foundConfigs = new ArrayList<>();
      for (Configuration config : allConfigs) {
         if (id == config.getId()) foundConfigs.add(config);
      }
      return foundConfigs;
   }

   /**
    * Search a config using (a part of) the name. Search is case sensitive.
    *
    * @param searchName the search argument.
    * @return a list with zero or more configurations.
    * @throws DatabaseException
    */
   public List<Configuration> search(final String searchName) throws DatabaseException {
      checkNotNull(searchName);
      List<Configuration> allConfigs = readAll();
      List<Configuration> foundConfigs = new ArrayList<>();
      for (Configuration config : allConfigs) {
         if (config.getName().contains(searchName)) foundConfigs.add(config);
      }
      return foundConfigs;
   }

   /**
    * Retrieve all configurations.
    *
    * @return the configurations.
    * @throws DatabaseException
    */
   public List<Configuration> readAll() throws DatabaseException {
      List<String[]> allLines;
      List<Configuration> configList = new ArrayList<>();
      try (CSVReader reader = createReader(CONF_FILE)) {
         allLines = reader.readAll();
      } catch (IOException e) {
         throw new DatabaseException("IOException when reading all configurations : " + e.getMessage());
      }
      try {
         for (String[] line : allLines) {  // respectively id, key, value
            configList.add(new ConfigurationCsvMapper().configFromCsv(line));
         }
      } catch (UnknownIdException e) {
         LOG.error("Exception when reading all configurations. " + e.getMessage());
         throw new DatabaseException("Exception when reading all configurations.");
      }
      return configList;
   }

   /**
    * Define max id as currently used for a Configuration using a minimum value of 1000. Lower id's are reserved for
    * standard configurations.
    *
    * @return the max id that was found.
    * @throws DatabaseException
    */
   public long getMaxId() throws DatabaseException {
      List<Configuration> configs = readAll();
      long maxId = 0;
      for (Configuration config : configs) {
         if (config.getId() > maxId) maxId = config.getId();
      }
      return Math.max(maxId, MIN_ID_NEW_CONFIGS);
   }


}
