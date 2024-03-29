/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RatingsTest {

   private final Ratings rating = Ratings.C;

   @Test
   public void getId() {
      assertEquals(4, rating.getId());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("ratings.c", rating.getNameForRB());
   }

   @Test
   public void total() {
      assertEquals(8, Ratings.values().length);
   }

   @Test
   public void ratingForId() {
      assertEquals(Ratings.A, rating.getRatingForId(2));
   }

   @Test
   public void ratingForIdNotFound() {
      assertEquals(Ratings.ZZ, rating.getRatingForId(1000));
   }

   @Test
   public void ratingForName() {
      assertEquals(Ratings.B, Ratings.ratingForName("B - Biography or autobiography"));
   }

   @Test
   public void ratingForNameNotFound() {
      assertEquals(Ratings.ZZ, rating.ratingForName("i do not exist"));
   }

   @Test
   public void getObservableList() {
      var observableList = Ratings.getObservableList();
      assertEquals(8, observableList.size());
   }
}