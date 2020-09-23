/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.analysis

import com.radixpro.enigma.domain.IMetaData

data class MetaDataForAnalysis(override val name: String,
                               override val configName: String,
                               val baseOrb: Double) : IMetaData