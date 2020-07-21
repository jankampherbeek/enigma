/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;

import javafx.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeKeysTest {

   private final TimeKeys timeKey = TimeKeys.NAIBOD;

   @Test
   public void total() {
      assertEquals(3, TimeKeys.values().length);
   }

   @Test
   public void getTimeKeyForId() {
      assertEquals(TimeKeys.REAL_SECUNDARY_SUN, timeKey.getTimeKeyForId(1));
   }

   @Test
   public void getTimeKeyForIdNotFound() {
      assertEquals(TimeKeys.NOT_DEFINED, timeKey.getTimeKeyForId(1000));
   }

   @Test
   public void timeKeyForName() {
      assertEquals(TimeKeys.REAL_SECUNDARY_SUN, timeKey.timeKeyForName("Secundary Sun"));
   }

   @Test
   public void timeKeyForNameNotFound() {
      assertEquals(TimeKeys.NOT_DEFINED, timeKey.timeKeyForName("i do not exist"));
   }

   @Test
   public void getObservableList() {
      ObservableList observableList = timeKey.getObservableList();
      assertEquals(2, observableList.size());
   }

   @Test
   public void getId() {
      assertEquals(2, timeKey.getId());
   }

   @Test
   public void getNameForRB() {
      assertEquals("timekeys.naibod", timeKey.getNameForRB());
   }
}
