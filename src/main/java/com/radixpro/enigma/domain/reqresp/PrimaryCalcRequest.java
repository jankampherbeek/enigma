/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.TimeKeys;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Request for primaryy directions.
 */
public class PrimaryCalcRequest implements IProgCalcRequest {

   private final DateTimeJulian dateTimeProg;
   private final DateTimeJulian dateTimeRadix;
   private final ICalcSettings settings;
   private final TimeKeys timeKey;
   private final Location location;
   private final CalculatedChart calculatedChart;


   /**
    * Constructor defines all elements
    *
    * @param dateTimeProg    time of event.
    * @param dateTimeRadix   time of birth.
    * @param settings        settings for calculations. Also contains the celestial points to calculate.
    * @param timekey         timekey to use for the calculation.
    * @param location        location of birth.
    * @param calculatedChart Positions of houses and planets.
    */
   public PrimaryCalcRequest(@NotNull final DateTimeJulian dateTimeProg, @NotNull final DateTimeJulian dateTimeRadix, @NotNull final ICalcSettings settings,
                             @NotNull final TimeKeys timekey, @NotNull final Location location, @NotNull final CalculatedChart calculatedChart) {
      this.dateTimeProg = dateTimeProg;
      this.dateTimeRadix = dateTimeRadix;
      this.settings = settings;
      this.timeKey = timekey;
      this.location = location;
      this.calculatedChart = calculatedChart;
   }

   @Override
   public DateTimeJulian getDateTime() {
      return dateTimeProg;
   }

   @Override
   public ICalcSettings getSettings() {
      return settings;
   }

   @Override
   public Location getLocation() {
      return location;
   }

   public DateTimeJulian getDateTimeRadix() {
      return dateTimeRadix;
   }

   public TimeKeys getTimeKey() {
      return timeKey;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }
}
