/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

class StatsFacade(private val projApi: StatsProjApi) {

    fun saveProject(projectFe: StatsProjectFe): ApiResult {
        return projApi.save(projectFe)
    }


}