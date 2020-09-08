/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.jetbrains.annotations.NotNull;

/**
 * Scenario types for statistical research.
 */
public enum StatsScenarioTypes {

   DUAL(1, "statsscenariotype.dual.name", "statsscenariotype.dual.descr"),
   TRIPLE(2, "statsscenariotype.triple.name", "statsscenariotype.triple.descr"),
   MULTIPLE_SINGLE(3, "statsscenariotype.multiplesingle.name", "statsscenariotype.multiplesingle.descr"),
   RANGE(4, "statsscenariotype.range.name", "statsscenariotype.range.descr"),
   MINMAX(5, "statsscenariotype.minmax.name", "statsscenariotype.minmax.descr");

   private final int id;
   private final String rbName;
   private final String rbDescr;

   StatsScenarioTypes(final int id,
                      @NotNull final String rbName,
                      @NotNull final String rbDescr) {
      this.id = id;
      this.rbName = rbName;
      this.rbDescr = rbDescr;
   }

   public static StatsScenarioTypes getScenarioTypeForId(final int id) throws UnknownIdException {
      for (StatsScenarioTypes type : StatsScenarioTypes.values()) {
         if (type.getId() == id) {
            return type;
         }
      }
      throw new UnknownIdException("Tried to read StatsScenarioType with invalid id : " + id);
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }

   public String getRbDescr() {
      return rbDescr;
   }
}
