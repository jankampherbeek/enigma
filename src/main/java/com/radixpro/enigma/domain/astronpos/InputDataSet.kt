/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.domain.input.ChartInputData

/**
 * Input dataset to be saved in Json format.
 */
data class InputDataSet(val name: String,
                        val description: String,
                        val origFileName: String,
                        val dateTime: String,
                        val inputData: List<ChartInputData>)