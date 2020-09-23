/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.references.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CalculationSettings {

   private final List<CelestialObjects> celBodies;
   private final HouseSystems houseSystem;
   private final Ayanamshas ayanamsha;
   private final boolean sidereal;
   private final boolean topocentric;


   public CalculationSettings(@NotNull final List<CelestialObjects> celBodies, @NotNull final HouseSystems houseSystem, @NotNull final Ayanamshas ayanamsha,
                              final boolean sidereal, final boolean topocentric) {
      this.celBodies = celBodies;
      this.houseSystem = houseSystem;
      this.ayanamsha = ayanamsha;
      this.sidereal = sidereal;
      this.topocentric = topocentric;
   }

   public CalculationSettings(final Configuration configuration) {
      this.celBodies = constructCelObjects(configuration);
      this.houseSystem = configuration.getAstronConfiguration().getHouseSystem();
      this.ayanamsha = configuration.getAstronConfiguration().getAyanamsha();
      this.sidereal = configuration.getAstronConfiguration().getEclipticProjection() == EclipticProjections.SIDEREAL;
      this.topocentric = configuration.getAstronConfiguration().getObserverPosition() == ObserverPositions.TOPOCENTRIC;
   }

   public List<CelestialObjects> getCelBodies() {
      return celBodies;
   }

   public HouseSystems getHouseSystem() {
      return houseSystem;
   }

   public Ayanamshas getAyanamsha() {
      return ayanamsha;
   }

   public boolean isSidereal() {
      return sidereal;
   }

   public boolean isTopocentric() {
      return topocentric;
   }

   private List<CelestialObjects> constructCelObjects(final Configuration configuration) {
      List<CelestialObjects> celObjects = new ArrayList<>();
      List<ConfiguredCelObject> confCelObjects = configuration.getAstronConfiguration().getCelObjects();
      for (ConfiguredCelObject confCelObject : confCelObjects) {
         celObjects.add(confCelObject.getCelObject());
      }
      return celObjects;
   }

}
