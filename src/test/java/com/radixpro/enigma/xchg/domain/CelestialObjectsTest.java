/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CelestialObjectsTest {

   private CelestialObjects celBody;

   @Before
   public void setUp() {
      celBody = CelestialObjects.JUPITER;
   }

   @Test
   public void getId() {
      assertEquals(7, celBody.getId());
   }

   @Test
   public void getSeId() {
      assertEquals(5, celBody.getSeId());
   }

   @Test
   public void getCategory() {
      assertEquals(CelObjectCategory.CLASSICS, celBody.getCategory());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("celobject.jupiter", celBody.getRbKey());
   }

   @Test
   public void total() {
      assertEquals(14, CelestialObjects.values().length);
   }

   @Test
   public void getCelObjectForId() throws UnknownIdException {
      assertEquals(CelestialObjects.JUPITER, CelestialObjects.SUN.getCelObjectForId(7));
   }

   @Test(expected = UnknownIdException.class)
   public void getCelObjectForIdNotFound() throws UnknownIdException {
      CelestialObjects.SUN.getCelObjectForId(7000);
   }

   @Test
   public void getObservableList() {
      assertEquals(13, celBody.getObservableList().size());
   }

   @Test
   public void getOrbitalPeriod() {
      assertEquals(84.01, CelestialObjects.URANUS.getOrbitalPeriod(), 0.000000001);
   }
}