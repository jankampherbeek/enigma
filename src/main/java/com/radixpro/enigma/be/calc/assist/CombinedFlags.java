/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.references.SeFlags;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Combined result of all flags.
 */
public class CombinedFlags {

   /**
    * Calculate combined value of flags.
    *
    * @param flagList The flags to combine. PRE: not null.
    * @return the combined value of te flags.
    */
   public long getCombinedValue(final List<SeFlags> flagList) {
      return performCombination(checkNotNull(flagList));
   }

   private long performCombination(final List<SeFlags> flagList) {
      List<SeFlags> selFlags = checkNotNull(flagList);
      long result = 0;
      for (SeFlags flag : selFlags) {
         result = result | flag.getSeValue();
      }
      return result;
   }


}
