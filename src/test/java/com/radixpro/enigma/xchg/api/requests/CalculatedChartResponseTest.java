/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.responses.CalculatedChartResponse;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatedChartResponseTest {

   @Mock
   private CalculatedChart calculatedChartMock;
   private String resultMsg = "OK";
   private CalculatedChartResponse response;

   @Before
   public void setUp() throws Exception {
      response = new CalculatedChartResponse(calculatedChartMock, resultMsg);
   }

   @Test
   public void getCalculatedChart() {
      assertEquals(calculatedChartMock, response.getCalculatedChart());
   }

   @Test
   public void getResultMsg() {
      assertEquals(resultMsg, response.getResultMsg());
   }

   @Test(expected = IllegalArgumentException.class)
   public void emptyResultMsg() {
      response = new CalculatedChartResponse(calculatedChartMock, "");
   }
}