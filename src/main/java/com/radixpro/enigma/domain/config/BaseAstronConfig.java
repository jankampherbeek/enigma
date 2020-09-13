/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.HouseSystems;
import com.radixpro.enigma.references.ObserverPositions;
import org.jetbrains.annotations.NotNull;

/**
 * Essentials of the astronomical part of a configuration.
 */
public class BaseAstronConfig {

   private final HouseSystems houseSystem;
   private final Ayanamshas ayanamsha;
   private final EclipticProjections eclipticProjection;
   private final ObserverPositions observerPosition;

   public BaseAstronConfig(@NotNull final HouseSystems houseSystem,
                           @NotNull final Ayanamshas ayanamsha,
                           @NotNull final EclipticProjections eclipticProjection,
                           @NotNull final ObserverPositions observerPosition) {
      this.houseSystem = houseSystem;
      this.ayanamsha = ayanamsha;
      this.eclipticProjection = eclipticProjection;
      this.observerPosition = observerPosition;
   }

   public HouseSystems getHouseSystem() {
      return houseSystem;
   }

   public Ayanamshas getAyanamsha() {
      return ayanamsha;
   }

   public EclipticProjections getEclipticProjection() {
      return eclipticProjection;
   }

   public ObserverPositions getObserverPosition() {
      return observerPosition;
   }
}
