/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Housesystems for calculation, internal id and id for SE.
 */
public enum HouseSystems {  // todo add NO_HOUSES at position 1, add Gauquelin at position 20
   EMPTY(0, "", "houses.unknown", 0, false, false, false),
   WHOLESIGN(2, "W", "houses.wholesign", 12, true, false, true),
   EQUAL(3, "A", "houses.equalasc", 12, true, false, true),
   EQUAL_MC(4, "D", "houses.equalmc", 12, true, false, true),
   VEHLOW(5, "V", "houses.vehlow", 12, true, false, false),
   PLACIDUS(6, "P", "houses.placidus", 12, true, true, true),
   KOCH(7, "K", "houses.koch", 12, true, true, true),
   PORPHYRI(8, "O", "houses.porphyri", 12, true, true, true),
   REGIOMONTANUS(9, "R", "houses.regiomontanus", 12, true, true, true),
   CAMPANUS(10, "C", "houses.campanus", 12, true, true, true),
   ALCABITIUS(11, "B", "houses.alcabitius", 12, true, true, true),
   TOPOCENTRIC(12, "T", "houses.topocentric", 12, true, true, true),
   KRUSINSKI(13, "U", "houses.krusinsky", 12, true, true, true),
   APC(14, "Y", "houses.apc", 12, true, true, true),
   MORIN(15, "M", "houses.morin", 12, true, false, true),
   AXIAL(16, "X", "houses.axial", 12, true, false, true),
   HORIZON(17, "H", "houses.azimuth", 12, true, false, true),
   CARTER(18, "F", "houses.carter", 12, true, false, true),
   EQUAL_ARIES(19, "N", "houses.equalaries", 12, true, false, true),
   SUNSHINE(21, "i", "houses.sunshine", 12, true, false, false),
   SUNSHINE_TREINDL(22, "I", "houses.sunshinetreindl", 12, true, false, true);

   private final String seId;
   private final int id;
   private final int nrOfCusps;
   private final String nameForRB;
   private final boolean counterClockwise;
   private final boolean quadrantSystem;
   private final boolean cuspIsStart;


   HouseSystems(final int id, final String seId, final String nameForRB, final int nrOfCusps,
                final boolean counterClockwise, final boolean quadrantSystem, final boolean cuspIsStart) {
      this.id = id;
      this.seId = seId;
      this.nameForRB = nameForRB;
      this.nrOfCusps = nrOfCusps;
      this.counterClockwise = counterClockwise;
      this.quadrantSystem = quadrantSystem;
      this.cuspIsStart = cuspIsStart;
   }

   public static HouseSystems getSystemForId(int id) throws UnknownIdException {
      for (HouseSystems system : HouseSystems.values()) {
         if (system.getId() == id) {
            return system;
         }
      }
      throw new UnknownIdException("Tried to read HouseSystems with invalid id : " + id);
   }

   /**
    * Create an observable list with names of house systems that can be used in the UI, e.g. in a SelectBox.
    *
    * @return The constructed observable list.
    */
   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> houseSystemNames = new ArrayList<>();
      for (HouseSystems houseSystem : HouseSystems.values()) {
         if (houseSystem != HouseSystems.EMPTY) houseSystemNames.add(rosetta.getText(houseSystem.nameForRB));
      }
      return FXCollections.observableArrayList(houseSystemNames);
   }

   public IndexMappingsList getIndexMappings() {
      List<IndexMapping> idList = new ArrayList<>();
      int runningIndex = 0;
      for (HouseSystems houseSystem : HouseSystems.values()) {
         if (houseSystem != HouseSystems.EMPTY) idList.add(new IndexMapping(runningIndex++, houseSystem.getId()));
      }
      return new IndexMappingsList(idList);
   }

   public String getSeId() {
      return seId;
   }

   public int getId() {
      return id;
   }

   public int getNrOfCusps() {
      return nrOfCusps;
   }

   public String getNameForRB() {
      return nameForRB;
   }

   public boolean isCounterClockwise() {
      return counterClockwise;
   }

   public boolean isQuadrantSystem() {
      return quadrantSystem;
   }

   public boolean isCuspIsStart() {
      return cuspIsStart;
   }

}
