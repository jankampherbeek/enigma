/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import com.radixpro.enigma.xchg.domain.AspectOrbStructures;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AspectConfigurationTest {

   private static final double DELTA = 0.00000001;
   private final double baseOrb = 7.7;
   private final AspectOrbStructures orbStructure = AspectOrbStructures.ASPECT;
   private final boolean drawInOutGoing = true;
   private List<ConfiguredAspect> aspects;
   private AspectConfiguration aspectConfiguration;

   @Before
   public void setUp() {
      aspects = new ArrayList<>();
      aspectConfiguration = new AspectConfiguration(aspects, baseOrb, orbStructure, drawInOutGoing);
   }

   @Test
   public void getBaseOrb() {
      assertEquals(baseOrb, aspectConfiguration.getBaseOrb(), DELTA);
   }

   @Test
   public void getOrbStructure() {
      assertEquals(orbStructure, aspectConfiguration.getOrbStructure());
   }

   @Test
   public void getAspects() {
      assertEquals(aspects, aspectConfiguration.getAspects());
   }

   @Test
   public void isDrawInOutGoing() {
      assertEquals(drawInOutGoing, aspectConfiguration.isDrawInOutGoing());
   }
}