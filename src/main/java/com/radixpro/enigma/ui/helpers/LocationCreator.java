/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.domain.LocationOld;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a Location value object
 */
public class LocationCreator {

   /**
    * Create a location
    *
    * @param latDeg degrees in latitude. PRE: -90 < latDeg < 90.
    * @param latMin minutes in latitude. PRE: 0 <= latMin < 60.
    * @param latSec seconds in latitude. PRE: 0 <= latSec < 60.
    * @param latDir direction for latitude. PRE: "N", "S", or "Z" (uppercase/lowercase allowed).
    * @param lonDeg degrees in longitude. PRE: -180 < lonDeg <= 180
    * @param lonMin minutes in longitude. PRE: 0 <= lonMin < 60. If lonDeg == 180, lonMin == 0.
    * @param lonSec seconds in longitude. PRE: 0 <= lonSec < 60. If lonDeg == 180, lonSec == 0.
    * @param lonDir direction for longitude. PRE: "E", "O", or "W" (uppercase/lowercase allowed).
    * @return instance of Location.
    */
   public Location createLocation(final int latDeg, final int latMin, final int latSec, @NotNull final String latDir,
                                  final int lonDeg, final int lonMin, final int lonSec, @NotNull final String lonDir) {
      checkArgument(latDir.equalsIgnoreCase("N") || latDir.equalsIgnoreCase("S") || latDir.equalsIgnoreCase("Z"));
      checkArgument(lonDir.equalsIgnoreCase("E") || lonDir.equalsIgnoreCase("O") || lonDir.equalsIgnoreCase("W"));
      checkArgument(90 > latDeg && -90 < latDeg);
      checkArgument(0 <= latMin && latMin < 60);
      checkArgument(0 <= latSec && latSec < 60);
      checkArgument(180 >= lonDeg && -180 < lonDeg);
      checkArgument(lonDeg != 180 || (lonMin == 0 && lonSec == 0));
      checkArgument(0 <= lonMin && lonMin < 60);
      checkArgument(0 <= lonSec && lonSec < 60);

      double geoLat = createCoordinate(latDeg, latMin, latSec) * checkDirection(latDir);
      double geoLon = createCoordinate(lonDeg, lonMin, lonSec) * checkDirection(lonDir);
      return new Location(geoLat, geoLon);
   }


   private double createCoordinate(int lonDeg, int lonMin, int lonSec) {
      return lonDeg + lonMin / 60.0 + lonSec / 3600.0;
   }

   private int checkDirection(final String dir) {
      if ("WZSwzs".contains(dir)) return -1;
      return 1;
   }

   /**
    * Temporary solution to support the move from the old Location object (now LocationOld) to the simplified version.
    */
   public Location tempConvertOld2New(final LocationOld locOld) {
      return new Location(locOld.getGeoLat(), locOld.getGeoLong());
   }


}
