/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.api.requests.ProgCalcRequestFake;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleProgResponseTest {

   private List<SimplePosVo> positions;
   private IProgCalcRequest request;
   private SimpleProgResponse response;

   @Before
   public void setUp() throws Exception {
      positions = createPositions();
      request = new ProgCalcRequestFake();
      response = new SimpleProgResponse(positions, request);
   }

   @Test
   public void getPositions() {
      assertEquals(positions, response.getPositions());
   }

   @Test
   public void getRequest() {
      assertEquals(request, response.getRequest());
   }

   private List<SimplePosVo> createPositions() {
      List<SimplePosVo> points = new ArrayList<>();
      points.add(new SimplePosVo(CelestialObjects.SATURN, 10.0, -1.0, 12.0, 1.5));
      return points;
   }
}