/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.xchg.domain.SeFlags;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Combined result of all flags.
 */
public class CombinedFlags {

   private final long combinedValue;

   /**
    * Constructor expects a listof all the flags that will be combined.
    *
    * @param flagList The flags.
    */
   public CombinedFlags(final List<SeFlags> flagList) {
      combinedValue = performCombination(checkNotNull(flagList));
   }

   private long performCombination(final List<SeFlags> flagList) {
      List<SeFlags> selFlags = checkNotNull(flagList);
      long result = 0;
      for (SeFlags flag : selFlags) {
         result = result | flag.getSeValue();
      }
      return result;
   }

   public long getCombinedValue() {
      return this.combinedValue;
   }
}
