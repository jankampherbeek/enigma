/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.versions;

import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.config.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Updater {

   private static final Logger LOG = Logger.getLogger(Updater.class);
   private PersistedPropertyApi propApi;


   public void performFullUpdate() {
      propApi = new PersistedPropertyApi();
      updateStep20201();
      LOG.info("Full initialization completed.");
   }

   private void updateStep20201() {  // initial version
      LOG.info("Updating to version 2020.1");
      LOG.info("Inserted properties for 2020.1");

      PersistedConfigurationApi api = new PersistedConfigurationApi();
      var celObjects = createCelObjects(StandardConfigurations.WESTERN_STANDARD);
      var astronConfiguration = new AstronConfiguration(HouseSystems.PLACIDUS, Ayanamshas.NONE,
            EclipticProjections.TROPICAL, ObserverPositions.GEOCENTRIC, celObjects);
      List<ConfiguredAspect> aspects = createAspects(StandardConfigurations.WESTERN_STANDARD);
      AspectConfiguration aspectConfiguration = new AspectConfiguration(aspects, 8.0, AspectOrbStructure.ASPECT, false);
      DelinConfiguration delinConfiguration = new DelinConfiguration(aspectConfiguration);
      Configuration standardWestern = new Configuration(1, 0, "Western standard", "Standard for western astrology",
            astronConfiguration, delinConfiguration);
      api.insert(standardWestern);
      LOG.info("Inserted Standard Western configuration for 2020.1");

      celObjects = createCelObjects(StandardConfigurations.ASTRONOMICAL_CORRECT);
      astronConfiguration = new AstronConfiguration(HouseSystems.REGIOMONTANUS, Ayanamshas.NONE,
            EclipticProjections.TROPICAL, ObserverPositions.TOPOCENTRIC, celObjects);
      aspects = createAspects(StandardConfigurations.ASTRONOMICAL_CORRECT);
      aspectConfiguration = new AspectConfiguration(aspects, 8.0, AspectOrbStructure.ASPECT, false);
      delinConfiguration = new DelinConfiguration(aspectConfiguration);
      Configuration astronomicalCorrect = new Configuration(2, 0, "Astronomical Correct", "Based on astronomical correct/visual positions",
            astronConfiguration, delinConfiguration);
      api.insert(astronomicalCorrect);
      LOG.info("Inserted Astronomical Correct configuration for 2020.1");

      celObjects = createCelObjects(StandardConfigurations.HELLENISTIC);
      astronConfiguration = new AstronConfiguration(HouseSystems.WHOLESIGN, Ayanamshas.NONE,
            EclipticProjections.TROPICAL, ObserverPositions.GEOCENTRIC, celObjects);
      aspects = createAspects(StandardConfigurations.HELLENISTIC);
      aspectConfiguration = new AspectConfiguration(aspects, 8.0, AspectOrbStructure.ASPECT, false);
      delinConfiguration = new DelinConfiguration(aspectConfiguration);
      Configuration hellenistic = new Configuration(3, 0, "Hellenistic", "Hellenistic",
            astronConfiguration, delinConfiguration);
      api.insert(hellenistic);
      LOG.info("Inserted Hellenistic configuration for 2020.1");

      celObjects = createCelObjects(StandardConfigurations.VEDIC);
      astronConfiguration = new AstronConfiguration(HouseSystems.WHOLESIGN, Ayanamshas.LAHIRI,
            EclipticProjections.SIDEREAL, ObserverPositions.GEOCENTRIC, celObjects);
      aspects = createAspects(StandardConfigurations.VEDIC);
      aspectConfiguration = new AspectConfiguration(aspects, 8.0, AspectOrbStructure.ASPECT, false);
      delinConfiguration = new DelinConfiguration(aspectConfiguration);
      Configuration vedic = new Configuration(4, 0, "Vedic", "Vedic",
            astronConfiguration, delinConfiguration);
      api.insert(vedic);
      LOG.info("Inserted Vedic configuration for 2020.1");


   }

   private List<ConfiguredCelObject> createCelObjects(final StandardConfigurations config) {
      checkNotNull(config);
      final List<ConfiguredCelObject> celObjects = new ArrayList<>();
      celObjects.add(new ConfiguredCelObject(CelestialObjects.SUN, "a", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.MOON, "b", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.MERCURY, "c", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.VENUS, "d", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.MARS, "f", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.JUPITER, "g", 100, true));
      celObjects.add(new ConfiguredCelObject(CelestialObjects.SATURN, "h", 100, true));
      if (!(config == StandardConfigurations.VEDIC || config == StandardConfigurations.HELLENISTIC)) {
         celObjects.add(new ConfiguredCelObject(CelestialObjects.URANUS, "i", 100, true));
         celObjects.add(new ConfiguredCelObject(CelestialObjects.NEPTUNE, "j", 100, true));
         celObjects.add(new ConfiguredCelObject(CelestialObjects.PLUTO, "k", 100, true));
         celObjects.add(new ConfiguredCelObject(CelestialObjects.CHEIRON, "w", 100, true));
      }
      if (config == StandardConfigurations.ASTRONOMICAL_CORRECT) {
         celObjects.add(new ConfiguredCelObject(CelestialObjects.TRUE_NODE, "{", 100, true));
      } else {
         celObjects.add(new ConfiguredCelObject(CelestialObjects.MEAN_NODE, "{", 100, true));
      }
      return celObjects;
   }

   private List<ConfiguredAspect> createAspects(final StandardConfigurations config) {
      checkNotNull(config);
      final List<ConfiguredAspect> aspects = new ArrayList<>();
      aspects.add(new ConfiguredAspect(AspectTypes.CONJUNCTION, 100, "B", true));
      aspects.add(new ConfiguredAspect(AspectTypes.OPPOSITION, 100, "C", true));
      aspects.add(new ConfiguredAspect(AspectTypes.TRIANGLE, 85, "D", true));
      aspects.add(new ConfiguredAspect(AspectTypes.SQUARE, 85, "E", true));
      aspects.add(new ConfiguredAspect(AspectTypes.SEXTILE, 60, "F", true));
      aspects.add(new ConfiguredAspect(AspectTypes.PARALLEL, 15, "O", false));
      aspects.add(new ConfiguredAspect(AspectTypes.CONTRAPARALLEL, 15, "P", false));
      if (config == StandardConfigurations.WESTERN_STANDARD || config == StandardConfigurations.ASTRONOMICAL_CORRECT) {
         aspects.add(new ConfiguredAspect(AspectTypes.INCONJUNCT, 25, "H", true));
      }
      return aspects;
   }

}
