/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.Rosetta
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.io.Serializable
import java.util.*

/**
 * Enum with ratings for the reliability of the time for a chart, as define buy Louise Rodden.
 * Adds a code 'ZZ' as fallback if the rating is unknown.
 * Is persistable as part of a saved chart.
 */
enum class Ratings(val id: Int, val nameForRB: String) : Serializable {
    ZZ(0, "ratings.zz"),
    AA(1, "ratings.aa"),
    A(2, "ratings.a"),
    B(3, "ratings.b"),
    C(4, "ratings.c"),
    DD(5, "ratings.dd"),
    X(6, "ratings.x"),
    XX(7, "ratings.xx");

    companion object {

        @JvmStatic
        fun getRatingForId(id: Int): Ratings {
            for (rating in values()) {
                if (rating.id == id) {
                    return rating
                }
            }
            return ZZ
        }

        @JvmStatic
        fun ratingForName(ratingName: String): Ratings {
            val rosetta = Rosetta.getRosetta()
            for (rating in values()) {
                if (rosetta.getText(rating.nameForRB) == ratingName) {
                    return rating
                }
            }
            return ZZ
        }

        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val rosetta = Rosetta.getRosetta()
                val ratingNames: MutableList<String> = ArrayList()
                for (rating in values()) {
                    ratingNames.add(rosetta.getText(rating.nameForRB))
                }
                return FXCollections.observableArrayList(ratingNames)
            }
    }
}