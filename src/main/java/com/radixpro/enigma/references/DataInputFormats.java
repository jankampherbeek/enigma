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

import java.util.ArrayList;
import java.util.List;

public enum DataInputFormats {
   UNDEFINED("datainputformat.undefined"),
   CSV_CHARTS_STANDARD("datainputformat.csv.chart.standard"),
   CSV_EVENTS_STANDARD("datainputformat.csv.event.standard");

   private final String rbKey;

   DataInputFormats(final String rbKey) {
      this.rbKey = rbKey;
   }

   public String getRbKey() {
      return rbKey;
   }

   public ObservableList<String> getObservableList() {
      final List<String> formatKeys = new ArrayList<>();
      for (DataInputFormats format : DataInputFormats.values()) {
         formatKeys.add(Rosetta.getText(format.getRbKey()));
      }
      return FXCollections.observableArrayList(formatKeys);
   }

   public DataInputFormats formatForName(final String name) {
      for (DataInputFormats format : DataInputFormats.values()) {
         if (Rosetta.getText(format.getRbKey()).equals(name)) {
            return format;
         }
      }
      return DataInputFormats.UNDEFINED;
   }

}


