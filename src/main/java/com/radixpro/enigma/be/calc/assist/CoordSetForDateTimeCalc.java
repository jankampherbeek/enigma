/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.calculatedobjects.CoordinateSet;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates a CoordinateSet, a set of two coordinates.
 */
public class CoordSetForDateTimeCalc {

   private static final Logger LOG = Logger.getLogger(CoordSetForDateTimeCalc.class);
   private final SeFrontend seFrontend;

   /**
    * Use ProgCalcFactory to instantiate this class.
    *
    * @param seFrontend Instance of seFrontEnd.
    * @see com.radixpro.enigma.be.calc.factories.ProgCalcFactory
    */
   public CoordSetForDateTimeCalc(final SeFrontend seFrontend) {
      this.seFrontend = seFrontend;
   }

   /**
    * Calculate two coordinates (longitude/latitude or right ascension/decliantion.).
    *
    * @param jdUt     Julian day number for UT.
    * @param id       id for the celestial body as used by the SE.
    * @param flags    combination of flags that incidcates the preferences. The flags also define the choice for eclitpical or equatorial.
    * @param location Location. Is only used for parallax. PRE: not null.
    * @return the calculated coordinates.
    */
   public CoordinateSet calcSet(final double jdUt, final int id, final int flags, final Location location) {
      checkNotNull(location);
      SePositionResultCelObjects resultSet = seFrontend.getPositionsForCelBody(jdUt, id, flags, location);
      if (!resultSet.getErrorMsg().isEmpty()) {
         LOG.error("Error while calculating position for point : " + id + " using flags : " + flags + " and jdUt : " + jdUt);
         new FailFastHandler().terminate("Received error msg from SE : " + resultSet.getErrorMsg());
      }
      return new CoordinateSet(resultSet.getAllPositions()[0], resultSet.getAllPositions()[1]);
   }


}
