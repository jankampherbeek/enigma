/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.analysis.ChartPointTypes;
import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Celestial bodies and id's to access the SE.
 */
public enum CelestialObjects implements IChartPoints {
   EMPTY(0, -1, CelObjectCategory.UNKNOWN, -1.0, "celobject.unknown"),
   SUN(1, 0, CelObjectCategory.CLASSICS, 1.0, "celobject.sun"),
   MOON(2, 1, CelObjectCategory.CLASSICS, 0.0748, "celobject.moon"),
   MERCURY(3, 2, CelObjectCategory.CLASSICS, 0.2408, "celobject.mercury"),
   VENUS(4, 3, CelObjectCategory.CLASSICS, 0.615, "celobject.venus"),
   //   EARTH(5, 14, CelObjectCategory.CLASSICS, 1.0, "celobject.earth"),
   MARS(6, 4, CelObjectCategory.CLASSICS, 1.881, "celobject.mars"),
   JUPITER(7, 5, CelObjectCategory.CLASSICS, 11.86, "celobject.jupiter"),
   SATURN(8, 6, CelObjectCategory.CLASSICS, 29.46, "celobject.saturn"),
   URANUS(9, 7, CelObjectCategory.MODERN, 84.01, "celobject.uranus"),
   NEPTUNE(10, 8, CelObjectCategory.MODERN, 164.8, "celobject.neptune"),
   PLUTO(11, 9, CelObjectCategory.MODERN, 248.1, "celobject.pluto"),
   CHEIRON(12, 15, CelObjectCategory.CENTAURS, 50.42, "celobject.cheiron"),
   MEAN_NODE(13, 10, CelObjectCategory.INTERSECTIONS, 18.61, "celobject.meannode"),
   TRUE_NODE(14, 11, CelObjectCategory.INTERSECTIONS, 18.61, "celobject.truenode");
   // prepared for future use:
//   PHOLUS(15, 16, CelObjectCategory.CENTAURS, "celobject.pholus"),
//   CERES(16, 17, CelObjectCategory.ASTEROIDS, 4.6, "celobject.ceres"),
//   PALLAS(17, 18, CelObjectCategory.ASTEROIDS, "celobject.pallas"),
//   JUNO(18, 19, CelObjectCategory.ASTEROIDS, "celobject.juno"),
//   VESTA(19, 20, CelObjectCategory.ASTEROIDS, 3.629, "celobject.vesta"),
//   NESSUS(20, 17066, CelObjectCategory.CENTAURS, "celobject.nessus"),
//   HUYA(21, 48628, CelObjectCategory.EXTRA_PLUT, "celobject.huya"),
//   MAKEMAKE(22, 146472, CelObjectCategory.EXTRA_PLUT, "celobject.makemake"),
//   HAUMEA(23, 146108, CelObjectCategory.EXTRA_PLUT, "celobject.haumea"),
//   ERIS(24, 146199, CelObjectCategory.EXTRA_PLUT, 557.o, "celobject.eris"),
//   IXION(25, 38978, CelObjectCategory.EXTRA_PLUT, "celobject.ixion"),
//   ORCUS(26, 100482, CelObjectCategory.EXTRA_PLUT, "celobject.orcus"),
//   QUAOAR(27, 60000, CelObjectCategory.EXTRA_PLUT, 287.5, "celobject.quaoar"),
//   SEDNA(28, 100377, CelObjectCategory.EXTRA_PLUT, 12050.0,"celobject.sedna"),
//   VARUNA(29, 30000, CelObjectCategory.EXTRA_PLUT, "celobject.varuna");

   private final int id;
   private final long seId;
   private final double orbitalPeriod;
   private final String nameForRB;
   private final CelObjectCategory category;
   private final ChartPointTypes pointType;

   /**
    * @param id            internally used id.
    * @param seId          id used by the SE.
    * @param category      category of the body.
    * @param orbitalPeriod siderealorbital period.
    * @param nameForRB     key to address the resource bundle.
    */
   CelestialObjects(final int id, final long seId, final CelObjectCategory category, final double orbitalPeriod, final String nameForRB) {
      this.id = id;
      this.seId = seId;
      this.orbitalPeriod = orbitalPeriod;
      this.category = checkNotNull(category);
      this.nameForRB = checkNotNull(nameForRB);
      this.pointType = ChartPointTypes.CEL_BODIES;
   }

   public CelestialObjects getCelObjectForId(int id) throws UnknownIdException {
      for (CelestialObjects celObject : CelestialObjects.values()) {
         if (celObject.getId() == id) {
            return celObject;
         }
      }
      throw new UnknownIdException("Tried to read CelestialObjects with invalid id : " + id);
   }


   /**
    * Create an observable list with names of celestial objects that can be used in the UI, e.g. in a CheckComboBox.
    *
    * @return The constructed observable list.
    */
   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> celObjectNames = new ArrayList<>();
      for (CelestialObjects celestialObject : CelestialObjects.values()) {
         if (celestialObject != CelestialObjects.EMPTY) celObjectNames.add(rosetta.getText(celestialObject.nameForRB));
      }
      return FXCollections.observableArrayList(celObjectNames);
   }

   @Override
   public int getId() {
      return id;
   }


   @Override
   public IChartPoints getItemForId(int id) {
      return null;
   }

   @Override
   public ChartPointTypes getPointType() {
      return null;
   }

   public long getSeId() {
      return seId;
   }

   @Override
   public String getRbKey() {
      return nameForRB;
   }

   public CelObjectCategory getCategory() {
      return category;
   }

   public double getOrbitalPeriod() {
      return orbitalPeriod;
   }

   public IndexMappingsList getIndexMappings() {
      List<IndexMapping> idList = new ArrayList<>();
      int runningIndex = 0;
      for (CelestialObjects celestialObject : CelestialObjects.values()) {
         if (celestialObject != CelestialObjects.EMPTY) {
            idList.add(new IndexMapping(runningIndex++, celestialObject.getId()));
         }
      }
      return new IndexMappingsList(idList);
   }

}


