/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.shared.exceptions.InputDataException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Converter for csv data to Location.
 */
public class Csv2LocationConverter {

   /**
    * Creates a location from csv text.
    *
    * @param lonTxt input for longitude: degrees and mnutes separated with E or W for the direction. E.g. 6E54.
    * @param latTxt input for latitude: degrees and mnutes separated with N or S for the direction. E.g. 52N13.
    * @return The location with the correct coordinates.
    * @throws InputDataException if any error is encoutered during the conversion.
    */
   public Location convert(@NotNull final String lonTxt, @NotNull final String latTxt) throws InputDataException {
      return new Location(createLatitude(latTxt), createLongitude(lonTxt));
   }

   private double createLongitude(final String lonTxt) throws InputDataException {
      String dir;
      String[] parts = lonTxt.toUpperCase().split("E");
      if (2 == parts.length) dir = "E";
      else {
         parts = lonTxt.toUpperCase().split("W");
         if (2 == parts.length) {
            dir = "W";
         } else throw new InputDataException("Error when defining longitude for : " + lonTxt);
      }
      return createCoordinate(parts, dir);
   }

   private double createLatitude(final String latTxt) throws InputDataException {
      String dir;
      String[] parts = latTxt.toUpperCase().split("N");
      if (2 == parts.length) dir = "N";
      else {
         parts = latTxt.toUpperCase().split("S");
         if (2 == parts.length) {
            dir = "S";
         } else throw new InputDataException("Error when defining latitude for : " + latTxt);
      }
      return createCoordinate(parts, dir);
   }


   private double createCoordinate(final String[] parts, final String direction) throws InputDataException {
      int degrees;
      int minutes;
      int dirCorrection = "NOEnoe".contains(direction) ? 1 : -1;
      try {
         degrees = Integer.parseInt(parts[0]);
         minutes = Integer.parseInt(parts[1]);
      } catch (NumberFormatException nfe) {
         throw new InputDataException("NumberFormatException when parsing GeographicCoordinate for : " + Arrays.toString(parts));
      }
      return (degrees + (minutes / 60.0)) * dirCorrection;
   }

}
