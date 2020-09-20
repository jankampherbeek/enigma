/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.AspectsHandler
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.config.AspectConfiguration

class AspectsApi(private val handler: AspectsHandler) {

    fun analyzeAspects(celObjects: List<IPosition>,
                       mundaneValues: List<IPosition>,
                       config: AspectConfiguration): List<IAnalyzedPair> {
        return handler.retrieveAspects(celObjects, mundaneValues, config)
    }
}