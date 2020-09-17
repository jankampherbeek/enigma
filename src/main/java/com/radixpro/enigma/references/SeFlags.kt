/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import swisseph.SweConst

/**
 * Different parameters that define a calculation.
 * The long are values will be or'ed to define the flag value for the SE.
 */
enum class SeFlags(val seValue: Long) {
    SWISSEPH(SweConst.SEFLG_SWIEPH.toLong()),       // 2L
    HELIOCENTRIC(SweConst.SEFLG_HELCTR.toLong()),   // 8L
    SPEED(SweConst.SEFLG_SPEED.toLong()),           // 256L
    EQUATORIAL(SweConst.SEFLG_EQUATORIAL.toLong()), // 2048L
    TOPOCENTRIC(SweConst.SEFLG_TOPOCTR.toLong()),   // 32*1024L
    SIDEREAL(SweConst.SEFLG_SIDEREAL.toLong()),     // 64*1024L
    HORIZONTAL(SweConst.SE_ECL2HOR.toLong());       // 0 int! Not to be combined with other flags

}