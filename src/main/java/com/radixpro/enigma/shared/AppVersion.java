/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import com.radixpro.enigma.be.versions.Updater;
import org.apache.log4j.Logger;

/**
 * Takes care of update if a new version has been isntalled.
 */
public class AppVersion {

   private static final Logger LOG = Logger.getLogger(AppVersion.class);


   public AppVersion(final AppDb appDb, final Updater updater) {
      LOG.info("Instantiated AppVersion.");
      final String codeVersion = EnigmaDictionary.VERSION;
      final String dbVersion = "0"; // TODO read version from database, db
      // TODO: check existence of c:/enigmadata and convert it

      if (codeVersion.compareToIgnoreCase(dbVersion) > 0) {
         LOG.info("Database needs to be updated. Version : " + dbVersion + " must be " + codeVersion);
         updateDatabase(updater, dbVersion);

      }
   }

   private void updateDatabase(final Updater updater, final String dbVersion) {
      if (dbVersion.equals("0") || (dbVersion.equals("2020.1"))) {
         // initalize full database
         // current version 2020.2
         updater.updateStep20202();
      }
   }


}
