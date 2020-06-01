package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.xchg.domain.*;

public class ChartDataCsvMapper {

   public ChartData chartDataFromCsv(final String[] csvLine) {
      return handleCsvLine(csvLine);
   }

   public String[] csvFromChartData(final ChartData chartData) {
      return handleChartData(chartData);
   }

   private ChartData handleCsvLine(final String[] csvLine) {
      long id = Long.parseLong(csvLine[0]);
      ChartMetaData chartMetaData = createMetaData(csvLine);
      FullDateTime fullDateTime = createFullDateTime(csvLine);
      Location location = createLocation(csvLine);
      return new ChartData(id, fullDateTime, location, chartMetaData);
   }

   private String[] handleChartData(final ChartData chartData) {
      String id = Long.toString(chartData.getId());
      String name = chartData.getChartMetaData().getName();
      String description = chartData.getChartMetaData().getDescription();
      String source = chartData.getChartMetaData().getSource();
      String chartType = Integer.toString(chartData.getChartMetaData().getChartType().getId());
      String rating = Integer.toString(chartData.getChartMetaData().getRating().getId());
      String year = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getDate().getYear());
      String month = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getDate().getMonth());
      String day = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getDate().getDay());
      String greg = chartData.getFullDateTime().getSimpleDateTime().getDate().isGregorian() ? "y" : "n";
      String hour = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getTime().getHour());
      String minute = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getTime().getMinute());
      String second = Integer.toString(chartData.getFullDateTime().getSimpleDateTime().getTime().getSecond());
      String timezone = Integer.toString(chartData.getFullDateTime().getTimeZone().getId());
      String dst = chartData.getFullDateTime().isDst() ? "y" : "n";
      String offsetLmt = Double.toString(chartData.getFullDateTime().getOffsetForLmt());
      String locName = chartData.getLocation().getName();
      String longDeg = Integer.toString(chartData.getLocation().getLongInput().getDegrees());
      String longMin = Integer.toString(chartData.getLocation().getLongInput().getMinutes());
      String longSec = Integer.toString(chartData.getLocation().getLongInput().getSeconds());
      String longDir = chartData.getLocation().getLongInput().getDirection();
      String longVal = Double.toString(chartData.getLocation().getLongInput().getValue());

      String latDeg = Integer.toString(chartData.getLocation().getLatInput().getDegrees());
      String latMin = Integer.toString(chartData.getLocation().getLatInput().getMinutes());
      String latSec = Integer.toString(chartData.getLocation().getLatInput().getSeconds());
      String latDir = chartData.getLocation().getLatInput().getDirection();
      String latVal = Double.toString(chartData.getLocation().getLatInput().getValue());

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
