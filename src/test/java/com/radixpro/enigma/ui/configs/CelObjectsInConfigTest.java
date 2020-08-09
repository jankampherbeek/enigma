/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.ui.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CelObjectsInConfigTest {

   private CelObjectsInConfig celObjectsInConfig;

   @Before
   public void setUp() {
      celObjectsInConfig = new CelObjectsInConfig(Rosetta.getRosetta());
   }

   @Test
   public void constructProperties() {
      List<PresentableProperty> props = celObjectsInConfig.constructProperties(createConfiguredCelObjects());
      assertEquals("Classic bodies", props.get(0).getName());
      assertEquals("Sun", props.get(0).getValue().trim());
      assertEquals("Modern bodies", props.get(1).getName());
      assertEquals("Uranus", props.get(1).getValue().trim());
   }

   private List<ConfiguredCelObject> createConfiguredCelObjects() {
      List<ConfiguredCelObject> configuredCelObjects = new ArrayList();
      configuredCelObjects.add(new ConfiguredCelObject(CelestialObjects.SUN, "a", 100, true));
      configuredCelObjects.add(new ConfiguredCelObject(CelestialObjects.URANUS, "b", 70, true));
      configuredCelObjects.add(new ConfiguredCelObject(CelestialObjects.CHEIRON, "c", 40, true));
      configuredCelObjects.add(new ConfiguredCelObject(CelestialObjects.MEAN_NODE, "e", 60, true));
      return configuredCelObjects;
   }

}