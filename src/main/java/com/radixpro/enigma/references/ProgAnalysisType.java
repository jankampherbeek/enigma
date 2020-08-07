/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

/**
 * possible analyses for progressive positions.
 */
public enum ProgAnalysisType {
   EMPTY(0, ""),
   ASPECTS(1, "proganalysistype.aspects"),
   MIDPOINTS(2, "proganalysistype.midpoints");

   private final int id;
   private final String rbName;

   ProgAnalysisType(final int id, final String rbName) {
      this.id = id;
      this.rbName = rbName;
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }

}
