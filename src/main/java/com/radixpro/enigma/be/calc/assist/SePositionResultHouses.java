/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;


import org.jetbrains.annotations.NotNull;

/**
 * DTO for the result of a SE calculation for houses, holds array with positions and a possible error Message.
 */
public class SePositionResultHouses {

   private final double[] ascMc;
   private final double[] cusps;

   /**
    * Constructor defines all elements.
    *
    * @param ascMc Array with from 0 to 7: Ascendant, MC, ARMC, Vertex, equatorial ascendant,
    *              co-ascendant (Walter Koch), co-ascendant (Michael Munkasey), polar ascendant (M. Munkasey)
    * @param cusps Array with teh cusps starting at position 1. Position 0 is not used.
    */
   public SePositionResultHouses(@NotNull final double[] ascMc, @NotNull final double[] cusps) {
      this.ascMc = ascMc;
      this.cusps = cusps;
   }

   public double[] getAscMc() {
      return this.ascMc;
   }

   public double[] getCusps() {
      return this.cusps;
   }
}
