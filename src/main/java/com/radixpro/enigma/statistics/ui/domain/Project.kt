/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui.domain

import com.radixpro.enigma.share.ui.domain.AstronConfigFe

data class StatsProjectFe(val name: String, val description: String, val dataFileName: String, val config: AstronConfigFe)
