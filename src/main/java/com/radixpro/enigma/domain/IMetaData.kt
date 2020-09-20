/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain

/**
 * Interface for meta data: data bout a chart, progression etc.
 */
interface IMetaData {
    val name: String?
    val configName: String?
}