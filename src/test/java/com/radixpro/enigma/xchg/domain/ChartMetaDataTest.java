/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChartMetaDataTest {

   private final String name = "Thomas Ring";
   private final String description = "German astrologer";
   private final String source = "Published by the native";
   private final String sex = "m";
   private final ChartTypes chartType = ChartTypes.NATAL;
   private final Ratings rating = Ratings.AA;


   private ChartMetaData chartMetaData;

   @Before
   public void setUp() {
      List<Integer> categories = new ArrayList<>();
      categories.add(1);
      categories.add(33);
      chartMetaData = new ChartMetaData(name, description, source, chartType, rating);
   }

   @Test
   public void getName() {
      assertEquals(name, chartMetaData.getName());
   }

   @Test
   public void getDescription() {
      assertEquals(description, chartMetaData.getDescription());
   }

   @Test
   public void getSource() {
      assertEquals(source, chartMetaData.getSource());
   }

   @Test
   public void getChartType() {
      assertEquals(chartType, chartMetaData.getChartType());
   }

   @Test
   public void getRating() {
      assertEquals(rating, chartMetaData.getRating());
   }
}