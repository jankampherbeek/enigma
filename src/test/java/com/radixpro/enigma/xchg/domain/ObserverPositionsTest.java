/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;

import static org.junit.Assert.assertEquals;

public class ObserverPositionsTest {

   private final ObserverPositions observerPosition = ObserverPositions.TOPOCENTRIC;


   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getId() {
      assertEquals(2, observerPosition.getId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getRbKeyForName() {
      assertEquals("observerpositions.topocentric", observerPosition.getNameForRB());
   }


   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getObserverPositionForId() throws UnknownIdException {
      assertEquals(ObserverPositions.TOPOCENTRIC, observerPosition.getObserverPositionForId(2));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db(expected = UnknownIdException.class)
   public void getObserverPositionForIdNotFound() throws UnknownIdException {
      observerPosition.getObserverPositionForId(1000);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void total() {
      assertEquals(3, ObserverPositions.values().length);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getObservableList() {
      assertEquals(2, observerPosition.getObservableList().size());
   }
}