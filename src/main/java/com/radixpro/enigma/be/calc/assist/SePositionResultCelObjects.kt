/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.be.calc.assist

/**
 * Container for the result of a SE calculation for celestial bodies.
 *
 * @param allPositions Array with the following values from 0..5 : main position, deviation, distance,
 * speed of main position, speed of deviation, speed of distance.
 * @param errorMsg     Error message or "OK".
 */
class SePositionResultCelObjects(val allPositions: DoubleArray, val errorMsg: String)