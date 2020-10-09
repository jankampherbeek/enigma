/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.process

import com.radixpro.enigma.be.persistency.BePersistencyInjector
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector

object BeProcessInjector {

    fun injectControlDataCalendar(): ControlDataCalendar {
        return ControlDataCalendar()
    }

    fun injectControlDataCharts(): ControlDataCharts {
        return ControlDataCharts(BePersistencyInjector.injectJsonReader(), BePersistencyInjector.injectJsonWriter(),
                BePersistencyInjector.injectPropertyDao(), BePersMappersInjector.injectInputDataSetMapper(), injectControlDataCalendar())
    }
}

