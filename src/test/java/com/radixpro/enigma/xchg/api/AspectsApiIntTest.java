/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.config.AspectConfiguration;
import com.radixpro.enigma.domain.config.ConfiguredAspect;
import com.radixpro.enigma.references.AspectOrbStructures;
import com.radixpro.enigma.references.AspectTypes;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.MundanePointsAstron;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Integration test for AspectsApi
 */
@RunWith(MockitoJUnitRunner.class)
public class AspectsApiIntTest {

   @Mock
   private IPosition sunPosMock;
   @Mock
   private IPosition moonPosMock;
   @Mock
   private IPosition mcPosMock;
   @Mock
   private IPosition ascPosMock;

   private List<IPosition> celObjects;
   private List<IPosition> mundaneValues;
   private AspectConfiguration config;
   private AspectsApi api;

   @Before
   public void setUp() {
      when(sunPosMock.getLongitude()).thenReturn(100.0);
      when(sunPosMock.getChartPoint()).thenReturn(CelestialObjects.SUN);
      when(moonPosMock.getLongitude()).thenReturn(281.0);
      when(moonPosMock.getChartPoint()).thenReturn(CelestialObjects.MOON);
      celObjects = new ArrayList<>();
      celObjects.add(sunPosMock);
      celObjects.add(moonPosMock);

      when(mcPosMock.getLongitude()).thenReturn(218.0);
      when(mcPosMock.getChartPoint()).thenReturn(MundanePointsAstron.MC);
      when(ascPosMock.getLongitude()).thenReturn(278.0);
      when(ascPosMock.getChartPoint()).thenReturn(MundanePointsAstron.ASC);
      mundaneValues = new ArrayList<>();
      mundaneValues.add(mcPosMock);
      mundaneValues.add(ascPosMock);
      config = createConfig();
      api = XchgApiInjector.injectAspectsApi();
   }

   @Test
   public void analyzeAspects() {
      List<IAnalyzedPair> results = api.analyzeAspects(celObjects, mundaneValues, config);
      assertEquals(6, results.size());
      IAnalyzedPair result0 = results.get(0);
      assertEquals(CelestialObjects.SUN, result0.getFirst().getChartPoint());
      assertEquals(CelestialObjects.MOON, result0.getSecond().getChartPoint());
      assertEquals(1.0, result0.getActualOrb(), DELTA_8_POS);
      assertEquals(12.5, result0.getPercOrb(), DELTA_8_POS);
      IAnalyzedPair result1 = results.get(2);
      assertEquals(CelestialObjects.SUN, result1.getFirst().getChartPoint());
      assertEquals(MundanePointsAstron.ASC, result1.getSecond().getChartPoint());
      assertEquals(2.0, result1.getActualOrb(), DELTA_8_POS);
      assertEquals(25.0, result1.getPercOrb(), DELTA_8_POS);
   }


   private AspectConfiguration createConfig() {
      final double baseOrb = 8.0;
      final AspectOrbStructures structure = AspectOrbStructures.ASPECT;
      final boolean drawInOutGoing = false;
      final List<ConfiguredAspect> aspects = createAspects();
      return new AspectConfiguration(aspects, baseOrb, structure, drawInOutGoing);
   }

   private List<ConfiguredAspect> createAspects() {
      final List<ConfiguredAspect> newAspects = new ArrayList<>();
      newAspects.add(new ConfiguredAspect(AspectTypes.CONJUNCTION, 100, "a", true));
      newAspects.add(new ConfiguredAspect(AspectTypes.OPPOSITION, 100, "b", true));
      newAspects.add(new ConfiguredAspect(AspectTypes.TRIANGLE, 80, "c", true));
      newAspects.add(new ConfiguredAspect(AspectTypes.SQUARE, 80, "d", true));
      newAspects.add(new ConfiguredAspect(AspectTypes.SEXTILE, 60, "c", true));
      return newAspects;
   }


}