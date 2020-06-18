/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.shared.AppDb;
import com.radixpro.enigma.testsupport.DbTestSupport;
import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersistedChartDataApiIntTest {

   private AppDb appDb;
   private PersistedChartDataApi pChartApi;

   @Before
   public void setUp() throws Exception {
      new DbTestSupport();
      appDb = AppDb.getInstance();
      pChartApi = new PersistedChartDataApi();
   }

   @Test
   public void insertReadAll() {
      List<ChartData> charts = pChartApi.readAll();
      assertTrue(charts.isEmpty());
      int id = pChartApi.insert(createChartData("Jan"));
      int id2 = pChartApi.insert(createChartData("Yvonne"));
      charts = pChartApi.readAll();
      assertEquals(2, charts.size());
      assertEquals("Yvonne", charts.get(1).getChartMetaData().getName());
      pChartApi.delete(id);
      pChartApi.delete(id2);
   }

   @Test
   public void delete() {
      int id = pChartApi.insert(createChartData("Jan"));
      int id2 = pChartApi.insert(createChartData("Yvonne"));
      List<ChartData> charts = pChartApi.readAll();
      assertEquals(2, charts.size());
      pChartApi.delete(id);
      charts = pChartApi.readAll();
      assertEquals(1, charts.size());
      assertEquals("Yvonne", charts.get(0).getChartMetaData().getName());
      pChartApi.delete(id2);
      charts = pChartApi.readAll();
      assertTrue(charts.isEmpty());
   }

   @Test
   public void read() {
      int id = pChartApi.insert(createChartData("Jan"));
      int id2 = pChartApi.insert(createChartData("Yvonne"));
      ChartData result = pChartApi.read(id2).get(0);
      assertEquals("Yvonne", result.getChartMetaData().getName());
      pChartApi.delete(id);
      pChartApi.delete(id2);
   }


   @Test
   public void search() {
      int id = pChartApi.insert(createChartData("Jan"));
      int id2 = pChartApi.insert(createChartData("Yvonne"));
      ChartData result = pChartApi.search("Yvon").get(0);
      assertEquals("Yvonne", result.getChartMetaData().getName());
      assertEquals("test for Yvonne", result.getChartMetaData().getDescription());
      pChartApi.delete(id);
      pChartApi.delete(id2);
   }

   private ChartData createChartData(final String name) {
      final SimpleTime sTime = new SimpleTime(18, 21, 30);
      final SimpleDate sDate = new SimpleDate(2020, 6, 18, true);
      final FullDateTime fDateTime = new FullDateTime(new SimpleDateTime(sDate, sTime), TimeZones.CET, true, 0.0);
      final GeographicCoordinate geoLat = new GeographicCoordinate(52, 13, 0, "n", 52.216666666667);
      final GeographicCoordinate geoLong = new GeographicCoordinate(6, 54, 0, "E", 6.9);
      final Location location = new Location(geoLong, geoLat, "Enschede");
      final ChartMetaData metaData = new ChartMetaData(name, "test for " + name, "source", ChartTypes.NATAL, Ratings.DD);
      return new ChartData(0, fDateTime, location, metaData);
   }
}