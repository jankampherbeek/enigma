/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import com.radixpro.enigma.ui.validators.ValidatedLatitude;
import com.radixpro.enigma.ui.validators.ValidatedLongitude;
import com.radixpro.enigma.xchg.domain.GeographicCoordinate;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creator for location, based on data from input screen.
 */
public class LocationCreator {

   /**
    * Create instance of Location.
    *
    * @param locName name of location. PRE: not null. (Can be empty).
    * @param longDir indicator for east or west. PRE: not null. Value : e, E, o, O, w, W
    * @param latDir  indicator for north or south. PRE: not null. Value : n, N, s, S, z, Z
    * @param valLong validated value for longitude. PRE: nut null, isValiadated.
    * @param valLat  validated value for latitude. PRE: not null, isValidated.
    * @return
    */
   public Location constructLocation(final String locName, final String longDir, final String latDir, final ValidatedLongitude valLong,
                                     final ValidatedLatitude valLat) {
//      checkArgument(null != valLong && valLong.isValidated());
//      checkArgument(null != valLat && valLat.isValidated());
//      checkArgument(null != longDir && (longDir.equalsIgnoreCase("E") || longDir.equalsIgnoreCase("O") ||
//            longDir.equalsIgnoreCase("W")));
      checkArgument(null != latDir && (latDir.equalsIgnoreCase("N") || latDir.equalsIgnoreCase("S") ||
            latDir.equalsIgnoreCase("Z")));
      final String enteredLocation = checkNotNull(locName.trim());
      String longDirTxt = longDir;
      String latDirTxt = latDir;
      if (longDirTxt.equalsIgnoreCase("O")) longDirTxt = "E";
      if (longDirTxt.equalsIgnoreCase("W")) valLong.applyWesternLongitude();
      GeographicCoordinate lonCoord = new GeographicCoordinate(valLong.getDegrees(), valLong.getMinutes(), valLong.getSeconds(), longDir, valLong.getValue());
      if (latDirTxt.equalsIgnoreCase("Z")) latDirTxt = "S";
      if (latDirTxt.equals("S")) valLat.applySouthernLatitude();
      GeographicCoordinate latCoord = new GeographicCoordinate(valLat.getDegrees(), valLat.getMinutes(), valLat.getSeconds(), latDir, valLat.getValue());
      return new Location(lonCoord, latCoord, enteredLocation);
   }

}