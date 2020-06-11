/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import org.apache.log4j.Logger;

/**
 * Manages database access, properties etc..
 * Implemented as a singleton.
 */
public class AppController {
   private static final Logger LOG = Logger.getLogger(AppController.class);
   private static AppController instance;
   private final AppProperties appProperties;
   private final AppDb appDb;
   private final AppVersion appVersion;


   private AppController(final AppProperties appProperties, final AppDb appDb, final AppVersion appVersion) {
      this.appProperties = appProperties;
      this.appDb = appDb;
      this.appVersion = appVersion;
      Rosetta.getRosetta().setLanguage(appProperties.getLanguage());
      LOG.info("Instantiated AppController and set language to " + appProperties.getLanguage());
   }

   public static AppController getAppController(final AppProperties appProperties, final AppDb appDb, final AppVersion appVersion) {
      if (null == instance) {
         instance = new AppController(appProperties, appDb, appVersion);
      }
      return instance;
   }


}
