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

import java.io.Serializable;
import java.util.List;

/**
 * Astronomical parts of the configuration.
 */
public class AstronConfiguration implements Serializable {

   private HouseSystems houseSystem;
   private Ayanamshas ayanamsha;
   private EclipticProjections eclipticProjection;
   private ObserverPositions observerPosition;
   private List<ConfiguredCelObject> celObjects;

   /**
    * Constructor defines all memebers.
    *
    * @param houseSystem        Selected house system.
    * @param ayanamsha          Selected ayanamasha, Ayanamshas.NONE for tropical zodiac.
    * @param eclipticProjection Tropical or sidereal zodiac.
    * @param observerPosition   Positionof the observer.
    * @param celObjects         The supported celestial objects.
    */
   public AstronConfiguration(@NotNull final HouseSystems houseSystem,
                              @NotNull final Ayanamshas ayanamsha,
                              @NotNull final EclipticProjections eclipticProjection,
                              @NotNull final ObserverPositions observerPosition,
                              @NotNull final List<ConfiguredCelObject> celObjects) {
      this.houseSystem = houseSystem;
      this.ayanamsha = ayanamsha;
      this.eclipticProjection = eclipticProjection;
      this.observerPosition = observerPosition;
      this.celObjects = celObjects;
   }

   public HouseSystems getHouseSystem() {
      return houseSystem;
   }

   public void setHouseSystem(HouseSystems houseSystem) {
      this.houseSystem = houseSystem;
   }

   public Ayanamshas getAyanamsha() {
      return ayanamsha;
   }

   public void setAyanamsha(Ayanamshas ayanamsha) {
      this.ayanamsha = ayanamsha;
   }

   public EclipticProjections getEclipticProjection() {
      return eclipticProjection;
   }

   public void setEclipticProjection(EclipticProjections eclipticProjection) {
      this.eclipticProjection = eclipticProjection;
   }

   public ObserverPositions getObserverPosition() {
      return observerPosition;
   }

   public void setObserverPosition(ObserverPositions observerPosition) {
      this.observerPosition = observerPosition;
   }

   public List<ConfiguredCelObject> getCelObjects() {
      return celObjects;
   }

   public void setCelObjects(List<ConfiguredCelObject> celObjects) {
      this.celObjects = celObjects;
   }
}
