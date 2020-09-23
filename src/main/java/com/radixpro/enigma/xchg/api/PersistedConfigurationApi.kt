/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.persistency.ConfigurationDao;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.exceptions.DatabaseException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PersistedConfigurationApi {

   private static final Logger LOG = Logger.getLogger(PersistedConfigurationApi.class);
   private final ConfigurationDao dao;


   public PersistedConfigurationApi(@NotNull final ConfigurationDao dao) {
      this.dao = dao;
   }

   public int insert(@NotNull final Configuration configuration) {
      int configId = -1;
      try {
         configId = dao.insert(configuration);
      } catch (DatabaseException de) {
         new FailFastHandler().terminate(de.getMessage());
      }
      return configId;
   }

   public void update(@NotNull final Configuration configuration) {
      try {
         dao.update(configuration);
      } catch (DatabaseException de) {
         new FailFastHandler().terminate(de.getMessage());
      }
   }

   public void delete(final int id) {
      try {
         dao.delete(id);
      } catch (Exception e) {
         new FailFastHandler().terminate(e.getMessage());
      }
   }

   public List<Configuration> read(final int id) {
      List<Configuration> configs = new ArrayList<>();
      try {
         configs = dao.read(id);
      } catch (Exception e) {
         new FailFastHandler().terminate(e.getMessage());
      }
      return configs;
   }

   public List<Configuration> search(@NotNull final String searchName) {
      List<Configuration> configs = new ArrayList<>();
      try {
         configs = dao.search(searchName);
      } catch (Exception de) {
         new FailFastHandler().terminate(de.getMessage());
      }
      return configs;
   }

   public List<Configuration> readAll() {
      List<Configuration> configs = new ArrayList<>();
      try {
         configs = dao.readAll();
         LOG.info("Read configurations, size of list : " + configs.size());
      } catch (Exception e) {
         new FailFastHandler().terminate(e.getMessage());
      }
      return configs;
   }


}

