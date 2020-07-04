/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.core;

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.SeFlags;
import org.apache.log4j.Logger;
import swisseph.SweDate;
import swisseph.SwissEph;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.SE_LOCATION;

/**
 * Simple wrapper to access the Java port to the SE by Thomas Mack.
 * Implemented as a singleton to prevent multiple instantiations.
 * Always use this wrapper to access the Java port.
 */
public class SeFrontend {

   private static final Logger LOG = Logger.getLogger(SeFrontend.class);
   private static final String PATH = SE_LOCATION;
   private static SeFrontend instance = null;
   private final SwissEph swissEph = new SwissEph(PATH);

   private SeFrontend() {
      // prevent direct instantiation.
   }

   /**
    * Retrieve instance of singleton for SeFrontend.
    *
    * @return unique instance
    */
   public static SeFrontend getFrontend() {
      if (instance == null) {
         instance = new SeFrontend();
         LOG.info("Created singleton instance for SeFrontend.");
      }
      return instance;
   }

   /**
    * Calculate ecliptical or equatorial positions for a body
    *
    * @param jdUt  Julian Day based on Ephemeris Time
    * @param id    indicates the body
    * @param flags combined settings for the SE
    * @return calculated positions. Array contains for ecliptical positions: from 0..5: Longitude, latitude, distance in AU, speed long, speed lat, speed dist,
    * and for equatorial positions from 0..5: right ascension, declination, distance in AU, speed RA, speed decl, speed dist.
    */
   public SePositionResultCelObjects getPositionsForCelBody(final double jdUt, final int id, final int flags, final Location location) {
      double[] allPositions = new double[6];
      var errorMsg = new StringBuffer();  // StringBuilder not possible because Java Port to the SE uses a StringBuffer.

      if (flags == (flags | SeFlags.TOPOCENTRIC.getSeValue())) {
         swissEph.swe_set_topo(location.getGeoLong(), location.getGeoLat(), 0.0);
      }
      swissEph.swe_calc_ut(jdUt, id, flags, allPositions, errorMsg);
      return new SePositionResultCelObjects(allPositions, errorMsg.toString());
   }

   /**
    * Calculate epsilon
    *
    * @param jdUt  Julian Day based on Ephemeris Time
    * @param id    indicates the body
    * @param flags combined settings for the SE
    * @return calculated positions
    */
   public SePositionResultCelObjects getPositionsForEpsilon(final double jdUt, final int id, final int flags) {
      double[] allPositions = new double[6];
      var errorMsg = new StringBuffer();  // StringBuilder not possible because Java Port to the SE uses a StringBuffer.
      swissEph.swe_calc_ut(jdUt, id, flags, allPositions, errorMsg);
      return new SePositionResultCelObjects(allPositions, errorMsg.toString());
   }


   /**
    * Calculate horizontal positions for a body
    *
    * @param jdUt     Julian day based on ephemeris time
    * @param eclCoord ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance
    * @param location geographic latitude and longitude
    * @param flags    combined settings for the SE
    * @return calculated positions
    */
   public double[] getHorizontalPosition(final double jdUt, final double[] eclCoord, final Location location,
                                         final int flags) {
      checkNotNull(eclCoord);
      checkNotNull(location);
      double[] geoPos = {location.getGeoLong(), location.getGeoLat(), 0.0};
      double[] eclPos = {eclCoord[0], eclCoord[1], eclCoord[2]};
      double atPress = 0.0;
      double atTemp = 0.0;
      var azAlt = new double[3];
      swissEph.swe_azalt(jdUt, flags, geoPos, atPress, atTemp, eclPos, azAlt);
      return azAlt;
   }


   /**
    * Calculate positions for houses
    *
    * @param jdUt      Julian Day based on Universal Time
    * @param flags     combined settings for the SE
    * @param location  geographic latitude and longitude
    * @param system    the housesystem to use
    * @param nrOfCusps number of cusps for the current housesystem
    * @return calculated positions
    */
   public SePositionResultHouses getPositionsForHouses(final double jdUt, final int flags, final Location location,
                                                       final int system, final int nrOfCusps) {
      checkNotNull(location);
      double[] cusps = new double[nrOfCusps + 1];
      double[] ascMc = new double[10];
      double[] tempCusps = new double[100];
      swissEph.swe_houses(jdUt, flags, location.getGeoLat(), location.getGeoLong(), system, tempCusps, ascMc);
      if (nrOfCusps >= 0) System.arraycopy(tempCusps, 1, cusps, 1, nrOfCusps);
      return new SePositionResultHouses(ascMc, cusps);
   }


   /**
    * Checks if a date is valid.
    *
    * @param year      The year of the date.
    * @param month     The month of the date.
    * @param day       The day of the date.
    * @param gregorian True if gregorian calendar, otherwise false.
    * @return True if date is valid, otherwise false.
    */
   public boolean isValidDate(final int year, final int month, final int day, final boolean gregorian) {
      SweDate sweDate1 = new SweDate(year, month, day, 0.0, gregorian);
      double calculatedJulDay = sweDate1.getJulDay();
      SweDate sweDate2 = new SweDate(calculatedJulDay, gregorian);
      return (sweDate1.getYear() == sweDate2.getYear() && sweDate1.getMonth() == sweDate2.getMonth() &&
            sweDate1.getDay() == sweDate2.getDay());
   }

}

