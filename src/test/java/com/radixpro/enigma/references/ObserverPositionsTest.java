/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

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
   public void getObserverPositionForId() {
      assertEquals(ObserverPositions.TOPOCENTRIC, ObserverPositions.getObserverPositionForId(2));
   }

   @Test
   public void getObserverPositionForIdNotFound() {
      assertEquals(ObserverPositions.GEOCENTRIC, ObserverPositions.getObserverPositionForId(1000));
   }

   @Test
   public void total() {
      assertEquals(2, ObserverPositions.values().length);
   }

   @Test
   public void getObservableList() {
      assertEquals(2, ObserverPositions.getObservableList().size());
   }

   @Test
   public void getIndexMappings() {
      assertEquals(2, ObserverPositions.getIndexMappings().getAllIndexMappings().size());
   }
}