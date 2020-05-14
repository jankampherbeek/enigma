/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class CelestialObjectsTest {

   private CelestialObjects celBody;

   @Before
   public void setUp() {
      celBody = CelestialObjects.JUPITER;
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getId() {
      assertEquals(7, celBody.getId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getSeId() {
      assertEquals(5, celBody.getSeId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getCategory() {
      assertEquals(CelObjectCategory.CLASSICS, celBody.getCategory());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getRbKeyForName() {
      assertEquals("celobject.jupiter", celBody.getNameForRB());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void total() {
      assertEquals(15, CelestialObjects.values().length);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getCelObjectForId() throws UnknownIdException {
      assertEquals(CelestialObjects.JUPITER, CelestialObjects.SUN.getCelObjectForId(7));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db(expected = UnknownIdException.class)
   public void getCelObjectForIdNotFound() throws UnknownIdException {
      CelestialObjects.SUN.getCelObjectForId(7000);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getObservableList() {
      assertEquals(14, celBody.getObservableList().size());
   }
}