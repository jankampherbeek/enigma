/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.main.CelObjectPosition;
import com.radixpro.enigma.be.calc.main.MundaneValues;
import com.radixpro.enigma.xchg.domain.CalculationSettings;
import com.radixpro.enigma.xchg.domain.FullChart;
import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * API for a calculated full chart. Contains all positions and additional astronomical info.
 */
public class CalculatedFullChart {

   private final FullChart fullchart;

   public CalculatedFullChart(final FullDateTime fullDateTime, final Location location,
                              final CalculationSettings settings) {
      checkNotNull(fullDateTime);
      checkNotNull(location);
      checkNotNull(settings);
      fullchart = new FullChart(fullDateTime, location, settings);
   }

   public List<CelObjectPosition> getBodies() {
      return fullchart.getBodies();
   }

   public MundaneValues getHouseValues() {
      return fullchart.getMundaneValues();
   }

   public CalculationSettings getSettings() {
      return fullchart.getSettings();
   }

   public FullDateTime getDateTime() {
      return fullchart.getFullDateTime();
   }

   public Location getLocation() {
      return fullchart.getLocation();
   }

   public double getObliquity() {
      return fullchart.getObliquity();
   }

   public FullChart getFullChart() {
      return fullchart;
   }
}
