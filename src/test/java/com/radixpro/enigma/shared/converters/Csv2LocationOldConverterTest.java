/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.shared.exceptions.InputDataException;
import com.radixpro.enigma.xchg.domain.LocationOld;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class Csv2LocationOldConverterTest {

   private String lonTxt = "123W45";
   private String latTxt = "33N18";
   private String lonTxtError = "12345";
   private String latTxtError = "33Q18";
   private String lonTxtNumericError = "123Eab";
   private Csv2LocationConverter converter;

   @Before
   public void startUp() {
      converter = new Csv2LocationConverter();
   }

   @Test
   public void convertHappyFlow() throws InputDataException {
      LocationOld loc = converter.convert(lonTxt, latTxt);
      assertEquals(123, loc.getLongInput().getDegrees());
      assertEquals(45, loc.getLongInput().getMinutes());
      assertEquals(0, loc.getLongInput().getSeconds());
      assertEquals("W", loc.getLongInput().getDirection());
      assertEquals(123.75, loc.getGeoLong(), DELTA_8_POS);
      assertEquals(33, loc.getLatInput().getDegrees());
      assertEquals(18, loc.getLatInput().getMinutes());
      assertEquals(0, loc.getLatInput().getSeconds());
      assertEquals(33.3, loc.getGeoLat(), DELTA_8_POS);
   }

   @Test(expected = InputDataException.class)
   public void convertErrorNoDir() throws InputDataException {
      converter.convert(lonTxtError, latTxt);
   }

   @Test(expected = InputDataException.class)
   public void convertErrorWrongDir() throws InputDataException {
      converter.convert(lonTxt, latTxtError);
   }

   @Test(expected = InputDataException.class)
   public void convertNumericError() throws InputDataException {
      converter.convert(lonTxtNumericError, latTxt);
   }

}