/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObserverPositionsTest {

   private final ObserverPositions observerPosition = ObserverPositions.TOPOCENTRIC;

   @Test
   public void getId() {
      assertEquals(2, observerPosition.getId());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("observerpositions.topocentric", observerPosition.getNameForRB());
   }


   @Test
   public void getObserverPositionForId() throws UnknownIdException {
      assertEquals(ObserverPositions.TOPOCENTRIC, observerPosition.getObserverPositionForId(2));
   }

   @Test(expected = UnknownIdException.class)
   public void getObserverPositionForIdNotFound() throws UnknownIdException {
      observerPosition.getObserverPositionForId(1000);
   }

   @Test
   public void total() {
      assertEquals(3, ObserverPositions.values().length);
   }

   @Test
   public void getObservableList() {
      assertEquals(2, observerPosition.getObservableList().size());
   }

   @Test
   public void getIndexMappings() {
      assertEquals(2, observerPosition.getIndexMappings().getAllIndexMappings().size());
   }
}