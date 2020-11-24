/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.statistics.core.ScenarioBe


class ScenPersister() {

    fun saveScenario(scenarioBe: ScenarioBe, pathFileName: String) {
        JsonWriter().write2File(pathFileName, scenarioBe, true)
    }

}

