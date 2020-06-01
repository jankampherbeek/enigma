/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IndexMappingsListTest {

   private IndexMappingsList indexMappingsList;

   @Before
   public void setUp() {
      indexMappingsList = new IndexMappingsList(createAllIndexMappings());
   }

   @Test
   public void getAllIndexMappings() {
      assertEquals(3, indexMappingsList.getAllIndexMappings().size());
   }

   @Test
   public void getSequenceIdForEnumId() {
      assertEquals(1, indexMappingsList.getSequenceIdForEnumId(2));
   }

   @Test
   public void getSequenceIdForEnumIdDoesNotExist() {
      assertEquals(-1, indexMappingsList.getSequenceIdForEnumId(1000));
   }

   @Test
   public void getEnumIdForSequenceId() {
      assertEquals(5, indexMappingsList.getEnumIdForSequenceId(4));
   }

   @Test
   public void getEnumIdForSequenceIdDoesNotExist() {
      assertEquals(-1, indexMappingsList.getEnumIdForSequenceId(1000));
   }

   private List<IndexMapping> createAllIndexMappings() {
      List<IndexMapping> mappings = new ArrayList<>();
      mappings.add(new IndexMapping(1, 2));
      mappings.add(new IndexMapping(3, 8));
      mappings.add(new IndexMapping(4, 5));
      return mappings;
   }
}