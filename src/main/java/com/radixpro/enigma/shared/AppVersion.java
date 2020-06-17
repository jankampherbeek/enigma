/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import com.radixpro.enigma.be.persistency.daos.VersionDao;
import com.radixpro.enigma.be.versions.Updater;
import com.radixpro.enigma.xchg.api.VersionApi;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Takes care of update if a new version has been installed.
 */
public class AppVersion {

   private static final Logger LOG = Logger.getLogger(AppVersion.class);


   public AppVersion(final Updater updater) {
      LOG.info("Instantiated AppVersion.");
      final String codeVersion = EnigmaDictionary.VERSION;

      // check if database exists
      AppProperties appProperties = AppDb.getInstance().getProps();
      String dbFileName = appProperties.getDatabasePath() + ".mv.db";
      File dbFile = new File(dbFileName);
      String dbVersion;
      VersionApi api = new VersionApi(new VersionDao());  // TODO use factory
      if (dbFile.exists()) {
         LOG.info("Database file exists.");
         dbVersion = api.latestVersion();
      } else {
         LOG.info("Database file does not yet exist.");
         dbVersion = "0.0";
      }
      // TODO: check existence of c:/enigmadata and convert it

      if (codeVersion.compareToIgnoreCase(dbVersion) > 0) {
         LOG.info("Database needs to be updated. Version : " + dbVersion + " must be " + codeVersion);
         updateDatabase(updater, dbVersion);
         api.defineNewVersion(EnigmaDictionary.VERSION);
      }
   }

   private void updateDatabase(final Updater updater, final String dbVersion) {
      if (dbVersion.equals("0.0") || (dbVersion.equals("2020.1"))) {
         // initalize full database
         // current version 2020.2
         updater.updateStep20202();
      }
   }


}
