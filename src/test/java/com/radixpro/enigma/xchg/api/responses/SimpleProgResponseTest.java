/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleProgResponseTest {

   @Mock
   private IProgCalcRequest requestMock;
   private List<IPosition> positions;
   private SimpleProgResponse response;

   @Before
   public void setUp() throws Exception {
      positions = new ArrayList<>();
      response = new SimpleProgResponse(positions, requestMock);
   }

   @Test
   public void getPositions() {
      assertEquals(positions, response.getPositions());
   }

   @Test
   public void getRequest() {
      assertEquals(requestMock, response.getRequest());
   }

}