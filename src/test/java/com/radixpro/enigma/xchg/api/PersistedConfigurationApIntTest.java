/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.testsupport.DbTestSupport;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.config.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersistedConfigurationApIntTest {

   private AppDb appDb;
   private PersistedConfigurationApi pConfApi;

   @Before
   public void setUp() {
      new DbTestSupport();
      appDb = AppDb.getInstance();
      pConfApi = new PersistedConfigurationApi();
   }

   @Test
   public void insertAndDelete() {
      List<Configuration> configurations = pConfApi.readAll();
      assertEquals(4, configurations.size());
      Configuration config = createConfig();
      int configId = pConfApi.insert(config);
      configurations = pConfApi.readAll();
      assertEquals(5, configurations.size());
      configurations = pConfApi.read(configId);
      assertEquals("Just a test", configurations.get(0).getDescription());
      pConfApi.delete(configId);
      configurations = pConfApi.readAll();
      assertEquals(4, configurations.size());
      configurations = pConfApi.read(configId);
      assertTrue(configurations.isEmpty());
   }

   @Test
   public void update() {
      Configuration config1 = createConfig();
      int configId = pConfApi.insert(config1);
      List<Configuration> configurations = pConfApi.read(configId);
      assertEquals("Just a test", configurations.get(0).getDescription());
      Configuration config2 = createConfig();
      config2.setId(configId);
      config2.setName("Changed config");
      config2.setDescription("Changed description");
      pConfApi.update(config2);
      configurations = pConfApi.read(configId);
      assertEquals("Changed description", configurations.get(0).getDescription());
      pConfApi.delete(configId);
   }


   @Test
   public void read() {
      final List<Configuration> configurations = pConfApi.read(3);
      assertEquals(1, configurations.size());
      assertEquals("Hellenistic", configurations.get(0).getName());
   }

   @Test
   public void search() {
      final List<Configuration> configurations = pConfApi.search("Vedic");
      assertEquals(1, configurations.size());
      assertEquals("Vedic", configurations.get(0).getName());
   }

   @Test
   public void readAll() {
      final List<Configuration> configurations = pConfApi.readAll();
      assertEquals(4, configurations.size());
      Configuration config = configurations.get(0);
      assertEquals(HouseSystems.PLACIDUS, config.getAstronConfiguration().getHouseSystem());
      assertEquals(0, config.getParentId());
   }

   private Configuration createConfig() {
      List<ConfiguredAspect> aspects = new ArrayList<>();
      aspects.add(new ConfiguredAspect(AspectTypes.CONJUNCTION, 100, "a", true));
      aspects.add(new ConfiguredAspect(AspectTypes.OPPOSITION, 100, "b", true));
      AspectConfiguration aspConfig = new AspectConfiguration(aspects, 8.0, AspectOrbStructures.ASPECT, false);
      DelinConfiguration delConfig = new DelinConfiguration(aspConfig);
      List<ConfiguredCelObject> points = new ArrayList<>();
      points.add(new ConfiguredCelObject(CelestialObjects.SUN, "a", 100, true));
      points.add(new ConfiguredCelObject(CelestialObjects.MOON, "b", 100, true));
      AstronConfiguration astrConfig = new AstronConfiguration(HouseSystems.ALCABITIUS, Ayanamshas.NONE, EclipticProjections.TROPICAL,
            ObserverPositions.GEOCENTRIC, points);
      return new Configuration(0, 1, "Test1", "Just a test", astrConfig, delConfig);
   }
}