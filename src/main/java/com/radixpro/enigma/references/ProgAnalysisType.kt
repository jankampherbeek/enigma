/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

/**
 * possible analyses for progressive positions.
 */
enum class ProgAnalysisType(val id: Int, val rbName: String) {
    EMPTY(0, ""), ASPECTS(1, "proganalysistype.aspects"), MIDPOINTS(2, "proganalysistype.midpoints");
}