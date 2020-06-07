/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedAspectTransit;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransitsAspectResponseTest {

   private final long chartId = 333;
   private List<IAnalyzedPair> transitAspects;
   private TransitsAspectResponse response;

   @Before
   public void setUp() throws Exception {
      transitAspects = createAspects();
      response = new TransitsAspectResponse(chartId, transitAspects);
   }

   @Test
   public void getChartId() {
      assertEquals(chartId, response.getChartId());
   }

   @Test
   public void getAnalyzedAspects() {
      assertEquals(transitAspects, response.getAnalyzedAspects());
   }

   private List<IAnalyzedPair> createAspects() {
      List<IAnalyzedPair> aspects = new ArrayList<>();
      aspects.add(new AnalyzedAspectTransit(new AnalyzablePoint(CelestialObjects.SATURN, 100.0),
            new AnalyzablePoint(CelestialObjects.MARS, 279.5),
            AspectTypes.OPPOSITION, 0.5, 1.0));
      return aspects;
   }
}