/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Enum for the position of the observer, this results in a geocentric chart, a topocentric chart
 * (using parallax correction), or a heliocentric chart. Is persistable as part of a configuration.
 */
public enum ObserverPositions implements Serializable {
   EMPTY(0, "observerpositions.unknown"),
   GEOCENTRIC(1, "observerpositions.geocentric"),
   TOPOCENTRIC(2, "observerpositions.topocentric");

   private final int id;
   private final String nameForRB;

   ObserverPositions(final int id, final String nameForRB) {
      this.id = id;
      this.nameForRB = nameForRB;
   }

   /**
    * Return an observer position for the specified index.
    *
    * @param id The index.
    * @return The resulting obserever position.
    */
   public static ObserverPositions getObserverPositionForId(final int id) throws UnknownIdException {
      for (ObserverPositions observerPos : ObserverPositions.values()) {
         if (observerPos.getId() == id) {
            return observerPos;
         }
      }
      throw new UnknownIdException("Tried to read ObserverPositions with invalid id : " + id);
   }

   /**
    * Create an observable list with names of observer positions that can be used in the UI, e.g. in a SelectBox.
    *
    * @return The constructed observable list.
    */
   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> observerPosNames = new ArrayList<>();
      for (ObserverPositions observerPosition : ObserverPositions.values()) {
         if (observerPosition != ObserverPositions.EMPTY)
            observerPosNames.add(rosetta.getText(observerPosition.nameForRB));
      }
      return FXCollections.observableArrayList(observerPosNames);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }

   public IndexMappingsList getIndexMappings() {
      List<IndexMapping> idList = new ArrayList<>();
      int runningIndex = 0;
      for (ObserverPositions observerPosition : ObserverPositions.values()) {
         if (observerPosition != ObserverPositions.EMPTY)
            idList.add(new IndexMapping(runningIndex++, observerPosition.getId()));
      }
      return new IndexMappingsList(idList);
   }
}
