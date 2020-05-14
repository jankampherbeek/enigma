/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleDateTimeTest {

   @Mock
   private SimpleDate dateMock;
   @Mock
   private SimpleTime timeMock;
   private SimpleDateTime dateTime;

   @Before
   public void setUp() throws Exception {
      dateTime = new SimpleDateTime(dateMock, timeMock);
   }

   @Test
   public void getSimpleDate() {
      assertEquals(dateMock, dateTime.getDate());
   }

   @Test
   public void getSimpleTime() {
      assertEquals(timeMock, dateTime.getTime());
   }

}