/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.prog;

import com.radixpro.enigma.be.calc.converters.EclipticEquatorialConversions;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.handlers.astrondata.AstronDataHandlersFactory;
import com.radixpro.enigma.shared.Range;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;

public class TetenburgHandler {

   private final SeFrontend seFrontend;
   private final EclipticEquatorialConversions eclipticEquatorialConversions;

   public TetenburgHandler(final SeFrontend seFrontend, final EclipticEquatorialConversions eclipticEquatorialConversions) {
      this.seFrontend = checkNotNull(seFrontend);
      this.eclipticEquatorialConversions = eclipticEquatorialConversions;
   }

   public double criticalPoint(final double jdRadix, final double jdEvent, final double geoLat, final double radixMc, final double solarSpeed) {
      final double eps = new AstronDataHandlersFactory().getObliquityHandler().calcTrueObliquity(jdRadix);
      final double jdDiff = jdEvent - jdRadix;
      final double nrOfYears = jdDiff / TROPICAL_YEAR;
      final double progMc = new Range(0.0, 360.0).checkValue(radixMc + (nrOfYears * solarSpeed));
      final double[] eclValues = {progMc, 0.0, 1.0};
      double[] eqValues = eclipticEquatorialConversions.convertToEquatorial(eclValues, eps);
      final double progRaMc = eqValues[0];
      return seFrontend.ascFromMc(progRaMc, geoLat, eps);
   }

}
