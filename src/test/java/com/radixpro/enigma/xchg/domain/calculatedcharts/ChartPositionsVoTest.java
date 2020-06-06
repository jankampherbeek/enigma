/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedcharts;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChartPositionsVoTest {

   private final long chartId = 1223L;
   private List<SimplePosVo> celestialPoints;
   private List<SimplePosVo> mundanePositions;
   private ChartPositionsVo vo;

   @Before
   public void setUp() {
      celestialPoints = new ArrayList<>();
      celestialPoints.add(new SimplePosVo(CelestialObjects.SATURN, 100.0, 1.0, 102.0, 3.0));
      mundanePositions = new ArrayList<>();
      vo = new ChartPositionsVo(chartId, celestialPoints, mundanePositions);
   }

   @Test
   public void getChartId() {
      assertEquals(chartId, vo.getChartId());
   }

   @Test
   public void getCelestialPoints() {
      assertEquals(celestialPoints, vo.getCelestialPoints());
   }

   @Test
   public void getMundanePositions() {
      assertEquals(mundanePositions, vo.getMundanePositions());
   }

   @Test(expected = IllegalArgumentException.class)
   public void errorTwoEmptyArrays() {
      celestialPoints = new ArrayList<>();
      mundanePositions = new ArrayList<>();
      vo = new ChartPositionsVo(chartId, celestialPoints, mundanePositions);
   }

}