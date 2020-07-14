/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.ProgAnalysisType;
import com.radixpro.enigma.xchg.domain.astrondata.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProgAnalyzeRequestTest {

   @Mock
   private FullPointPosition fppEclMock;
   @Mock
   private FullPointPosition fppTransitMock;
   @Mock
   private MundanePosition mundMock;
   private final ProgAnalysisType type = ProgAnalysisType.ASPECTS;
   private final double orb = 1.0;
   private List<IPosition> transitPositions;
   private CalculatedChart calculatedChart;
   private List<AspectTypes> aspects;
   private ProgAnalyzeRequest request;

   @Before
   public void setUp() throws Exception {
      transitPositions = createTransits();
      calculatedChart = new CalculatedChart(createCelestialPoints(), createMundanePositions());
      aspects = createAspects();
      request = new ProgAnalyzeRequest(type, transitPositions, calculatedChart, aspects, orb);
   }

   @Test
   public void getType() {
      assertEquals(type, request.getType());
   }

   @Test
   public void getTransitPositions() {
      assertEquals(transitPositions, request.getProgPositions());
   }

   @Test
   public void getChartPositions() {
      assertEquals(calculatedChart, request.getCalculatedChart());
   }

   @Test
   public void getAspects() {
      assertEquals(aspects, request.getAspects());
   }

   @Test
   public void getOrb() {
      assertEquals(orb, request.getOrb(), DELTA_8_POS);
   }

   private List<IPosition> createTransits() {
      List<IPosition> transits = new ArrayList<>();
      transits.add(fppTransitMock);
      return transits;
   }

   private List<FullPointPosition> createCelestialPoints() {
      List<FullPointPosition> points = new ArrayList<>();
      points.add(fppEclMock);
      return points;
   }

   private AllMundanePositions createMundanePositions() {
      List<MundanePosition> mundPos = new ArrayList<>();
      mundPos.add(mundMock);
      List<MundanePosition> specPoints = new ArrayList<>();
      return new AllMundanePositions(mundPos, specPoints);
   }

   private List<AspectTypes> createAspects() {
      List<AspectTypes> aspects = new ArrayList<>();
      aspects.add(AspectTypes.SQUARE);
      return aspects;
   }


}