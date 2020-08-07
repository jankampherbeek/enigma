/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgTechniquesTest {

   final ProgTechniques pTech = ProgTechniques.SOLAR;

   @Test
   public void getRbValue() {
      assertEquals("progtechnique.solar", pTech.getRbValue());
   }

   @Test
   public void values() {
      assertEquals(4, ProgTechniques.values().length);
   }
}