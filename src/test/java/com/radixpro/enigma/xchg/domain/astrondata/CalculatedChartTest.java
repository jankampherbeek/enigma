/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatedChartTest {

   @Mock
   private FullPointPosition celPoint1Mock;
   @Mock
   private FullPointPosition celPoint2Mock;
   @Mock
   private MundanePosition mundPoint1Mock;
   @Mock
   private MundanePosition mundPoint2Mock;
   private CalculatedChart calculatedChart;

   @Before
   public void setUp() {
      List<FullPointPosition> celPoints = new ArrayList<>();
      celPoints.add(celPoint1Mock);
      celPoints.add(celPoint2Mock);
      List<MundanePosition> mundPoints = new ArrayList<>();
      mundPoints.add(mundPoint1Mock);
      mundPoints.add(mundPoint2Mock);
      List<MundanePosition> ascMc = new ArrayList<>();
      AllMundanePositions allMundanePositions = new AllMundanePositions(mundPoints, ascMc);
      calculatedChart = new CalculatedChart(celPoints, allMundanePositions);
   }

   @Test
   public void getCelPoints() {
      assertEquals(2, calculatedChart.getCelPoints().size());
      assertEquals(celPoint1Mock, calculatedChart.getCelPoints().get(0));
   }

   @Test
   public void getMundPoints() {
      assertEquals(2, calculatedChart.getMundPoints().getCusps().size());
      assertEquals(mundPoint2Mock, calculatedChart.getMundPoints().getCusps().get(1));
   }
}