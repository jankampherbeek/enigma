/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.Rosetta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Time keys for progressions and directions.
 * TODO create abstract parent for enums with lookups, e.g. for TimeKeys, Ratings, ObserverPositions, EclipticProjections etc.
 */
public enum TimeKeys {

   NOT_DEFINED(0, "timekeys.notdefined"),
   REAL_SECUNDARY_SUN(1, "timekeys.realsecsun"),
   NAIBOD(2, "timekeys.naibod");

   private final int id;
   private final String nameForRB;

   TimeKeys(final int id, @NotNull final String nameForRb) {
      this.id = id;
      this.nameForRB = nameForRb;
   }

   /**
    * Return the timekey for a specified id.
    *
    * @param id The id for the time key.
    * @return The resulting time key.
    */
   public TimeKeys getTimeKeyForId(final int id) {
      for (TimeKeys timeKey : TimeKeys.values()) {
         if (timeKey.getId() == id) {
            return timeKey;
         }
      }
      return TimeKeys.NOT_DEFINED;
   }

   /**
    * Return the timekey for a specified name.
    *
    * @param keyName The anme for the timekey.
    * @return The resulting timekey.
    */
   public TimeKeys timeKeyForName(@NotNull final String keyName) {
      final Rosetta rosetta = Rosetta.getRosetta();
      for (TimeKeys timeKey : TimeKeys.values()) {
         if (rosetta.getText(timeKey.nameForRB).equals(keyName)) {
            return timeKey;
         }
      }
      return TimeKeys.NOT_DEFINED;
   }


   /**
    * Return an observable list with all timekeys, to be used in the UI.
    *
    * @return The resulting observable list.
    */
   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> keyNames = new ArrayList<>();
      for (TimeKeys timeKey : TimeKeys.values()) {
         if (timeKey != TimeKeys.NOT_DEFINED) keyNames.add(rosetta.getText(timeKey.nameForRB));
      }
      return FXCollections.observableArrayList(keyNames);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }
}