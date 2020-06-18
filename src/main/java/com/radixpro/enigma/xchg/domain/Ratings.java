/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.Rosetta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enuim with ratings for the reliability of the time for a chart, as define buy Louise Rodden.
 * Adds a code 'ZZ' as fallback if the rating is unknown.
 * Is persistable as part of a saved chart.
 */
public enum Ratings implements Serializable {
   ZZ(0, "ratings.zz"),
   AA(1, "ratings.aa"),
   A(2, "ratings.a"),
   B(3, "ratings.b"),
   C(4, "ratings.c"),
   DD(5, "ratings.dd"),
   X(6, "ratings.x"),
   XX(7, "ratings.xx");

   private final int id;
   private final String nameForRB;

   Ratings(final int id, final String nameForRB) {
      this.id = id;
      this.nameForRB = nameForRB;
   }

   /**
    * Return the rating for a specified id.
    *
    * @param id The id for the rating.
    * @return The resulting rating.
    */
   public static Ratings getRatingForId(final int id) {
      for (Ratings rating : Ratings.values()) {
         if (rating.getId() == id) {
            return rating;
         }
      }
      return Ratings.ZZ;
   }

   /**
    * Return the rating for a specified name, using names as 'AA', 'X' etc.
    *
    * @param ratingName The anme for the rating.
    * @return The resulting rating.
    */
   public Ratings ratingForName(final String ratingName) {
      checkNotNull(ratingName);
      final Rosetta rosetta = Rosetta.getRosetta();
      for (Ratings rating : Ratings.values()) {
         if (rosetta.getText(rating.nameForRB).equals(ratingName)) {
            return rating;
         }
      }
      return Ratings.ZZ;
   }


   /**
    * Return an observable list with all ratings, to be used in the UI.
    *
    * @return The resulting observable list.
    */
   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> ratingNames = new ArrayList<>();
      for (Ratings rating : Ratings.values()) {
         ratingNames.add(rosetta.getText(rating.nameForRB));
      }
      return FXCollections.observableArrayList(ratingNames);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }
}