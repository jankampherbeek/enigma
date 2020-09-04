/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.references.SeFlags;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Combined result of all flags.
 */
public class CombinedFlags {

   /**
    * Calculate combined value of flags.
    *
    * @param flagList The flags to combine.
    * @return the combined value of te flags.
    */
   public long getCombinedValue(@NotNull final List<SeFlags> flagList) {
      return performCombination(flagList);
   }

   private long performCombination(@NotNull final List<SeFlags> flagList) {
      long result = 0;
      for (SeFlags flag : flagList) {
         result = result | flag.getSeValue();
      }
      return result;
   }


}
