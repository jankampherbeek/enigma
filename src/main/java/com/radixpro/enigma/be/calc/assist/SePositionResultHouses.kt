/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.be.calc.assist

/**
 * DTO for the result of a SE calculation for houses, holds array with positions and a possible error Message.
 *
 * @param ascMc Array with from 0 to 7: Ascendant, MC, ARMC, Vertex, equatorial ascendant, co-ascendant (Walter Koch), co-ascendant (Michael Munkasey),
 * polar ascendant (M. Munkasey)
 * @param cusps Array with teh cusps starting at position 1. Position 0 is not used.
 */
class SePositionResultHouses(val ascMc: DoubleArray, val cusps: DoubleArray)