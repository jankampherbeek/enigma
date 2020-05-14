/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.be.astron.assist.CombinedFlags;
import com.radixpro.enigma.be.astron.core.SeFrontend;
import com.radixpro.enigma.be.astron.main.CelObjectPosition;
import com.radixpro.enigma.be.astron.main.MundaneValues;
import com.radixpro.enigma.be.astron.main.Obliquity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A 'full' chart with information on all positions.
 */
public class FullChart {

   private final FullDateTime fullDateTime;
   private final Location location;
   private final CalculationSettings settings;
   private final SeFrontend seFrontend;
   private final double jdUt;
   private MundaneValues mundaneValues;
   private List<CelObjectPosition> bodies;
   private double obliquity;
   private long flagsValue;
   private List<SeFlags> allFlags;

   public FullChart(final FullDateTime fullDateTime, final Location location, final CalculationSettings settings) {
      this.fullDateTime = checkNotNull(fullDateTime);
      this.location = checkNotNull(location);
      this.settings = checkNotNull(settings);
      this.jdUt = fullDateTime.getJdUt();
      seFrontend = SeFrontend.getFrontend();
      calculateFlags();
      calculateHouses();
      calculateBodies();
      calculateObliquity();
   }

   private void calculateFlags() {
      allFlags = new ArrayList<>();
      allFlags.add(SeFlags.SWISSEPH);
      allFlags.add(SeFlags.SPEED);
      if (settings.isSidereal()) {
         allFlags.add(SeFlags.SIDEREAL);
      }
      if (settings.isTopocentric()) {
         allFlags.add(SeFlags.TOPOCENTRIC);
      }
      final CombinedFlags combinedFlags = new CombinedFlags(allFlags);
      flagsValue = combinedFlags.getCombinedValue();
   }

   private void calculateHouses() {
      mundaneValues = new MundaneValues(seFrontend, jdUt, (int) flagsValue, location, settings.getHouseSystem());
   }

   private void calculateBodies() {
      bodies = new ArrayList<>();
      for (int i = 0; i < settings.getCelBodies().size(); i++) {
         bodies.add(new CelObjectPosition(seFrontend, jdUt, settings.getCelBodies().get(i), location, allFlags));
      }
   }

   private void calculateObliquity() {
      obliquity = new Obliquity(seFrontend, jdUt).getTrueObliquity();
   }

   public double getJulianDayForUt() {
      return jdUt;
   }

   public FullDateTime getFullDateTime() {
      return fullDateTime;
   }

   public Location getLocation() {
      return location;
   }

   public CalculationSettings getSettings() {
      return settings;
   }

   public MundaneValues getMundaneValues() {
      return mundaneValues;
   }

   public List<CelObjectPosition> getBodies() {
      return bodies;
   }

   public double getObliquity() {
      return obliquity;
   }
}
