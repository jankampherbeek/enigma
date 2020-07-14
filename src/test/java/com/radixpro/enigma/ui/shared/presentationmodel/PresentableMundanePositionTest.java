/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;


import com.radixpro.enigma.xchg.domain.astrondata.MundanePosition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentableMundanePositionTest {

   @Mock
   private MundanePosition mundPosMock;
   private double[] azAlt;
   private final String name = "Asc";
   private PresentableMundanePosition presMundanePos;

   @Before
   public void setUp() {
      double ra = 125.25;
      double decl = -12.2000001;
      double azimuth = 189.0;
      double altitude = 12.33334;
      azAlt = new double[]{azimuth, altitude};
      double longitude = 123.5;
      when(mundPosMock.getLongitude()).thenReturn(longitude);
      when(mundPosMock.getEqPos().getMainCoord()).thenReturn(ra);
      when(mundPosMock.getEqPos().getDeviation()).thenReturn(decl);
      when(mundPosMock.getHorPos().getMainCoord()).thenReturn(azimuth);
      when(mundPosMock.getHorPos().getDeviation()).thenReturn(altitude);
      presMundanePos = new PresentableMundanePosition(name, mundPosMock);
   }

   @Test
   public void getName() {
      assertEquals(name, presMundanePos.getName());
   }

   @Test
   public void getFormattedLongitude() {
      assertEquals(" 3" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN,
            presMundanePos.getFormattedLongitude());
   }

   @Test
   public void getFormattedRa() {
      assertEquals("125" + DEGREESIGN + "15" + MINUTESIGN + "00" + SECONDSIGN,
            presMundanePos.getFormattedRa());
   }

   @Test
   public void getFormattedDeclination() {
      assertEquals("-12" + DEGREESIGN + "12" + MINUTESIGN + "00" + SECONDSIGN,
            presMundanePos.getFormattedDeclination());
   }

   @Test
   public void getFormattedAzimuth() {
      assertEquals("189" + DEGREESIGN + "00" + MINUTESIGN + "00" + SECONDSIGN,
            presMundanePos.getFormattedAzimuth());
   }

   @Test
   public void getFormattedAltitude() {
      assertEquals("+12" + DEGREESIGN + "20" + MINUTESIGN + "00" + SECONDSIGN,
            presMundanePos.getFormattedAltitude());
   }

   @Test
   public void getSignGlyph() {
      assertEquals("5", presMundanePos.getSignGlyph());
   }
}