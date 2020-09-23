/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.google.common.base.Preconditions
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.*
import com.radixpro.enigma.shared.Range
import com.radixpro.enigma.shared.common.EnigmaDictionary.NAIBOD_KEY
import com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException
import com.radixpro.enigma.xchg.api.settings.ICalcSettings
import com.radixpro.enigma.xchg.api.settings.ProgSettings
import org.apache.log4j.Logger

class TimeKeyHandler(private val secundaryDateHandler: SecundaryDateHandler,
                     private val fpPosHandler: FullPointPositionHandler) {

    @Throws(UnknownTimeKeyException::class)
    fun retrieveTimeSpan(birthDateTime: DateTimeJulian,
                         eventDateTime: DateTimeJulian,
                         timeKey: TimeKeys,
                         location: Location,
                         settingsRadix: ICalcSettings): Double {
        Preconditions.checkArgument(TimeKeys.NOT_DEFINED !== timeKey)
        return calculateTimeSpan(birthDateTime, eventDateTime, timeKey, location, settingsRadix)
    }

    @Throws(UnknownTimeKeyException::class)
    private fun calculateTimeSpan(birthDateTime: DateTimeJulian, eventDateTime: DateTimeJulian, timeKey: TimeKeys, location: Location,
                                  settingsRadix: ICalcSettings): Double {
        return if (timeKey === TimeKeys.NAIBOD) timeSpanForNaibod(birthDateTime, eventDateTime)
        else if (timeKey === TimeKeys.REAL_SECUNDARY_SUN) timeSpanForRealSolarArc(birthDateTime, eventDateTime, location, settingsRadix)
        else {
            LOG.error("Using non-supported time key : " + timeKey.name)
            throw UnknownTimeKeyException("Tried to retrieve a calculation for a non existing timekey : " + timeKey.name)
        }
    }

    private fun timeSpanForNaibod(birthDateTime: DateTimeJulian, eventDataTime: DateTimeJulian): Double {
        val nrOfDays = eventDataTime.jd - birthDateTime.jd
        return nrOfDays / TROPICAL_YEAR * NAIBOD_KEY
    }

    private fun timeSpanForRealSolarArc(birthDateTime: DateTimeJulian, eventDataTime: DateTimeJulian, location: Location,
                                        settingsRadix: ICalcSettings): Double {
        val (jd) = secundaryDateHandler.calcSecundaryDate(birthDateTime, eventDataTime)
        val pSetRx = settingsRadix as ProgSettings
        val obsPos = if (pSetRx.isTopocentric) ObserverPositions.TOPOCENTRIC else ObserverPositions.GEOCENTRIC
        val eclProj = if (pSetRx.isSidereal) EclipticProjections.SIDEREAL else EclipticProjections.TROPICAL
        val ayanamsha: Ayanamshas = pSetRx.ayanamsha
        val fppSunRadix = fpPosHandler.definePosition(CelestialObjects.SUN, birthDateTime.jd, obsPos, eclProj, ayanamsha, location)
        val fppSunSec = fpPosHandler.definePosition(CelestialObjects.SUN, jd, obsPos, eclProj, ayanamsha, location)
        val range = Range(0.0, 360.0)
        return range.checkValue(fppSunSec.longitude - fppSunRadix.longitude)
    }

    companion object {
        private val LOG = Logger.getLogger(TimeKeyHandler::class.java)
    }
}