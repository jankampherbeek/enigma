/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.xchg.domain.SeFlags;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CombinedFlagsTest {

   private ArrayList<SeFlags> allSeFlags;

   @Before
   public void setUp() {
      allSeFlags = new ArrayList<>();
      allSeFlags.add(SeFlags.SWISSEPH);           // 2L
      allSeFlags.add(SeFlags.HELIOCENTRIC);       // 8L
      allSeFlags.add(SeFlags.TOPOCENTRIC);        // 32 * 1024L
   }

   @Test
   public void getCombinedValue() {
      assertEquals(32778L, new CombinedFlags().getCombinedValue(allSeFlags));
   }
}
