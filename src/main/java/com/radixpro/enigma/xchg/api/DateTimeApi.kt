/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.calc.SeFrontend

class DateTimeApi() {

    fun checkDate(year: Int,
                  month: Int,
                  day: Int,
                  gregorian: Boolean): Boolean {
        return SeFrontend.isValidDate(year, month, day, gregorian)
    }
}