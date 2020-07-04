/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class MetaDataForAnalysisTest {

   private final String name = "MyName";
   private final String configName = "MyConfig";
   private final double baseOrb = 9.5;
   private MetaDataForAnalysis meta;

   @Before
   public void setUp() {
      meta = new MetaDataForAnalysis(name, configName, baseOrb);
   }

   @Test
   public void getName() {
      assertEquals(name, meta.getName());
   }

   @Test
   public void getConfigName() {
      assertEquals(configName, meta.getConfigName());
   }

   @Test
   public void getBaseOrb() {
      assertEquals(baseOrb, meta.getBaseOrb(), DELTA_8_POS);
   }
}