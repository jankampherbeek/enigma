/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

   private static final Logger LOG = Logger.getLogger(AppProperties.class);
   private final String USER_HOME = "user.home";
   private final String PROPS_FILE = "enigmaastroprops";
   private final String DBLOC_PROP = "dblocation";
   private final String LANG_PROP = "lang";

   private String databasePath;
   private String language;

   public AppProperties(final String env) {
      defineProperties(env);
   }

   private void defineProperties(final String env) {
      String userHome = "";
      try {
         LOG.info("Started AppProperties.");
         userHome = System.getProperty(USER_HOME);
         LOG.info("Value for userHome : " + userHome);
         Properties defaultProps = new Properties();
         String propsLoc = userHome + File.separator + "." + PROPS_FILE;
         if ("dev".equalsIgnoreCase(env)) propsLoc = "dev" + File.separator + PROPS_FILE;
         if ("test".equalsIgnoreCase(env)) propsLoc = "test" + File.separator + PROPS_FILE;
         LOG.info("Location for properties file : " + propsLoc);
         FileInputStream in = new FileInputStream(propsLoc);
         defaultProps.load(in);
         in.close();
         Properties applicationProps = new Properties(defaultProps);
         databasePath = applicationProps.getProperty(DBLOC_PROP);
         LOG.info("Value for databasePath : " + databasePath);
         language = applicationProps.getProperty(LANG_PROP);
         LOG.info("Value for language : " + language);
      } catch (IOException e) {
         LOG.error("Could not read properties file in " + userHome + " . Exception : " + e.getMessage());
         new FailFastHandler().terminate("The properties file in " + userHome + " can not be read.");
      }
   }

   public String getDatabasePath() {
      return databasePath;
   }

   public String getLanguage() {
      return language;
   }
}
