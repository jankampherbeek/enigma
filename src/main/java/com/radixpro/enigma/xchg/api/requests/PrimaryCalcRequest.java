/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.TimeKeys;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Request for primaryy directions.
 */
public class PrimaryCalcRequest implements IProgCalcRequest {

   private final FullDateTime dateTimeProg;
   private final FullDateTime dateTimeRadix;
   private final ICalcSettings settings;
   private final TimeKeys timeKey;
   private final Location location;
   private final CalculatedChart calculatedChart;


   /**
    * Constructor defines all elements
    *
    * @param dateTimeProg    time of event. PRE: not null.
    * @param dateTimeRadix   time of birth. PRE: not null.
    * @param settings        settings for calculations. Also contains the celestial points to calculate. PRE: not null.
    * @param timekey         timekey to use for the calculation. PRE: not null.
    * @param location        location of birth. PRE: not null.
    * @param calculatedChart Positions of houses and planets. PRE: not null.
    */
   public PrimaryCalcRequest(final FullDateTime dateTimeProg, final FullDateTime dateTimeRadix, final ICalcSettings settings, final TimeKeys timekey,
                             final Location location, final CalculatedChart calculatedChart) {
      this.dateTimeProg = checkNotNull(dateTimeProg);
      this.dateTimeRadix = checkNotNull(dateTimeRadix);
      this.settings = checkNotNull(settings);
      this.timeKey = timekey;
      this.location = checkNotNull(location);
      this.calculatedChart = checkNotNull(calculatedChart);
   }

   @Override
   public FullDateTime getDateTime() {
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

   public FullDateTime getDateTimeRadix() {
      return dateTimeRadix;
   }

   public TimeKeys getTimeKey() {
      return timeKey;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }
}
