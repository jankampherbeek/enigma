/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.MidpointsHandler
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.IPosition

class MidpointsApi(private val handler: MidpointsHandler) {

    fun analyseMidpoints(celObjects: List<IPosition>, mundaneValues: List<IPosition>): List<IAnalyzedPair> {
        return handler.retrieveMidpoints(celObjects, mundaneValues)
    }
}