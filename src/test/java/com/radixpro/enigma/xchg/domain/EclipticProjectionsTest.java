/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class EclipticProjectionsTest {

   private EclipticProjections projection;

   @Before
   public void setUp() {
      projection = EclipticProjections.SIDEREAL;
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getId() {
      assertEquals(2, projection.getId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getRbKeyForName() {
      assertEquals("eclipticprojections.sidereal", projection.getNameForRB());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getProjectionForId() throws UnknownIdException {
      assertEquals(EclipticProjections.TROPICAL, projection.getProjectionForId(1));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db(expected = UnknownIdException.class)
   public void getProjectionForIdNotFound() throws UnknownIdException {
      projection.getProjectionForId(1000);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getObservableList() {
      assertEquals(2, projection.getObservableList().size());
   }
}