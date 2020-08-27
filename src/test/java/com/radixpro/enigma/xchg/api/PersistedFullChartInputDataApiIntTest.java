/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.testsupport.TestSupport;
import com.radixpro.enigma.xchg.domain.ChartMetaData;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import com.radixpro.enigma.xchg.domain.GeographicCoordinate;
import com.radixpro.enigma.xchg.domain.LocationOld;
import org.junit.Before;

public class PersistedFullChartInputDataApiIntTest {

   private AppDb appDb;
   private PersistedChartDataApi pChartApi;

   @Before
   public void setUp() throws Exception {
      appDb = TestSupport.useDb();
      pChartApi = XchgApiInjector.injectPersistedChartDataApi(new AppScope());
   }

   // TODO enable and fix integrationtests for PersistedChartDataApi

//   @Test
//   public void insertReadAll() {
//      List<ChartData> charts = pChartApi.readAll();
//      assertTrue(charts.isEmpty());
//      int id = pChartApi.insert(createChartData("Jan"));
//      int id2 = pChartApi.insert(createChartData("Yvonne"));
//      charts = pChartApi.readAll();
//      assertEquals(2, charts.size());
//      assertEquals("Yvonne", charts.get(1).getChartMetaData().getName());
//      pChartApi.delete(id);
//      pChartApi.delete(id2);
//   }
//
//   @Test
//   public void delete() {
//      int id = pChartApi.insert(createChartData("Jan"));
//      int id2 = pChartApi.insert(createChartData("Yvonne"));
//      List<ChartData> charts = pChartApi.readAll();
//      assertEquals(2, charts.size());
//      pChartApi.delete(id);
//      charts = pChartApi.readAll();
//      assertEquals(1, charts.size());
//      assertEquals("Yvonne", charts.get(0).getChartMetaData().getName());
//      pChartApi.delete(id2);
//      charts = pChartApi.readAll();
//      assertTrue(charts.isEmpty());
//   }
//
//   @Test
//   public void read() {
//      int id = pChartApi.insert(createChartData("Jan"));
//      int id2 = pChartApi.insert(createChartData("Yvonne"));
//      ChartData result = pChartApi.read(id2).get(0);
//      assertEquals("Yvonne", result.getChartMetaData().getName());
//      pChartApi.delete(id);
//      pChartApi.delete(id2);
//   }
//
//
//   @Test
//   public void search() {
//      int id = pChartApi.insert(createChartData("Jan"));
//      int id2 = pChartApi.insert(createChartData("Yvonne"));
//      ChartData result = pChartApi.search("Yvon").get(0);
//      assertEquals("Yvonne", result.getChartMetaData().getName());
//      assertEquals("test for Yvonne", result.getChartMetaData().getDescription());
//      pChartApi.delete(id);
//      pChartApi.delete(id2);
//   }

   private FullChartInputData createChartData(final String name) {
      final SimpleTime sTime = new SimpleTime(18, 21, 30);
      final SimpleDate sDate = new SimpleDate(2020, 6, 18, true);
      final FullDateTime fDateTime = new FullDateTime(new SimpleDateTime(sDate, sTime), TimeZones.CET, true, 0.0);
      final GeographicCoordinate geoLat = new GeographicCoordinate(52, 13, 0, "n", 52.216666666667);
      final GeographicCoordinate geoLong = new GeographicCoordinate(6, 54, 0, "E", 6.9);
      final LocationOld locationOld = new LocationOld(geoLong, geoLat, "Enschede");
      final ChartMetaData metaData = new ChartMetaData(name, "test for " + name, "source", ChartTypes.NATAL, Ratings.DD);
      return new FullChartInputData(0, fDateTime, locationOld, metaData);
   }
}