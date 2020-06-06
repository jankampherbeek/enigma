/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.MundanePoints;
import com.radixpro.enigma.xchg.domain.analysis.ProgAnalysisType;
import com.radixpro.enigma.xchg.domain.calculatedcharts.ChartPositionsVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TransitAnalyzeRequestTest {

   private final ProgAnalysisType type = ProgAnalysisType.ASPECTS;
   private final double orb = 1.0;
   private List<SimplePosVo> transitPositions;
   private ChartPositionsVo chartPositions;
   private List<AspectTypes> aspects;
   private TransitAnalyzeRequest request;

   @Before
   public void setUp() throws Exception {
      transitPositions = createTransits();
      chartPositions = new ChartPositionsVo(55L, createCelestialPoints(), createMundanePositions());
      aspects = createAspects();
      request = new TransitAnalyzeRequest(type, transitPositions, chartPositions, aspects, orb);
   }

   @Test
   public void getType() {
      assertEquals(type, request.getType());
   }

   @Test
   public void getTransitPositions() {
      assertEquals(transitPositions, request.getTransitPositions());
   }

   @Test
   public void getChartPositions() {
      assertEquals(chartPositions, request.getChartPositions());
   }

   @Test
   public void getAspects() {
      assertEquals(aspects, request.getAspects());
   }

   @Test
   public void getOrb() {
      assertEquals(orb, request.getOrb(), DELTA_8_POS);
   }

   private List<SimplePosVo> createTransits() {
      List<SimplePosVo> transits = new ArrayList<>();
      transits.add(new SimplePosVo(CelestialObjects.URANUS, 100.0, 1.0, 102.0, 15.0));
      return transits;
   }

   private List<SimplePosVo> createCelestialPoints() {
      List<SimplePosVo> points = new ArrayList<>();
      points.add(new SimplePosVo(CelestialObjects.SATURN, 10.0, -1.0, 12.0, 1.5));
      return points;
   }

   private List<SimplePosVo> createMundanePositions() {
      List<SimplePosVo> mundPos = new ArrayList<>();
      mundPos.add(new SimplePosVo(MundanePoints.ASC, 310.0, -1.4, 312.0, -11.5));
      return mundPos;
   }

   private List<AspectTypes> createAspects() {
      List<AspectTypes> aspects = new ArrayList<>();
      aspects.add(AspectTypes.SQUARE);
      return aspects;
   }

}