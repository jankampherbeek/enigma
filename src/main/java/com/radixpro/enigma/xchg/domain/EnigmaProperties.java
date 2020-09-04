/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.FailFastHandler;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Reads properties, these properties are retrieved from property-files.
 */
public class EnigmaProperties {
// TODO, seems obsolete, remove
   private static final Logger LOG = Logger.getLogger(EnigmaProperties.class);
   private static final String EXTENSION = ".properties";
   private Properties properties;

   /**
    * Constructor.
    *
    * @param propType The type of the property, the filename without the extension '.properties'.
    */
   public EnigmaProperties(@NotNull final String propType) {
      processProperties(propType);
   }

   private void processProperties(final String propType) {
      String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
      if (rootPath == null) new FailFastHandler().terminate("Could not define rootPath for Properties.");
      String appConfigPath = rootPath + propType + EXTENSION;
      properties = new Properties();
      try {
         properties.load(new FileInputStream(appConfigPath));
      } catch (IOException e) {
         LOG.error("Exception when reading properties : " + e.getMessage());
      }
   }

   public Properties getProperties() {
      return properties;
   }
}
