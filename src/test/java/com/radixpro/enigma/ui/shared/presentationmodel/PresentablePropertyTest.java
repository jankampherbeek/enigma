/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PresentablePropertyTest {

   private final String name = "PropName";
   private final String value = "PropValue";
   private PresentableProperty property;

   @Before
   public void setUp() throws Exception {
      property = new PresentableProperty(name, value);
   }

   @Test
   public void getName() {
      assertEquals(name, property.getName());
   }

   @Test
   public void getValue() {
      assertEquals(value, property.getValue());
   }
}