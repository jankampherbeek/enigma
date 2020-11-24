/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.apache.log4j.Logger
import java.util.*

// TODO create separate indication for seId for NO_HOUSES, and also for other housesystems that are not supported by the SE.
enum class HouseSystems(val id: Int,
                        val seId: String,
                        val nameForRB: String,
                        val nrOfCusps: Int,
                        val isCounterClockwise: Boolean,
                        val isQuadrantSystem: Boolean,
                        val isCuspIsStart: Boolean) {
    NO_HOUSES(1, "W", "houses.none", 0, false, false, false),
    WHOLESIGN(2, "W", "houses.wholesign", 12, true, false, true),
    EQUAL(3, "A", "houses.equalasc", 12, true, false, true),
    EQUAL_MC(4, "D", "houses.equalmc", 12, true, false, true),
    VEHLOW(5, "V", "houses.vehlow", 12, true, false, false),
    PLACIDUS(6, "P", "houses.placidus", 12, true, true, true),
    KOCH(7, "K", "houses.koch", 12, true, true, true),
    PORPHYRI(8, "O", "houses.porphyri", 12, true, true, true),
    REGIOMONTANUS(9, "R", "houses.regiomontanus", 12, true, true, true),
    CAMPANUS(10, "C", "houses.campanus", 12, true, true, true),
    ALCABITIUS(11, "B", "houses.alcabitius", 12, true, true, true),
    TOPOCENTRIC(12, "T", "houses.topocentric", 12, true, true, true),
    KRUSINSKI(13, "U", "houses.krusinsky", 12, true, true, true),
    APC(14, "Y", "houses.apc", 12, true, true, true),
    MORIN(15, "M", "houses.morin", 12, true, false, true),
    AXIAL(16, "X", "houses.axial", 12, true, false, true),
    HORIZON(17, "H", "houses.azimuth", 12, true, false, true),
    CARTER(18, "F", "houses.carter", 12, true, false, true),
    EQUAL_ARIES(19, "N", "houses.equalaries", 12, true, false, true),
    GAUQUELIN(20, "G", "houses.gauquelin", 36, true, false, true),
    SUNSHINE(21, "i", "houses.sunshine", 12, true, false, false),
    SUNSHINE_TREINDL(22, "I", "houses.sunshinetreindl", 12, true, false, true);

    companion object {
        private val LOG = Logger.getLogger(HouseSystems::class.java)

        @JvmStatic
        fun getSystemForId(id: Int): HouseSystems {
            for (system in values()) {
                if (system.id == id) {
                    return system
                }
            }
            LOG.error("Could not find HouseSystem for id : $id. Returned WHOLESIGN instead.")
            return WHOLESIGN
        }

        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val houseSystemNames: MutableList<String> = ArrayList()
                for (houseSystem in values()) {
                    houseSystemNames.add(Rosetta.getText(houseSystem.nameForRB))
                }
                return FXCollections.observableArrayList(houseSystemNames)
            }

        @JvmStatic
        val indexMappings: IndexMappingsList
            get() {
                val idList: MutableList<IndexMapping> = ArrayList()
                for ((runningIndex, houseSystem) in values().withIndex()) {
                    idList.add(IndexMapping(runningIndex, houseSystem.id))
                }
                return IndexMappingsList(idList)
            }
    }
}