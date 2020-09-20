/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.shared.exceptions.UnknownIdException

enum class AspectOrbStructures(val id: Int) {
    ASPECT(1), CELBODY(2), COMBINED(3), CATEGORY(4);

    companion object {
        @JvmStatic
        @Throws(UnknownIdException::class)
        fun getStructureForId(id: Int): AspectOrbStructures {
            for (structure in values()) {
                if (structure.id == id) {
                    return structure
                }
            }
            throw UnknownIdException("Could not find AspectOrbStructure for id : $id")
        }
    }
}