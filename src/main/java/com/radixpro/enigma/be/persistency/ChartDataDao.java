/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.shared.exceptions.DatabaseException;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao for chart data. Reads and persists the data to calcualte a chart, but not the calculated chart itself.
 */
public class ChartDataDao extends DaoParent {

   private static final Logger LOG = Logger.getLogger(ChartDataDao.class);
   private static final String SEL_CHARTS = "SELECT id, name, description, idcharttype, idrating, jdnr, cal, geolat, geolon, datainput ";
   private static final String ZERO = "0";
   private final AppDb appDb;

   public ChartDataDao() {
      this.appDb = AppDb.getInstance();
   }

   /**
    * Insert an instance of ChartData.
    *
    * @param insertFullChartInputData The ChartData instance to insert.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public int insert(@NotNull final FullChartInputData insertFullChartInputData) throws DatabaseException {
      final String insertChart = "INSERT INTO charts (id, name, description, idcharttype, idrating, jdnr, cal, geolat, geolon, datainput) " +
            "values(chartsseq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      final Connection con = appDb.getConnection();
      int chartId = -1;
      try (PreparedStatement pStmtCharts = con.prepareStatement(insertChart)) {
         pStmtCharts.setString(1, insertFullChartInputData.getChartMetaData().getName());
         pStmtCharts.setString(2, insertFullChartInputData.getChartMetaData().getDescription());
         pStmtCharts.setInt(3, insertFullChartInputData.getChartMetaData().getChartType().getId());
         pStmtCharts.setInt(4, insertFullChartInputData.getChartMetaData().getRating().getId());
         pStmtCharts.setDouble(5, insertFullChartInputData.getDateTimeJulian().getJd());
         pStmtCharts.setString(6, insertFullChartInputData.getDateTimeJulian().getCalendar());
         pStmtCharts.setDouble(7, insertFullChartInputData.getLocation().getGeoLat());
         pStmtCharts.setDouble(8, insertFullChartInputData.getLocation().getGeoLon());
         pStmtCharts.setString(9, insertFullChartInputData.getChartMetaData().getDataInput());
         int result = pStmtCharts.executeUpdate();
         if (result != 1)
            throw new DatabaseException("Could not insert chart " + insertFullChartInputData.getChartMetaData().getName() + " . No rows changed.");
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
         throw new DatabaseException("SQLException when inserting chart " + insertFullChartInputData.getChartMetaData().getName() + ". Exception :  " +
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
   public List<FullChartInputData> read(final int chartDataId) {
      List<FullChartInputData> fullChartInputDataList = new ArrayList<>();
      final String queryCharts = SEL_CHARTS + " FROM charts WHERE id = ?;";
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmt = con.prepareStatement(queryCharts)) {
         pStmt.setLong(1, chartDataId);
         try (ResultSet rsCharts = pStmt.executeQuery()) {
            while (rsCharts.next()) {
               fullChartInputDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading charts for id: " + chartDataId + " . Msg: " + throwables.getMessage());
      }
      return fullChartInputDataList;
   }

   /**
    * Read all instances of ChartData.
    *
    * @return A list with all instances of ChartData.
    */
   public List<FullChartInputData> readAll() {
      final String queryCharts = SEL_CHARTS + " FROM charts;";
      final Connection con = appDb.getConnection();
      List<FullChartInputData> fullChartInputDataList = new ArrayList<>();
      try {
         try (Statement stmtCharts = con.createStatement();
              ResultSet rsCharts = stmtCharts.executeQuery(queryCharts)) {
            while (rsCharts.next()) {
               fullChartInputDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading all charts. Msg : " + throwables.getMessage());
      }
      return fullChartInputDataList;
   }

   /**
    * Search for an instance of ChartData with the name.
    *
    * @param searchName The name to search for. There should be an exact match. Returns all Charts if searchName is an empty string. PRE: not null.
    * @return A list with found instances of ChartData that have the same name as the searchname.
    */
   public List<FullChartInputData> search(@NotNull final String searchName) {
      List<FullChartInputData> fullChartInputDataList = new ArrayList<>();
      final String queryCharts = SEL_CHARTS + " FROM charts WHERE name ILIKE '%' || ? || '%';";
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmt = con.prepareStatement(queryCharts)) {
         pStmt.setString(1, searchName);
         try (ResultSet rsCharts = pStmt.executeQuery()) {
            while (rsCharts.next()) {
               fullChartInputDataList.add(createChartData(rsCharts));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when searching for charts with name: " + searchName + " . Msg: " + throwables.getMessage());
      }
      return fullChartInputDataList;
   }

   private FullChartInputData createChartData(ResultSet rsCharts) throws SQLException {
      int id = rsCharts.getInt("id");
      String name = rsCharts.getString("name");
      String description = rsCharts.getString("description");
      ChartTypes chartType = ChartTypes.chartTypeForId(rsCharts.getInt("idcharttype"));
      Ratings rating = Ratings.getRatingForId(rsCharts.getInt("idrating"));
      double jdnr = rsCharts.getDouble("jdnr");
      String cal = rsCharts.getString("cal");
      double geoLat = rsCharts.getDouble("geolat");
      double geoLon = rsCharts.getDouble("geolon");
      String datainput = "" + rsCharts.getString("datainput");

      DateTimeJulian dateTime = new DateTimeJulian(jdnr, cal);
      Location location = new Location(geoLat, geoLon);
      ChartMetaData metaData = new ChartMetaData(name, description, chartType, rating, datainput);
      return new FullChartInputData(id, dateTime, location, metaData);
   }


//   private GeographicCoordinate createCoordinate(final String geoCoordTxt) {  // always 11 pos    ddd:mm:ss D
//      String input = geoCoordTxt;
//      if (10 == geoCoordTxt.length()) input = ZERO + geoCoordTxt;
//      int deg = Integer.parseInt(input.substring(0, 3));
//      int min = Integer.parseInt(input.substring(4, 6));
//      int sec = Integer.parseInt(input.substring(7, 9));
//      String dir = input.substring(10);
//      double value = deg + min / 60.0 + sec / 3600.0;
//      if (dir.equalsIgnoreCase("s") || dir.equalsIgnoreCase("w")) value = -value;
//      return new GeographicCoordinate(deg, min, sec, dir, value);
//   }
//
//   private String createCalDate(final FullDateTime dateTime) {
//      final String separator = "/";
//      SimpleDate sDate = dateTime.getSimpleDateTime().getDate();
//      int numYear = sDate.getYear();
//      int absYear = Math.abs(numYear);
//      String year = Integer.toString(absYear);
//      String month = Integer.toString(sDate.getMonth());
//      String day = Integer.toString(sDate.getDay());
//      if (absYear < 10) year = ZERO + year;
//      if (absYear < 100) year = ZERO + year;
//      if (absYear < 1000) year = ZERO + year;
//      year = (numYear >= 0 ? " " + year : "-" + year);
//      if (month.length() == 1) month = ZERO + month;
//      if (day.length() == 1) day = ZERO + day;
//      return year + separator + month + separator + day;
//   }

//   private double createTime(final FullDateTime dateTime) {
//      final String separator = ":";
//      SimpleTime sTime = dateTime.getSimpleDateTime().getTime();
//      return sTime.getHour() + sTime.getMinute() * 60.0 + sTime.getSecond() * 3600.0;
//   }

   private void handleDelete(final Connection con, final long id) throws SQLException {
      final String queryDelete = "DELETE charts where id = ?;";
      try (PreparedStatement pStmt = con.prepareStatement(queryDelete)) {
         pStmt.setLong(1, id);
         pStmt.executeUpdate();
      }
   }

}