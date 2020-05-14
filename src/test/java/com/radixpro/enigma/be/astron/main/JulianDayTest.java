/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.main;

import com.radixpro.enigma.xchg.domain.SimpleDate;
import com.radixpro.enigma.xchg.domain.SimpleDateTime;
import com.radixpro.enigma.xchg.domain.SimpleTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JulianDayTest {

   private static final double DELTA = 0.00000001;
   @Mock
   private SimpleDateTime dateTimeMock;
   @Mock
   private SimpleDate dateMock;
   @Mock
   private SimpleTime timeMock;
   private JulianDay julianDay;

   @Before
   public void setUp() throws Exception {
      when(dateMock.getYear()).thenReturn(2000);
      when(dateMock.getMonth()).thenReturn(1);
      when(dateMock.getDay()).thenReturn(1);
      when(timeMock.getHour()).thenReturn(0);
      when(timeMock.getMinute()).thenReturn(0);
      when(timeMock.getSecond()).thenReturn(0);
      when(dateTimeMock.getDate()).thenReturn(dateMock);
      when(dateTimeMock.getTime()).thenReturn(timeMock);
      julianDay = new JulianDay(dateTimeMock);
   }

   @Test
   public void getJdNrEt() {
      assertEquals(2451557.5007428704, julianDay.getJdNrEt(), DELTA);
   }

   @Test
   public void getJdNrUt() {
      assertEquals(2451557.500003979, julianDay.getJdNrUt(), DELTA);
   }
}