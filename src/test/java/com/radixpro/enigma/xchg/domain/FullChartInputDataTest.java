/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FullChartInputDataTest {

   private final int id = 123;
   private DateTimeJulian dateTime;
   private Location location;
   private ChartMetaData chartMetaData;
   private FullChartInputData fullChartInputData;

   @Before
   public void setUp() {
      location = new Location(0.0, 0.0);
      dateTime = new DateTimeJulian(123.456, "G");
      chartMetaData = new ChartMetaData("a", "b", ChartTypes.ELECTION, Ratings.A, "c");
      fullChartInputData = new FullChartInputData(id, dateTime, location, chartMetaData);
   }

   @Test
   public void getSimpleDateTime() {
      assertEquals(dateTime, fullChartInputData.getDateTimeJulian());
   }

   @Test
   public void getLocation() {
      assertEquals(location, fullChartInputData.getLocation());
   }

   @Test
   public void getId() {
      assertEquals(id, fullChartInputData.getId());
   }

   @Test
   public void getChartMetaData() {
      assertEquals(chartMetaData, fullChartInputData.getChartMetaData());
   }

}