/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.persistency.daos;

import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.shared.AppDb;
import com.radixpro.enigma.xchg.domain.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dao for chart data. Reads and persists the data to calcualte a chart, but not the calculated chart itself.
 */
public class ChartDataDao extends DaoParent {

   private static final Logger LOG = Logger.getLogger(ChartDataDao.class);
   private static final String SEL_CHARTS =
         "SELECT id, name, description, source, idcharttype, idrating, caldate, time, calendar, dst, idtz, offsetlmt, locname, geolong, geolat ";
   private static final String ZERO = "0";

   /**
    * Insert an instance of ChartData.
    *
    * @param insertChartData The ChartData instance to insert.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public int insert(final ChartData insertChartData) throws DatabaseException {
      checkNotNull(insertChartData);
      final String insertChart = "INSERT INTO charts (id, name, description, source, idcharttype, idrating, caldate, time, calendar, dst, idtz, offsetlmt" +
            ", locname, geolong, geolat) values(chartsseq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      int chartId = -1;
      try (PreparedStatement pStmtCharts = con.prepareStatement(insertChart)) {
         pStmtCharts.setString(1, insertChartData.getChartMetaData().getName());
         pStmtCharts.setString(2, insertChartData.getChartMetaData().getDescription());
         pStmtCharts.setString(3, insertChartData.getChartMetaData().getSource());
         pStmtCharts.setInt(4, insertChartData.getChartMetaData().getChartType().getId());
         pStmtCharts.setInt(5, insertChartData.getChartMetaData().getRating().getId());
         pStmtCharts.setString(6, createCalDate(insertChartData.getFullDateTime()));
         pStmtCharts.setString(7, createTime(insertChartData.getFullDateTime()));
         pStmtCharts.setString(8, (insertChartData.getFullDateTime().getSimpleDateTime().getDate().isGregorian() ? "g" : "j"));
         pStmtCharts.setBoolean(9, insertChartData.getFullDateTime().isDst());
         pStmtCharts.setInt(10, insertChartData.getFullDateTime().getTimeZone().getId());
         pStmtCharts.setDouble(11, insertChartData.getFullDateTime().getOffsetForLmt());
         pStmtCharts.setString(12, insertChartData.getLocation().getName());
         pStmtCharts.setString(13, createGeoLongitude(insertChartData.getLocation()));
         pStmtCharts.setString(14, createGeoLatitude(insertChartData.getLocation()));

         int result = pStmtCharts.executeUpdate();
         if (result != 1) throw new DatabaseException("Could not insert chart " + insertChartData.getChartMetaData().getName() + " . No rows changed.");
         final String queryCurrVal = "SELECT chartsseq.CURRVAL;";
         try (Statement stmtCurrval = con.createStatement();
              ResultSet rsCurrVal = stmtCurrval.executeQuery(queryCurrVal)) {
            while (rsCurrVal.next()) {
               chartId = rsCurrVal.getInt(1);
            }
         }
         con.commit();
      } catch (SQLException throwables) {
         try {
            con.rollback();
         } catch (SQLException e) {
            LOG.error("SQLException when trying to rollback : " + e.getMessage());
         }
         throw new DatabaseException("SQLException when inserting chart " + insertChartData.getChartMetaData().getName() + ". Exception :  " +
               throwables.getMessage());
      } finally {
         appDb.closeConnection();
      }
      return chartId;
   }

   /**
    * Delete chart.
    *
    * @param id id of chart to delete
    */
   public void delete(final int id) {
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      try {
         handleDelete(con, id);
      } catch (SQLException throwables) {
         LOG.error("SQLException while deleting chart : " + id + " . Msg : " + throwables.getMessage());
      }
   }

   /**
    * Read a single instance of ChartData.
    *
    * @param chartDataId Id of the ChartData to retrieve.
    * @return List with ChartData, the list will be empty if the ChartData is not found.
    */
   public List<ChartData> read(final int chartDataId) {
      List<ChartData> chartDataList = new ArrayList<>();
      final String queryCharts = SEL_CHARTS + " FROM charts WHERE id = ?;";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmt = con.prepareStatement(queryCharts)) {
         pStmt.setLong(1, chartDataId);
         try (ResultSet rsCharts = pStmt.executeQuery()) {
            while (rsCharts.next()) {
               chartDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading charts for id: " + chartDataId + " . Msg: " + throwables.getMessage());
      }
      return chartDataList;
   }

   /**
    * Read all instances of ChartData.
    *
    * @return A list with all instances of ChartData.
    */
   public List<ChartData> readAll() {
      final String queryCharts = SEL_CHARTS + " FROM charts;";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      List<ChartData> chartDataList = new ArrayList<>();
      try {
         try (Statement stmtCharts = con.createStatement();
              ResultSet rsCharts = stmtCharts.executeQuery(queryCharts)) {
            while (rsCharts.next()) {
               chartDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading all charts. Msg : " + throwables.getMessage());
      }
      return chartDataList;
   }

   /**
    * Search for an instance of ChartData with the name.
    *
    * @param searchName The name to search for. There should be an exact match. Returns all Charts if searchName is an empty string. PRE: not null.
    * @return A list with found instances of ChartData that have the same name as the searchname.
    */
   public List<ChartData> search(final String searchName) {
      checkNotNull(searchName);
      List<ChartData> chartDataList = new ArrayList<>();
      final String queryCharts = SEL_CHARTS + " FROM charts WHERE name ILIKE '%' || ? || '%';";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmt = con.prepareStatement(queryCharts)) {
         pStmt.setString(1, searchName);
         try (ResultSet rsCharts = pStmt.executeQuery()) {
            while (rsCharts.next()) {
               chartDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when searching for charts with name: " + searchName + " . Msg: " + throwables.getMessage());
      }
      return chartDataList;
   }

   private ChartData createChartData(ResultSet rsCharts) throws SQLException {
      int id = rsCharts.getInt("id");
      String name = rsCharts.getString("name");
      String description = rsCharts.getString("description");
      String source = rsCharts.getString("source");
      int idChartType = rsCharts.getInt("idcharttype");
      int idRating = rsCharts.getInt("idrating");
      String calDate = rsCharts.getString("caldate");
      String time = rsCharts.getString("time");
      String calendar = rsCharts.getString("calendar");
      boolean dst = rsCharts.getBoolean("dst");
      int idTz = rsCharts.getInt("idtz");
      double offsetLmt = rsCharts.getDouble("offsetlmt");
      String locName = rsCharts.getString("locname");
      String geoLong = rsCharts.getString("geolong");
      String geoLat = rsCharts.getString("geolat");
      FullDateTime fullDateTime = createDateTime(calDate, time, calendar, dst, idTz, offsetLmt);
      Location location = new Location(createCoordinate(geoLong), createCoordinate(geoLat), locName);
      ChartMetaData metaData = new ChartMetaData(name, description, source, ChartTypes.chartTypeForId(idChartType), Ratings.getRatingForId(idRating));
      return new ChartData(id, fullDateTime, location, metaData);
   }

   private FullDateTime createDateTime(final String date, final String time, final String cal, final boolean dst, final int idTz, final double offsetLmt) {
      String[] dateParts = date.split("/");
      int year = Integer.parseInt(dateParts[0].trim());
      int month = Integer.parseInt(dateParts[1].trim());
      int day = Integer.parseInt(dateParts[2].trim());
      String[] timeParts = time.split(":");
      int hour = Integer.parseInt(timeParts[0].trim());
      int minute = Integer.parseInt(timeParts[1].trim());
      int second = Integer.parseInt(timeParts[2].trim());
      SimpleDate simpleDate = new SimpleDate(year, month, day, cal.equalsIgnoreCase("g"));
      SimpleTime simpleTime = new SimpleTime(hour, minute, second);
      SimpleDateTime simpleDateTime = new SimpleDateTime(simpleDate, simpleTime);
      return new FullDateTime(simpleDateTime, TimeZones.timeZoneForId(idTz), dst, offsetLmt);
   }

   private GeographicCoordinate createCoordinate(final String geoCoordTxt) {  // always 11 pos    ddd:mm:ss D
      String input = geoCoordTxt;
      if (10 == geoCoordTxt.length()) input = ZERO + geoCoordTxt;
      int deg = Integer.parseInt(input.substring(0, 3));
      int min = Integer.parseInt(input.substring(4, 6));
      int sec = Integer.parseInt(input.substring(7, 9));
      String dir = input.substring(10);
      double value = deg + min / 60.0 + sec / 3600.0;
      if (dir.equalsIgnoreCase("s") || dir.equalsIgnoreCase("w")) value = -value;
      return new GeographicCoordinate(deg, min, sec, dir, value);
   }

   private String createCalDate(final FullDateTime dateTime) {
      final String separator = "/";
      SimpleDate sDate = dateTime.getSimpleDateTime().getDate();
      int numYear = sDate.getYear();
      int absYear = Math.abs(numYear);
      String year = Integer.toString(absYear);
      String month = Integer.toString(sDate.getMonth());
      String day = Integer.toString(sDate.getDay());
      if (absYear < 10) year = ZERO + year;
      if (absYear < 100) year = ZERO + year;
      if (absYear < 1000) year = ZERO + year;
      year = (numYear >= 0 ? " " + year : "-" + year);
      if (month.length() == 1) month = ZERO + month;
      if (day.length() == 1) day = ZERO + day;
      return year + separator + month + separator + day;
   }

   private String createTime(final FullDateTime dateTime) {
      final String separator = ":";
      SimpleTime sTime = dateTime.getSimpleDateTime().getTime();
      String hour = Integer.toString(sTime.getHour());
      String minute = Integer.toString(sTime.getMinute());
      String second = Integer.toString(sTime.getSecond());
      if (hour.length() == 1) hour = ZERO + hour;
      if (minute.length() == 1) minute = ZERO + minute;
      if (second.length() == 1) second = ZERO + second;
      return hour + separator + minute + separator + second;
   }

   private String createGeoLongitude(final Location location) {
      final String separator = ":";
      GeographicCoordinate geoLong = location.getLongInput();
      final String direction = geoLong.getDirection();
      String degree = Integer.toString(geoLong.getDegrees());
      String minute = Integer.toString(geoLong.getMinutes());
      String second = Integer.toString(geoLong.getSeconds());
      if (degree.length() < 3) degree = ZERO + degree;
      if (degree.length() < 3) degree = ZERO + degree;  // repeat to cover initial length of 1
      if (minute.length() < 2) minute = ZERO + minute;
      if (second.length() < 2) second = ZERO + second;
      return degree + separator + minute + separator + second + " " + direction;
   }

   private String createGeoLatitude(final Location location) {
      final String separator = ":";
      GeographicCoordinate geoLat = location.getLatInput();
      final String direction = geoLat.getDirection();
      String degree = Integer.toString(geoLat.getDegrees());
      String minute = Integer.toString(geoLat.getMinutes());
      String second = Integer.toString(geoLat.getSeconds());
      if (degree.length() < 2) degree = ZERO + degree;
      if (minute.length() < 2) minute = ZERO + minute;
      if (second.length() < 2) second = ZERO + second;
      return degree + separator + minute + separator + second + " " + direction;
   }


   private void handleDelete(final Connection con, final long id) throws SQLException {
      final String queryDelete = "DELETE charts where id = ?;";
      try (PreparedStatement pStmt = con.prepareStatement(queryDelete)) {
         pStmt.setLong(1, id);
         pStmt.executeUpdate();
      }
   }

}