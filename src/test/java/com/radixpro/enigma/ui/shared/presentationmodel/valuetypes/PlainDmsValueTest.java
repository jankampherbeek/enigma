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
   private double value = 123.456789054;
   private String expected = "123" + DEGREESIGN + 27 + MINUTESIGN + 24 + SECONDSIGN;

   @Before
   public void setUp() throws Exception {
      plainDmsValue = new PlainDmsValue(value);
   }

   @Test
   public void getFormattedPosition() {
      assertEquals(expected, plainDmsValue.getFormattedPosition());
   }

}