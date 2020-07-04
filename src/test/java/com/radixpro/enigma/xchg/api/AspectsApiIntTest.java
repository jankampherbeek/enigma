/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.api.factories.ApiAnalysisFactory;
import com.radixpro.enigma.xchg.domain.AspectOrbStructures;
import com.radixpro.enigma.xchg.domain.CelCoordinateElementVo;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MundanePoints;
import com.radixpro.enigma.xchg.domain.calculatedobjects.CelCoordinateVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.HouseCoordinateVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.IObjectVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.ObjectVo;
import com.radixpro.enigma.xchg.domain.config.AspectConfiguration;
import com.radixpro.enigma.xchg.domain.config.ConfiguredAspect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

/**
 * Integration test for AspectsApi
 */
public class AspectsApiIntTest {

   private List<IObjectVo> celObjects;
   private List<IObjectVo> mundaneValues;
   private AspectConfiguration config;
   private AspectsApi api;

   @Before
   public void setUp() {
      celObjects = createCelObjects();
      mundaneValues = createMundaneValues();
      config = createConfig();
      api = new ApiAnalysisFactory().createAspectsApi();
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
      assertEquals(MundanePoints.ASC, result1.getSecond().getChartPoint());
      assertEquals(2.0, result1.getActualOrb(), DELTA_8_POS);
      assertEquals(25.0, result1.getPercOrb(), DELTA_8_POS);
   }

   private List<IObjectVo> createCelObjects() {
      List<IObjectVo> newCelObjects = new ArrayList<>();
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(100.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.SUN));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(281.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.MOON));
      return newCelObjects;
   }

   private List<IObjectVo> createMundaneValues() {
      List<IObjectVo> newHouses = new ArrayList<>();
      newHouses.add(new ObjectVo(
            createHouseCoordinateVo(218.0),
            createHouseCoordinateVo(0.0),
            createHouseCoordinateVo(0.0),
            MundanePoints.MC));
      newHouses.add(new ObjectVo(
            createHouseCoordinateVo(278.0),
            createHouseCoordinateVo(0.0),
            createHouseCoordinateVo(0.0),
            MundanePoints.ASC));
      return newHouses;
   }

   private CelCoordinateVo createCelCoordinateVo(double basePos) {
      CelCoordinateElementVo posCoordinate = new CelCoordinateElementVo(basePos, 0.0, 0.0);
      CelCoordinateElementVo speedCoordinate = new CelCoordinateElementVo(0.0, 0.0, 0.0);
      return new CelCoordinateVo(posCoordinate, speedCoordinate);
   }

   private HouseCoordinateVo createHouseCoordinateVo(double basePos) {
      CelCoordinateElementVo posCoordinate = new CelCoordinateElementVo(basePos, 0.0, 0.0);
      return new HouseCoordinateVo(posCoordinate);
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