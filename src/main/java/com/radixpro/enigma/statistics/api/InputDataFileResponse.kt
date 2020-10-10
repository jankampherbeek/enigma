/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

/**
 * Response after reading a datafile and converting it to Json.
 */
data class InputDataFileResponse(val resultMsg: String, val errorLines: List<String>, val isSuccess: Boolean)