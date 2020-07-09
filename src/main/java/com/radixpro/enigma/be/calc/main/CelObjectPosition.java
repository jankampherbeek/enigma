/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.main;

import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.be.calc.converters.CalcConvertersFactory;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.domain.CelObjectSinglePosition;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.SeFlags;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Coordinates for a celestial object: ecliptical, equatorial and horizontal.
 */
public class CelObjectPosition {

   private final CelestialObjects celestialBody;
   private CelObjectSinglePosition eclipticalPosition;
   private CelObjectSinglePosition equatorialPosition;
   private double[] azAlt;
   private int eclipticalFlags;
   private int equatorialFlags;


   /**
    * Constructor defines all members.
    *
    * @param seFrontend Instantiation (singleton) of SeFrontend.
    * @param jdUt       Julian Day number in UT.
    * @param celBody    The celestial body.
    * @param location   The location.
    * @param flagList   A list with fglags to be used.
    */
   public CelObjectPosition(final SeFrontend seFrontend, final double jdUt, final CelestialObjects celBody,
                            final Location location, final List<SeFlags> flagList) {
      this.celestialBody = checkNotNull(celBody);
      final List<SeFlags> localFlagList = new ArrayList<>(checkNotNull(flagList)); // need copy to prevent changing the content of flaglist.
      defineFlags(localFlagList);
      calculate(checkNotNull(seFrontend), jdUt, checkNotNull(location));
   }

   private void defineFlags(final List<SeFlags> localFlagList) {
      eclipticalFlags = (int) new CombinedFlags().getCombinedValue(localFlagList);
      localFlagList.add(SeFlags.EQUATORIAL);
      equatorialFlags = (int) new CombinedFlags().getCombinedValue(localFlagList);
   }

   private void calculate(final SeFrontend seFrontend, final double jdUt, final Location location) {
      eclipticalPosition = new CelObjectSinglePosition(seFrontend, jdUt, celestialBody, eclipticalFlags, location);
      equatorialPosition = new CelObjectSinglePosition(seFrontend, jdUt, celestialBody, equatorialFlags, location);
      double[] eclipticalCoordinates = new double[]{eclipticalPosition.getMainPosition(),
            eclipticalPosition.getDeviationPosition(), eclipticalPosition.getDistancePosition()};
      azAlt = new CalcConvertersFactory().getEclipticHorizontalConverter().convert(jdUt, eclipticalCoordinates, location);
   }

   public CelObjectSinglePosition getEclipticalPosition() {
      return this.eclipticalPosition;
   }

   public CelObjectSinglePosition getEquatorialPosition() {
      return this.equatorialPosition;
   }

   public double[] getEclipticHorizontalConverter() {
      return azAlt;
   }

   public CelestialObjects getCelestialBody() {
      return this.celestialBody;
   }
}
