/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.jetbrains.annotations.NotNull;

/**
 * A celestial object or point that can be analyzed statistically.
 */
public class Player {
   final IChartPoints point;
   final double position;

   public Player(@NotNull final IChartPoints point,
                 final double position) {
      this.point = point;
      this.position = position;
   }

   public IChartPoints getPoint() {
      return point;
   }

   public double getPosition() {
      return position;
   }
}
