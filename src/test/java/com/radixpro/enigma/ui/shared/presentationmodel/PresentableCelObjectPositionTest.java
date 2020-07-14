/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointCoordinate;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointPosition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentableCelObjectPositionTest {

   private final double[] azAlt = {150.123456, -12.5};
   @Mock
   private FullPointPosition positionMock;
   @Mock
   private FullPointCoordinate eclPositionMock;
   @Mock
   private FullPointCoordinate equPositionMock;
   private PresentableCelObjectPosition presPos;

   @Before
   public void setUp() {
      when(eclPositionMock.getPosition().getMainCoord()).thenReturn(220.5);
      when(eclPositionMock.getPosition().getDeviation()).thenReturn(2.25);
      when(eclPositionMock.getSpeed().getMainCoord()).thenReturn(0.3333334);
      when(eclPositionMock.getSpeed().getDeviation()).thenReturn(-0.0528);
      when(eclPositionMock.getPosition().getDistance()).thenReturn(8.532897658210534);
      when(eclPositionMock.getSpeed().getDistance()).thenReturn(0.5);
      when(equPositionMock.getPosition().getMainCoord()).thenReturn(100.5);
      when(equPositionMock.getPosition().getDeviation()).thenReturn(-0.25);
      when(equPositionMock.getSpeed().getMainCoord()).thenReturn(-1.3333334);
      when(equPositionMock.getSpeed().getMainCoord()).thenReturn(0.0528);
      when(positionMock.getEclPos()).thenReturn(eclPositionMock);
      when(positionMock.getEqPos()).thenReturn(equPositionMock);
      when(positionMock.getCelObject()).thenReturn(CelestialObjects.MARS);
      presPos = new PresentableCelObjectPosition(positionMock, azAlt);
   }

   @Test
   public void getFormattedLongitude() {
      assertEquals("10" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedLongitude());
   }

   @Test
   public void getFormattedLongSpeed() {
      assertEquals(" +0" + DEGREESIGN + "20" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedLongSpeed());
   }

   @Test
   public void getFormattedLatitude() {
      assertEquals(" +2" + DEGREESIGN + "15" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedLatitude());
   }

   @Test
   public void getFormattedLatSpeed() {
      assertEquals(" -0" + DEGREESIGN + "03" + MINUTESIGN + "10" + SECONDSIGN, presPos.getFormattedLatSpeed());
   }

   @Test
   public void getSignGlyph() {
      assertEquals("8", presPos.getSignGlyph());
   }

   @Test
   public void getCelBodyGlyph() {
      assertEquals("f", presPos.getCelBodyGlyph());
   }

   @Test
   public void getFormattedRightAscension() {
      assertEquals("100" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedRightAscension());
   }

   @Test
   public void getFormattedRaSpeed() {
      assertEquals(" -1" + DEGREESIGN + "20" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedRaSpeed());
   }

   @Test
   public void getFormattedDeclination() {
      assertEquals(" -0" + DEGREESIGN + "15" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedDeclination());
   }

   @Test
   public void getFormattedDeclSpeed() {
      assertEquals(" +0" + DEGREESIGN + "03" + MINUTESIGN + "10" + SECONDSIGN, presPos.getFormattedDeclSpeed());
   }

   @Test
   public void getFormattedAzimuth() {
      assertEquals("150" + DEGREESIGN + "07" + MINUTESIGN + "24" + SECONDSIGN, presPos.getFormattedAzimuth());
   }

   @Test
   public void getFormattedAltitude() {
      assertEquals("-12" + DEGREESIGN + "30" + MINUTESIGN + "00" + SECONDSIGN, presPos.getFormattedAltitude());
   }

   @Test
   public void getFormattedDistance() {
      assertEquals("  8.53289765", presPos.getFormattedDistance());
   }

   @Test
   public void getFormattedDistSpeed() {
      assertEquals("  0.50000000", presPos.getFormattedDistSpeed());
   }

}