/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.ui.configs.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.config.ConfiguredAspect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AspectTypesInConfigTest {

   private AspectsInConfig aspectsInConfig;

   @Before
   public void setUp() {
      aspectsInConfig = new AspectsInConfig(Rosetta.getRosetta());
   }

   @Test
   public void constructProperties() {
      List<PresentableProperty> props = aspectsInConfig.constructProperties(createConfiguredAspects());
      assertEquals(4, props.size());
      assertEquals("Major", props.get(0).getName());
      assertEquals("Opposition", props.get(0).getValue().trim());
      assertEquals("Minor", props.get(1).getName());
      assertEquals("Novile", props.get(2).getValue().trim());
      assertEquals("Parallel", props.get(3).getValue().trim());
   }

   private List<ConfiguredAspect> createConfiguredAspects() {
      List<ConfiguredAspect> configuredAspects = new ArrayList<>();
      configuredAspects.add(new ConfiguredAspect(AspectTypes.OPPOSITION, 90, "x", true));
      configuredAspects.add(new ConfiguredAspect(AspectTypes.QUINTILE, 20, "y", false));
      configuredAspects.add(new ConfiguredAspect(AspectTypes.NOVILE, 10, "z", false));
      configuredAspects.add(new ConfiguredAspect(AspectTypes.PARALLEL, 15, "p", false));
      return configuredAspects;
   }
}