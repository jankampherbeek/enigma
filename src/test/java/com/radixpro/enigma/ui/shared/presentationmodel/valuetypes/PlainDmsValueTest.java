/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel.valuetypes;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PlainDmsValueTest {

   private PlainDmsValue plainDmsValue;

   @Before
   public void setUp() {
      double value = 123.456789054;
      plainDmsValue = new PlainDmsValue(value);
   }

   @Test
   public void getFormattedPosition() {
      String expected = "123" + DEGREESIGN + 27 + MINUTESIGN + 24 + SECONDSIGN;
      assertEquals(expected, plainDmsValue.getFormattedPosition());
   }

}