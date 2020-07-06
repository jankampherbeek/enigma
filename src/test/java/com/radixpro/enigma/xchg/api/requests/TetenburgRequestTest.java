/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TetenburgRequestTest {

   private final double longMcRadix = 283.56;
   private final double solarSpeed = 1.01;
   @Mock
   private Location locationMock;
   @Mock
   private FullDateTime birthDateTimeMock;
   @Mock
   private FullDateTime progDateTimeMock;
   private TetenburgRequest request;

   @Before
   public void setUp() throws Exception {
      request = new TetenburgRequest(longMcRadix, solarSpeed, locationMock, birthDateTimeMock, progDateTimeMock);
   }

   @Test
   public void getLongMcRadix() {
      assertEquals(longMcRadix, request.getLongMcRadix(), DELTA_8_POS);
   }

   @Test
   public void getSolarSpeed() {
      assertEquals(solarSpeed, request.getSolarSpeed(), DELTA_8_POS);
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, request.getLocation());
   }

   @Test
   public void getBirthDateTime() {
      assertEquals(birthDateTimeMock, request.getBirthDateTime());
   }

   @Test
   public void getProgDateTime() {
      assertEquals(progDateTimeMock, request.getProgDateTime());
   }
}