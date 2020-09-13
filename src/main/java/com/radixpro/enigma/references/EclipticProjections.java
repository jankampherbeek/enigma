/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum EclipticProjections {
   TROPICAL(1, "eclipticprojections.tropical"),
   SIDEREAL(2, "eclipticprojections.sidereal");

   private final int id;
   private final String nameForRB;
   private static final Logger LOG = Logger.getLogger(EclipticProjections.class);

   EclipticProjections(final int id, @NotNull final String nameForRB) {
      this.id = id;
      this.nameForRB = nameForRB;
   }

   public static EclipticProjections getProjectionForId(final int id) {
      for (EclipticProjections eclipticProjection : EclipticProjections.values()) {
         if (eclipticProjection.getId() == id) {
            return eclipticProjection;
         }
      }
      LOG.error("Could not find EclipticPRojection for id : " + id + ". Returned TROPICAL instead.");
      return EclipticProjections.TROPICAL;
   }

   /**
    * Create an observable list with names of ecliptical projections that can be used in the UI, e.g. in a SelectBox.
    *
    * @return The constructed observable list.
    */
   public static ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> eclipticalProjNames = new ArrayList<>();
      for (EclipticProjections eclipticProjection : EclipticProjections.values()) {
         eclipticalProjNames.add(rosetta.getText(eclipticProjection.nameForRB));
      }
      return FXCollections.observableArrayList(eclipticalProjNames);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }

   public static IndexMappingsList getIndexMappings() {
      List<IndexMapping> idList = new ArrayList<>();
      int runningIndex = 0;
      for (EclipticProjections eclipticProjection : EclipticProjections.values()) {
         idList.add(new IndexMapping(runningIndex++, eclipticProjection.getId()));
      }
      return new IndexMappingsList(idList);
   }

}
