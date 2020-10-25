/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api.xchg

import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

data class ApiResult(val success: Boolean, val msg: String)

data class ProjectApiResult(val apiResult: ApiResult, val project: StatsProject)

data class ProjectApiResultFe(val apiResult: ApiResult, val projectFe: StatsProjectFe)

