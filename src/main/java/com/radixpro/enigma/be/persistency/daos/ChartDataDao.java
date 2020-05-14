/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.persistency.daos;

import com.opencsv.CSVReader;
import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.be.persistency.mappers.ChartDataCsvMapper;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.ChartData;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dao for chart data. Reads and persists the data to calcualte a chart, but not the calculated chart itself.
 */
public class ChartDataDao extends DaoParent {

   private static final Logger LOG = Logger.getLogger(ChartDataDao.class);
   private static final String CD_FILE = DB_LOCATION + "chartdata.csv";
   private static final String NEW_CD_FILE = DB_LOCATION + "new_chartdata.csv";
   private static final String OLD_CD_FILE = DB_LOCATION + "old_chartdata.csv";


   /**
    * Insert an instance of ChartData.
    *
    * @param insertChartData The ChartData instance to insert.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public void insert(final ChartData insertChartData) throws DatabaseException {
      checkNotNull(insertChartData);
      List<ChartData> allChartDataList = readAll();
      allChartDataList.add(insertChartData);
      List<String[]> allLines = new ArrayList<>();
      for (ChartData chartData : allChartDataList) {
         String[] csvData = new ChartDataCsvMapper().csvFromChartData(chartData);
         allLines.add(csvData);
      }
      writeData(allLines, CD_FILE);
   }

   /**
    * Update an instance of ChartData, the key is the id of ChartData.
    *
    * @param updateChartData The ChartData instance to update.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public void update(final ChartData updateChartData) throws DatabaseException {
      checkNotNull(updateChartData);
      List<ChartData> allChartDataList = readAll();
      List<String[]> updatedChartData = new ArrayList<>();
      ChartDataCsvMapper mapper = new ChartDataCsvMapper();
      long updateChartDataId = updateChartData.getId();
      for (ChartData chartData : allChartDataList) {
         ChartData effectiveChartData = (chartData.getId() == updateChartDataId ? updateChartData : chartData);
         String[] chartDataLine = mapper.csvFromChartData(effectiveChartData);
         updatedChartData.add(chartDataLine);
      }
      writeData(updatedChartData, NEW_CD_FILE);
      updateFiles(OLD_CD_FILE, CD_FILE, NEW_CD_FILE);
   }

   /**
    * Delete an instance of ChartData, the key is the id of ChartData.
    *
    * @param delChartData The ChartData instance to delete.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public void delete(final ChartData delChartData) throws DatabaseException {
      checkNotNull(delChartData);
      List<ChartData> allChartData = readAll();
      List<String[]> remainingChartDataList = new ArrayList<>();
      ChartDataCsvMapper mapper = new ChartDataCsvMapper();
      for (ChartData chartData : allChartData) {
         if (chartData.getId() != delChartData.getId()) {
            String[] remainingChartData = mapper.csvFromChartData(chartData);
            remainingChartDataList.add(remainingChartData);
         }
      }
      writeData(remainingChartDataList, NEW_CD_FILE);
      updateFiles(OLD_CD_FILE, CD_FILE, NEW_CD_FILE);
   }

   /**
    * Read a single instance of ChartData.
    *
    * @param chartDataId Id of the ChartData to retrieve.
    * @return List with ChartData, the list will be empty if the ChartData is not found.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public List<ChartData> read(final long chartDataId) throws DatabaseException {
      List<ChartData> allChartData = readAll();
      List<ChartData> foundChartData = new ArrayList<>();
      for (ChartData chartData : allChartData) {
         if (chartDataId == chartData.getId()) foundChartData.add(chartData);
      }
      return foundChartData;
   }

   /**
    * Read all instances of ChartData.
    *
    * @return A list with all instances of ChartData.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public List<ChartData> readAll() throws DatabaseException {
      List<String[]> allLines;
      List<ChartData> chartDataList = new ArrayList<>();
      try (CSVReader reader = createReader(CD_FILE)) {
         allLines = reader.readAll();
      } catch (IOException e) {
         throw new DatabaseException("IOException when reading all chart data : " + e.getMessage());
      }
      try {
         for (String[] line : allLines) {  // respectively id, key, value
            chartDataList.add(new ChartDataCsvMapper().chartDataFromCsv(line));
         }
      } catch (UnknownIdException e) {
         LOG.error("Exception when reading all chartdata. " + e.getMessage());
         throw new DatabaseException("Exception when reading all chartdata.");
      }
      return chartDataList;
   }


   /**
    * Search for an instance of ChartData with the name.
    *
    * @param searchName The name to search for. There should be an exact match.
    * @return A list with found instances of ChartData that have the same name as the searchname.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public List<ChartData> search(final String searchName) throws DatabaseException {
      checkNotNull(searchName);
      List<ChartData> allChartData = readAll();
      List<ChartData> foundChartData = new ArrayList<>();
      for (ChartData chartData : allChartData) {
         if (chartData.getChartMetaData().getName().contains(searchName)) foundChartData.add(chartData);
      }
      return foundChartData;
   }


   /**
    * Find the max Id for a DataChart in the database. Using thuis mimics the use of a sequence.
    *
    * @return The max id in the database.
    * @throws DatabaseException Any exception is logged and rethrown as a Database exception.
    */
   public long getMaxId() throws DatabaseException {
      List<ChartData> chartDataList = readAll();
      long maxId = 0;
      for (ChartData chartData : chartDataList) {
         if (chartData.getId() > maxId) maxId = chartData.getId();
      }
      return maxId;
   }

}
