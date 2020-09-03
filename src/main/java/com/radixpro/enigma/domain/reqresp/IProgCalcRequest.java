/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;

/**
 * Interface for progressive calculation requests.
 */
public interface IProgCalcRequest {

   DateTimeJulian getDateTime();

   ICalcSettings getSettings();

   Location getLocation();

}
