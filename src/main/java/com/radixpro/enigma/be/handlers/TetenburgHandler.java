/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.shared.Range;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Handler for the calculation of the Critical Point as defined by Ton Tetenburg.
 */
public class TetenburgHandler {

   private final SeFrontend seFrontend;
   private final ObliquityHandler oblHandler;

   public TetenburgHandler(@NotNull final SeFrontend seFrontend,
                           @NotNull final ObliquityHandler oblHandler) {
      this.seFrontend = seFrontend;
      this.oblHandler = oblHandler;
   }

   public double criticalPoint(final double jdRadix, final double jdEvent, final double geoLat, final double radixMc, final double solarSpeed) {
      final double eps = oblHandler.calcTrueObliquity(jdRadix);
      final double jdDiff = jdEvent - jdRadix;
      final double nrOfYears = jdDiff / TROPICAL_YEAR;
      final double progMc = new Range(0.0, 360.0).checkValue(radixMc + (nrOfYears * solarSpeed));
      final double[] eclValues = {progMc, 0.0, 1.0};
      double[] eqValues = CoordinateConversions.eclipticToEquatorial(eclValues, eps);
      final double progRaMc = eqValues[0];
      return seFrontend.ascFromMc(progRaMc, geoLat, eps);
   }

}
