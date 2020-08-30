/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.references.TimeZones;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.*;

/**
 * Converts inputted elements and creates instance of InputData.
 */
public class InputDataConversionBuilder {

   private final Rosetta rosetta;
   private String locationName;
   private String latitude;
   private String longitude;
   private String date;
   private String time;
   private String offsetLmt;
   private String source;

   public InputDataConversionBuilder(@NotNull final Rosetta rosetta) {
      this.rosetta = rosetta;
   }

   public InputDataConversionBuilder setLocationName(@NotNull final String locationName) {
      this.locationName = locationName;
      return this;
   }

   public InputDataConversionBuilder setLatitude(final int deg, final int min, final int sec, @NotNull final String direction) {
      this.latitude = createDMS(deg, min, sec) + direction;
      return this;
   }

   public InputDataConversionBuilder setLongitude(final int deg, final int min, final int sec, @NotNull final String direction) {
      this.longitude = createDMS(deg, min, sec) + direction;
      return this;
   }

   public InputDataConversionBuilder setDate(int year, int month, int day, @NotNull final String cal) {
      this.date = year + "/" + month + "/" + day + " " + cal;
      return this;
   }

   public InputDataConversionBuilder setTime(int hour, int min, int sec, @NotNull final TimeZones zone, boolean dst) {
      final String hourTxt = hour <= 9 ? "0" : "" + hour;
      final String minTxt = min <= 9 ? "0" : "" + min;
      final String secTxt = sec <= 9 ? "0" : "" + sec;
      final String zoneTxt = rosetta.getText(zone.getNameForRB());
      final String dstText = dst ? rosetta.getText("ui.shared.dst") : rosetta.getText("ui.shared.nodst");
      this.time = hourTxt + ":" + minTxt + ":" + secTxt + " " + zoneTxt + ". " + dstText;
      return this;
   }

   public InputDataConversionBuilder setOffsetLmt(final int deg, final int min, final int sec, @NotNull final String direction) {
      this.offsetLmt = "Offset " + createDMS(deg, min, sec) + direction;
      return this;
   }

   public InputDataConversionBuilder setSource(@NotNull final String source) {
      this.source = source;
      return this;
   }

   private String createDMS(final int deg, final int min, final int sec) {
      String degTxt = deg <= 9 ? "0" : "" + deg;
      String minTxt = min <= 9 ? "0" : "" + min;
      String secTxt = sec <= 9 ? "0" : "" + sec;
      return degTxt + DEGREESIGN + minTxt + MINUTESIGN + secTxt + SECONDSIGN;
   }

   public String build() {
      String inputData = "";
      if (null != locationName && !locationName.isBlank()) inputData += locationName + ",";
      if (null != latitude) inputData += latitude + ",";
      if (null != longitude) inputData += longitude + "\n";
      if (null != date) inputData += date + " ";
      if (null != time) inputData += time + "\n";
      if (null != offsetLmt) inputData += offsetLmt + "\n";
      if (null != source && !source.isBlank()) inputData += source;
      return inputData;
   }

}
