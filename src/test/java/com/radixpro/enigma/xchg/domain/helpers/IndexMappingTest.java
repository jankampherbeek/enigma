/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.helpers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexMappingTest {

   private final int sequenceId = 5;
   private final int enumId = 32;
   private IndexMapping indexMapping;

   @Before
   public void setUp() throws Exception {
      indexMapping = new IndexMapping(sequenceId, enumId);
   }

   @Test
   public void getSequenceId() {
      assertEquals(sequenceId, indexMapping.getSequenceId());
   }

   @Test
   public void getEnumId() {
      assertEquals(enumId, indexMapping.getEnumId());
   }
}