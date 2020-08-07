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

public enum TimeZones {
   LMT(0, "timezone.lmt", 0.0),
   UT(1, "timezone.ut", 0.0),
   CET(2, "timezone.cet", 1.0),
   EET(3, "timezone.eet", 2.0),
   EAT(4, "timezone.eat", 3.0),
   IRST(5, "timezone.irst", 3.5),
   AMT(6, "timezone.amt", 4.0),
   AFT(7, "timezone.aft", 4.5),
   PKT(8, "timezone.pkt", 5.0),
   IST(9, "timezone.ist", 5.5),
   IOT(10, "timezone.iot", 6.0),
   MMT(11, "timezone.mmt", 6.5),
   ICT(12, "timezone.ict", 7.0),
   WST(13, "timezone.wst", 8.0),
   JST(14, "timezone.jst", 9.0),
   ACST(15, "timezone.acst", 9.5),
   AEST(16, "timezone.aest", 10.0),
   LHST(17, "timezone.lhst", 10.5),
   NCT(18, "timezone.nct", 11.0),
   NZST(19, "timezone.nzst", 12.0),
   SST(20, "timezone.sst", -11.0),
   HAST(21, "timezone.hast", -10.0),
   MART(22, "timezone.mart", -9.5),
   AKST(23, "timezone.akst", -9.0),
   PST(24, "timezone.pst", -8.0),
   MST(25, "timezone.mst", -7.0),
   CST(26, "timezone.cst", -6.0),
   EST(27, "timezone.est", -5.0),
   AST(28, "timezone.ast", -4.0),
   NST(29, "timezone.nst", -3.5),
   BRT(30, "timezone.brt", -3.0),
   GST(31, "timezone.gst", -2.0),
   AZOT(32, "timezone.azot", -1.0);

   private final int id;
   private final String nameForRB;
   private final double offset;

   TimeZones(final int id, final String nameForRB, final double offset) {
      this.id = id;
      this.nameForRB = nameForRB;
      this.offset = offset;
   }

   public static TimeZones timeZoneForId(final int id) {
      for (TimeZones timeZone : TimeZones.values()) {
         if (timeZone.getId() == id) {
            return timeZone;
         }
      }
      return TimeZones.UT;
   }

   public TimeZones timeZoneForName(final String zoneLocalName) {
      final Rosetta rosetta = Rosetta.getRosetta();
      for (TimeZones timeZone : TimeZones.values()) {
         if (rosetta.getText(timeZone.nameForRB).equals(zoneLocalName)) {
            return timeZone;
         }
      }
      return TimeZones.UT;
   }

   public ObservableList<String> getObservableList() {
      final Rosetta rosetta = Rosetta.getRosetta();
      final List<String> localnames = new ArrayList<>();
      for (TimeZones timeZone : TimeZones.values()) {
         localnames.add(rosetta.getText(timeZone.nameForRB));
      }
      return FXCollections.observableArrayList(localnames);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }

   public double getOffset() {
      return offset;
   }
}
