/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeFlagsTest {

   private SeFlags seFlags;

   @Before
   public void setUp() {
      seFlags = SeFlags.EQUATORIAL;
   }

   @Test
   public void getSeValue() {
      assertEquals(2048L, seFlags.getSeValue());
   }

   @Test
   public void total() {
      assertEquals(7, SeFlags.values().length);
   }
}
