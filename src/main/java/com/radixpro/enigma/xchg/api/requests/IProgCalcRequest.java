/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;

/**
 * Interface for progressive calculation requests.
 */
public interface IProgCalcRequest {

   FullDateTime getDateTime();

   ICalcSettings getSettings();

   Location getLocation();

}
