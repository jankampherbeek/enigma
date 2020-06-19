/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertyTest {

   private final String key = "myKey";
   private final String value = "myValue";
   private Property prop;

   @Before
   public void setUp() {
      prop = new Property(key, value);
   }

   @Test
   public void getKey() {
      assertEquals(key, prop.getKey());
   }

   @Test
   public void getValue() {
      assertEquals(value, prop.getValue());
   }


   @Test
   public void testToString() {
      assertEquals("Property(key=myKey, value=myValue)", prop.toString());
   }

   @Test
   public void testGetValue() {
      assertEquals(value, prop.getValue());
   }

   @Test
   public void setValue() {
      prop.setValue("newValue");
      assertEquals("newValue", prop.getValue());
   }
}