/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.converters;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.SE_ECL2HOR;

/**
 * Converts from ecliptical coordinates to horizontal coordinates.
 */
public class EclipticHorizontalConverter {

   private final SeFrontend seFrontend;

   /**
    * Initialize via factory.
    *
    * @param seFrontend instance of SeFrontend. PRE: not null.
    * @see CalcConvertersFactory
    */
   public EclipticHorizontalConverter(final SeFrontend seFrontend) {
      this.seFrontend = checkNotNull(seFrontend);
   }

   /**
    * Convert to horizontal position.
    *
    * @param jdUt     Julian day number for UT.
    * @param eclCoord ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance. PRE: not null.
    * @param location location. PRE: not null.
    * @return array with azimuth and altitude (in that sequence).
    */
   public double[] convert(final double jdUt, final double[] eclCoord, final Location location) {
      // TODO Release 2020.2 Check handling of sidereal positions
      return seFrontend.getHorizontalPosition(jdUt, checkNotNull(eclCoord), checkNotNull(location), SE_ECL2HOR);
   }

}
