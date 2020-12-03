/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.astronomy.di

import com.radixpro.enigma.astronomy.proces.GeneralPositionHandler

object AstronomyInjector {

    fun injectGeneralPositionHandler(): GeneralPositionHandler {
        return GeneralPositionHandler()
    }
}

