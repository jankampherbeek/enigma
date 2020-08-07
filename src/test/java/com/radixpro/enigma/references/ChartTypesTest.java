/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChartTypesTest {

   private final ChartTypes chartType = ChartTypes.EVENT;

   @Test
   public void getRbKeyForName() {
      assertEquals("charttype.event", chartType.getNameForRB());
   }

   @Test
   public void getId() {
      assertEquals(4, chartType.getId());
   }

   @Test
   public void chartTypeForId() {
      assertEquals(ChartTypes.EVENT, chartType.chartTypeForId(4));
   }

   @Test
   public void chartTypeForIdUnknown() {
      assertEquals(ChartTypes.UNKNOWN, chartType.chartTypeForId(1000));
   }

   @Test
   public void chartTypeForLocalName() {
      assertEquals(ChartTypes.HORARY, chartType.chartTypeForLocalName("Horary"));
   }

   @Test
   public void chartTypeForLocalNameNotFound() {
      assertEquals(ChartTypes.UNKNOWN, chartType.chartTypeForLocalName("i do not exist"));
   }

   @Test
   public void getObservableList() {
      var observableList = chartType.getObservableList();
      assertEquals(7, observableList.size());
   }
}