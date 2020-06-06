/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgAnalysisTypeTest {

   final int id = 1;
   final String rbName = "proganalysistype.aspects";
   ProgAnalysisType progAnalysisType;

   @Before
   public void setUp() {
      progAnalysisType = ProgAnalysisType.ASPECTS;
   }

   @Test
   public void getId() {
      assertEquals(id, progAnalysisType.getId());
   }

   @Test
   public void getRbName() {
      assertEquals(rbName, progAnalysisType.getRbName());
   }
}