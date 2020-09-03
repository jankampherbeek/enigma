/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.testsupport.TestSupport;
import com.radixpro.enigma.ui.helpers.DateTimeJulianCreator;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
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
      final DateTimeJulian dateTimeJulian =
            new DateTimeJulianCreator().createDateTime("2020/6/18", "G", "18:21:30", TimeZones.CET, false, 0.0d);
      final double geoLat = 52.216666666667;
      final double geoLon = 6.9;
      final Location location = new Location(geoLat, geoLon);
      String inputData = "";    // todo create inputData
      final ChartMetaData metaData = new ChartMetaData(name, "test for " + name, ChartTypes.NATAL, Ratings.DD, inputData);
      return new FullChartInputData(0, dateTimeJulian, location, metaData);
   }
}