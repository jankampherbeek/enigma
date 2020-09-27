/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RosettaTest {

    @Test
    fun setLanguageAndGetText() {
        Rosetta.setLanguage("en")
        assertEquals("Tropical", Rosetta.getText("eclipticprojections.tropical"))
        Rosetta.setLanguage("du")
        assertEquals("Tropisch", Rosetta.getText("eclipticprojections.tropical"))
    }

    @Test
    fun setLanguageAndGetHelpText() {
        Rosetta.setLanguage("en")
        assertEquals("Help for data input for charts", Rosetta.getHelpText("help.chartsinput.title"))
        Rosetta.setLanguage("du")
        assertEquals("Help voor invoer data voor horoscopen", Rosetta.getHelpText("help.chartsinput.title"))
    }

    @Test
    fun setLanguageUnsupportedLang() {
        Rosetta.setLanguage("en")
        assertEquals("Tropical", Rosetta.getText("eclipticprojections.tropical"))
        Rosetta.setLanguage("es")
        assertEquals("Tropical", Rosetta.getText("eclipticprojections.tropical"))
    }

    @Test
    fun getLocale() {
        Rosetta.setLanguage("en")
        assertEquals("EN", Rosetta.getLocale().getCountry())
        Rosetta.setLanguage("du")
        assertEquals("DU", Rosetta.getLocale().getCountry())
    }
}