/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.be.calc.assist.EquatorialPositionForHouses;
import com.radixpro.enigma.be.calc.assist.HousePosition;
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
   private HousePosition housePositionMock;
   private double[] azAlt;
   private final String name = "Asc";
   @Mock
   private EquatorialPositionForHouses equatorialPositionForHousesMock;
   private PresentableMundanePosition presMundanePos;

   @Before
   public void setUp() {
      double ra = 125.25;
      when(equatorialPositionForHousesMock.getRightAscension()).thenReturn(ra);
      double decl = -12.2000001;
      when(equatorialPositionForHousesMock.getDeclination()).thenReturn(decl);
      double azimuth = 189.0;
      double altitude = 12.33334;
      azAlt = new double[]{azimuth, altitude};
      double longitude = 123.5;
      when(housePositionMock.getLongitude()).thenReturn(longitude);
      when(housePositionMock.getEquatorialPositionForHouses()).thenReturn(equatorialPositionForHousesMock);
      when(housePositionMock.getEclipticHorizontalConverter()).thenReturn(azAlt);
      presMundanePos = new PresentableMundanePosition(name, housePositionMock);
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