package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.xchg.domain.ChartMetaData;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import com.radixpro.enigma.xchg.domain.GeographicCoordinate;
import com.radixpro.enigma.xchg.domain.Location;

/**
 * Should remain avaiable up to release 2020.2 to support reading existing csv data.
 */
public class ChartDataCsvMapper {

   public FullChartInputData chartDataFromCsv(final String[] csvLine) {
      return handleCsvLine(csvLine);
   }

   public String[] csvFromChartData(final FullChartInputData fullChartInputData) {
      return handleChartData(fullChartInputData);
   }

   private FullChartInputData handleCsvLine(final String[] csvLine) {
      int id = Integer.parseInt(csvLine[0]);
      ChartMetaData chartMetaData = createMetaData(csvLine);
      FullDateTime fullDateTime = createFullDateTime(csvLine);
      Location location = createLocation(csvLine);
      return new FullChartInputData(id, fullDateTime, location, chartMetaData);
   }

   private String[] handleChartData(final FullChartInputData fullChartInputData) {
      String id = Long.toString(fullChartInputData.getId());
      String name = fullChartInputData.getChartMetaData().getName();
      String description = fullChartInputData.getChartMetaData().getDescription();
      String source = fullChartInputData.getChartMetaData().getSource();
      String chartType = Integer.toString(fullChartInputData.getChartMetaData().getChartType().getId());
      String rating = Integer.toString(fullChartInputData.getChartMetaData().getRating().getId());
      String year = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getDate().getYear());
      String month = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getDate().getMonth());
      String day = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getDate().getDay());
      String greg = fullChartInputData.getFullDateTime().getSimpleDateTime().getDate().isGregorian() ? "y" : "n";
      String hour = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getTime().getHour());
      String minute = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getTime().getMinute());
      String second = Integer.toString(fullChartInputData.getFullDateTime().getSimpleDateTime().getTime().getSecond());
      String timezone = Integer.toString(fullChartInputData.getFullDateTime().getTimeZone().getId());
      String dst = fullChartInputData.getFullDateTime().isDst() ? "y" : "n";
      String offsetLmt = Double.toString(fullChartInputData.getFullDateTime().getOffsetForLmt());
      String locName = fullChartInputData.getLocation().getName();
      String longDeg = Integer.toString(fullChartInputData.getLocation().getLongInput().getDegrees());
      String longMin = Integer.toString(fullChartInputData.getLocation().getLongInput().getMinutes());
      String longSec = Integer.toString(fullChartInputData.getLocation().getLongInput().getSeconds());
      String longDir = fullChartInputData.getLocation().getLongInput().getDirection();
      String longVal = Double.toString(fullChartInputData.getLocation().getLongInput().getValue());

      String latDeg = Integer.toString(fullChartInputData.getLocation().getLatInput().getDegrees());
      String latMin = Integer.toString(fullChartInputData.getLocation().getLatInput().getMinutes());
      String latSec = Integer.toString(fullChartInputData.getLocation().getLatInput().getSeconds());
      String latDir = fullChartInputData.getLocation().getLatInput().getDirection();
      String latVal = Double.toString(fullChartInputData.getLocation().getLatInput().getValue());

      return new String[]{id, name, description, source, chartType, rating, year, month, day, greg, hour, minute,
            second, timezone, dst, offsetLmt, locName, longDeg, longMin, longSec, longDir, longVal, latDeg, latMin,
            latSec, latDir, latVal};
   }

   private ChartMetaData createMetaData(String[] csvLine) {
      String name = csvLine[1];
      String description = csvLine[2];
      String source = csvLine[3];
      ChartTypes chartType = ChartTypes.UNKNOWN.chartTypeForId(Integer.parseInt(csvLine[4]));
      Ratings rating = Ratings.ZZ.getRatingForId(Integer.parseInt(csvLine[5]));
      return new ChartMetaData(name, description, source, chartType, rating);
   }

   private SimpleDate createSimpleDate(String[] csvLine) {
      int year = Integer.parseInt(csvLine[6]);
      int month = Integer.parseInt(csvLine[7]);
      int day = Integer.parseInt(csvLine[8]);
      boolean greg = "y".equalsIgnoreCase(csvLine[9]);
      return new SimpleDate(year, month, day, greg);
   }

   private SimpleTime createSimpleTime(String[] csvLine) {
      int hour = Integer.parseInt(csvLine[10]);
      int minute = Integer.parseInt(csvLine[11]);
      int second = Integer.parseInt(csvLine[12]);
      return new SimpleTime(hour, minute, second);
   }

   private FullDateTime createFullDateTime(String[] csvLine) {
      SimpleDate simpleDate = createSimpleDate(csvLine);
      SimpleTime simpleTime = createSimpleTime(csvLine);
      SimpleDateTime simpleDateTime = new SimpleDateTime(simpleDate, simpleTime);
      TimeZones timeZone = TimeZones.UT.timeZoneForId(Integer.parseInt(csvLine[13]));
      boolean dst = "y".equalsIgnoreCase(csvLine[14]);
      double offsetLmt = Double.parseDouble(csvLine[15]);
      return new FullDateTime(simpleDateTime, timeZone, dst, offsetLmt);
   }

   private Location createLocation(String[] csvLine) {
      String locName = csvLine[16];
      int longDeg = Integer.parseInt(csvLine[17]);
      int longMin = Integer.parseInt(csvLine[18]);
      int longSec = Integer.parseInt(csvLine[19]);
      String longDir = csvLine[20];
      double longVal = Double.parseDouble(csvLine[21]);
      int latDeg = Integer.parseInt(csvLine[22]);
      int latMin = Integer.parseInt(csvLine[23]);
      int latSec = Integer.parseInt(csvLine[24]);
      String latDir = csvLine[25];
      double latVal = Double.parseDouble(csvLine[26]);
      GeographicCoordinate longInput = new GeographicCoordinate(longDeg, longMin, longSec, longDir, longVal);
      GeographicCoordinate latInput = new GeographicCoordinate(latDeg, latMin, latSec, latDir, latVal);
      return new Location(longInput, latInput, locName);
   }

}
