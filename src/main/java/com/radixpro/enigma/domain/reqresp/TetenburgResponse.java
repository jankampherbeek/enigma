/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Response for the calculation of a critical point according to the theory by Ton Tetenburg.
 */
public class TetenburgResponse {

   private final double longAsc;
   private final String resultMsg;

   /**
    * Constructor defines all properties.
    *
    * @param longAsc   ecliptical longitude of calculated Ascendant. PRE: 0.0 <= longAsc < 360.0
    * @param resultMsg If no error occurred: "OK", otherwise text that explains the error.
    */
   public TetenburgResponse(final double longAsc, @NotNull final String resultMsg) {
      checkArgument(0.0 <= longAsc && longAsc < 360.0);
      this.longAsc = longAsc;
      this.resultMsg = resultMsg;
   }

   public double getLongAsc() {
      return longAsc;
   }

   public String getResultMsg() {
      return resultMsg;
   }
}
