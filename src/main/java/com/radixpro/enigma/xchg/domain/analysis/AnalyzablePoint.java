/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import static com.google.common.base.Preconditions.checkNotNull;

public class AnalyzablePoint {

   private final int indexForType;
   private final AnalyzablePointTypes type;
   private final double position;

   public AnalyzablePoint(final int indexForType, final AnalyzablePointTypes type, final double position) {
      this.indexForType = indexForType;
      this.type = checkNotNull(type);
      this.position = position;
   }

   public int getIndexForType() {
      return indexForType;
   }

   public AnalyzablePointTypes getType() {
      return type;
   }

   public double getPosition() {
      return position;
   }
}
