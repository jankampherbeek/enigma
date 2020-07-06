/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.factories.EquatorialPositionForHousesFactory;
import com.radixpro.enigma.be.calc.main.Obliquity;
import com.radixpro.enigma.shared.Range;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;

public class TetenburgHandler {

   private final SeFrontend seFrontend;

   public TetenburgHandler(final SeFrontend seFrontend) {
      this.seFrontend = checkNotNull(seFrontend);
   }

   public double criticalPoint(final double jdRadix, final double jdEvent, final double geoLat, final double radixMc, final double solarSpeed) {
      final double eps = new Obliquity(seFrontend, jdRadix).getTrueObliquity();
      final double jdDiff = jdEvent - jdRadix;
      final double nrOfYears = jdDiff / TROPICAL_YEAR;
      final double progMc = new Range(0.0, 360.0).checkValue(radixMc + (nrOfYears * solarSpeed));
      final double progRaMc = new EquatorialPositionForHousesFactory().createInstance(progMc, jdRadix).getRightAscension();
      return seFrontend.ascFromMc(progRaMc, geoLat, eps);
   }

}
