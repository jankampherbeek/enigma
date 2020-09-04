package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.ui.helpers.DateTimeJulianCreator;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import org.jetbrains.annotations.NotNull;

/**
 * Should remain available up to release 2020.2 to support reading existing csv data.
 */
public class ChartDataCsvMapper {

   private final DateTimeJulianCreator dateTimeJulianCreator;
   private final Rosetta rosetta;

   public ChartDataCsvMapper(@NotNull final DateTimeJulianCreator dateTimeJulianCreator, @NotNull final Rosetta rosetta) {
      this.dateTimeJulianCreator = dateTimeJulianCreator;
      this.rosetta = rosetta;
   }

   public FullChartInputData chartDataFromCsv(final String[] csvLine) {
      return handleCsvLine(csvLine);
   }

   private FullChartInputData handleCsvLine(final String[] csvLine) {
      int id = Integer.parseInt(csvLine[0]);
      ChartMetaData chartMetaData = createMetaData(csvLine);
      DateTimeJulian dateTime = createDateTime(csvLine);
      Location location = createLocation(csvLine);
      return new FullChartInputData(id, dateTime, location, chartMetaData);
   }

   private ChartMetaData createMetaData(String[] csvLine) {
      String name = csvLine[1];
      String description = csvLine[2];
      String source = csvLine[3];
      ChartTypes chartType = ChartTypes.UNKNOWN.chartTypeForId(Integer.parseInt(csvLine[4]));
      Ratings rating = Ratings.ZZ.getRatingForId(Integer.parseInt(csvLine[5]));
      String SPACE = " ";
      String NEWLINE = "\n";
      String dateText = createDateText(csvLine);
      String calText = csvLine[5].equalsIgnoreCase("Y") ? "G" : "J";
      String timeText = createTimeText(csvLine);
      String locationText = createLocationText(csvLine);
      String inputData = dateText + SPACE + calText + SPACE + timeText + NEWLINE + locationText + NEWLINE + rosetta.getText("ui.shared.source") + SPACE +
            source;
      return new ChartMetaData(name, description, chartType, rating, inputData);
   }

   private String createLocationText(String[] csvLine) {
      String locName = csvLine[16];
      String locLon = csvLine[17] + ":" + csvLine[18] + ":" + csvLine[19] + " " + csvLine[20];
      String locLat = csvLine[21] + ":" + csvLine[22] + ":" + csvLine[23] + " " + csvLine[24];
      return locName + " " + locLon + " " + locLat;
   }

   private String createDateText(String[] csvLine) {
      String year = csvLine[6];
      String month = csvLine[7];
      String day = csvLine[8];
      String sep = "/";
      return year + sep + month + sep + day;
   }

   private String createTimeText(String[] csvLine) {
      String hour = csvLine[10];
      String minute = csvLine[11];
      String second = csvLine[12];
      TimeZones timeZone = TimeZones.UT.timeZoneForId(Integer.parseInt(csvLine[13]));
      String zone = rosetta.getText(timeZone.getNameForRB());
      boolean dst = "y".equalsIgnoreCase(csvLine[14]);
      String dstText = rosetta.getText("y".equalsIgnoreCase(csvLine[14]) ? "ui.shared.dst" : "ui.shared.nodst");
      String sep = ":";
      String offsetLmtTxt = "";
      if (timeZone == TimeZones.LMT) offsetLmtTxt = csvLine[15] + " ";
      return hour + sep + minute + sep + second + " " + zone + " " + dstText + " " + offsetLmtTxt;
   }

   private DateTimeJulian createDateTime(String[] csvLine) {
      String dateText = createDateText(csvLine);
      String timeText = createTimeText(csvLine);
      String cal = csvLine[9];
      TimeZones timeZone = TimeZones.UT.timeZoneForId(Integer.parseInt(csvLine[13]));
      boolean dst = "y".equalsIgnoreCase(csvLine[14]);
      double offsetLmt = Double.parseDouble(csvLine[15]);
      return dateTimeJulianCreator.createDateTime(dateText, cal, timeText, timeZone, dst, offsetLmt);
   }

   private Location createLocation(String[] csvLine) {
      double latVal = Double.parseDouble(csvLine[26]);
      double longVal = Double.parseDouble(csvLine[21]);
      return new Location(latVal, longVal);
   }

}
