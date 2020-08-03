/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointPosition;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.NAIBOD_KEY;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Handler for time keys.
 */
public class TimeKeyHandler {

   private static final Logger LOG = Logger.getLogger(TimeKeyHandler.class);
   private final SecundaryDateHandler secundaryDateHandler;
   private final FullPointPositionHandler fpPosHandler;

   /**
    * @param secundaryDateHandler Handler to calcualte a secundarn date. PRE: not null.
    * @param fpPosHandler         Handler for the calculation of a single body. PRE: not null.
    */
   public TimeKeyHandler(final SecundaryDateHandler secundaryDateHandler, final FullPointPositionHandler fpPosHandler) {
      this.secundaryDateHandler = checkNotNull(secundaryDateHandler);
      this.fpPosHandler = checkNotNull(fpPosHandler);
   }

   /**
    * Define the timespan based on the time key.
    *
    * @param birthDateTime Date and time of birth. PRE: not null.
    * @param eventDateTime Date and time of event. PRE: not null.
    * @param timeKey       Time key to use. PRE: not null and not Timekeys.NOT_DEFINED.
    * @param location      Location of birth. PRE: not null.
    * @param settingsRadix Settings for calculation. PRE: not null.
    * @return calculated value for time span.
    */
   public double retrieveTimeSpan(final FullDateTime birthDateTime, final FullDateTime eventDateTime, final TimeKeys timeKey, final Location location,
                                  final ICalcSettings settingsRadix) throws UnknownTimeKeyException {
      checkNotNull(birthDateTime);
      checkNotNull(eventDateTime);
      checkArgument(null != timeKey && TimeKeys.NOT_DEFINED != timeKey);
      checkNotNull(location);
      checkNotNull(settingsRadix);
      return calculateTimeSpan(birthDateTime, eventDateTime, timeKey, location, settingsRadix);
   }

   private double calculateTimeSpan(final FullDateTime birthDateTime, final FullDateTime eventDataTime, final TimeKeys timeKey, final Location location,
                                    final ICalcSettings settingsRadix) throws UnknownTimeKeyException {
      double timeSpan;
      if (timeKey == TimeKeys.NAIBOD) timeSpan = timeSpanForNaibod(birthDateTime, eventDataTime);
      else if (timeKey == TimeKeys.REAL_SECUNDARY_SUN) timeSpan = timeSpanForRealSolarArc(birthDateTime, eventDataTime, location, settingsRadix);
      else {
         LOG.error("Using non-supported time key : " + timeKey.name());
         throw new UnknownTimeKeyException("Tried to retrieve a calculation for a non existing timekey : " + timeKey.name());
      }
      return timeSpan;
   }

   private double timeSpanForNaibod(final FullDateTime birthDateTime, final FullDateTime eventDataTime) {
      final double nrOfDays = eventDataTime.getJdUt() - birthDateTime.getJdUt();
      return (nrOfDays / TROPICAL_YEAR) * NAIBOD_KEY;
   }

   private double timeSpanForRealSolarArc(final FullDateTime birthDateTime, final FullDateTime eventDataTime, final Location location,
                                          final ICalcSettings settingsRadix) {
      final FullDateTime secDateTime = secundaryDateHandler.calcSecundaryDate(birthDateTime, eventDataTime);
      final ProgSettings pSetRx = (ProgSettings) settingsRadix;
      final ObserverPositions obsPos = pSetRx.isTopocentric() ? ObserverPositions.TOPOCENTRIC : ObserverPositions.GEOCENTRIC;
      final EclipticProjections eclProj = pSetRx.isSidereal() ? EclipticProjections.SIDEREAL : EclipticProjections.TROPICAL;
      final Ayanamshas ayanamsha = pSetRx.getAyamsha();
      final FullPointPosition fppSunRadix = fpPosHandler.definePosition(CelestialObjects.SUN, birthDateTime.getJdUt(), obsPos, eclProj, ayanamsha, location);
      final FullPointPosition fppSunSec = fpPosHandler.definePosition(CelestialObjects.SUN, secDateTime.getJdUt(), obsPos, eclProj, ayanamsha, location);
      final Range range = new Range(0.0, 360.0);
      return range.checkValue(fppSunSec.getLongitude() - fppSunRadix.getLongitude());
   }

}
