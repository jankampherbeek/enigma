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

enum class Ayanamshas(val id: Int, val seId: Int, val nameForRB: String) {
    NONE(-1, -1, "ayanamshas.none"),
    FAGAN(0, 0, "ayanamshas.fagan"),
    LAHIRI(1, 1, "ayanamshas.lahiri"),
    DELUCE(2, 2, "ayanamshas.deluce"),
    RAMAN(3, 3, "ayanamshas.raman"),
    USHA_SHASHI(4, 4, "ayanamshas.ushashashi"),
    KRISHNAMURTI(5, 5, "ayanamshas.krishnamurti"),
    DJWHAL_KHUL(6, 6, "ayanamshas.djwhalkhul"),
    YUKTESHWAR(7, 7, "ayanamshas.yukteshwar"),
    BHASIN(8, 8, "ayanamshas.bhasin"),
    KUGLER_1(9, 9, "ayanamshas.kugler1"),
    KUGLER_2(10, 10, "ayanamshas.kugler2"),
    KUGLER_3(11, 11, "ayanamshas.kugler3"),
    HUBER(12, 12, "ayanamshas.huber"),
    ETA_PISCIUM(13, 13, "ayanamshas.etapiscium"),
    ALDEBARAN_15TAU(14, 14, "ayanamshas.aldebaran"),
    HIPPARCHUS(15, 15, "ayanamshas.hipparchus"),
    SASSANIAN(16, 16, "ayanamshas.sassanian"),
    GALACT_CTR_0SAG(17, 17, "ayanamshas.galcent0sag"),
    J2000(18, 18, "ayanamshas.j2000"),
    J1900(19, 19, "ayanamshas.j1900"),
    B1950(20, 20, "ayanamshas.b1950"),
    SURYASIDDHANTA(21, 21, "ayanamshas.suryasiddhanta"),
    SURYASIDDHANTA_MEAN_SUN(22, 22, "ayanamshas.suryasiddhantamean"),
    ARYABHATA(23, 23, "ayanamshas.aryabhata"),
    ARYABHATA_MEAN_SUN(24, 24, "ayanamshas.aryabhatamean"),
    SS_REVATI(25, 25, "ayanamshas.ssrevati"),
    SS_CITRA(26, 26, "ayanamshas.sscitra"),
    TRUE_CITRA(27, 27, "ayanamshas.truecitra"),
    TRUE_REVATI(28, 28, "ayanamshas.truerevati"),
    TRUE_PUSHYA(29, 29, "ayanamshas.truepushya"),
    GALACTIC_CTR_BRAND(30, 30, "ayanamshas.galcenterbrand"),
    GALACTIC_EQ_IAU1958(31, 31, "ayanamshas.galcenteriau1958"),
    GALACTIC_EQ(32, 32, "ayanamshas.galequator"),
    GALACTIC_EQ_MID_MULA(33, 33, "ayanamshas.galequatormidmula"),
    SKYDRAM(34, 34, "ayanamshas.skydram"),
    TRUE_MULA(35, 35, "ayanamshas.truemula"),
    DHRUVA(36, 36, "ayanamshas.dhruva"),
    ARYABHATA_522(37, 37, "ayanamshas.aryabhata522"),
    BRITTON(38, 38, "ayanamshas.britton"),
    GALACTIC_CTR_0CAP(39, 39, "ayanamshas.galcenter0cap");

    companion object {
        private val LOG = Logger.getLogger(Ayanamshas::class.java)

        @JvmStatic
        fun getAyanamshaForId(id: Int): Ayanamshas {
            for (ayanamsha in values()) {
                if (ayanamsha.id == id) {
                    return ayanamsha
                }
            }
            LOG.error("Could not find Ayanamsha for id : $id. Returned NONE instead.")
            return NONE
        }

        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val rosetta = Rosetta.getRosetta()
                val ayanamshaNames: MutableList<String> = ArrayList()
                for (ayanamsha in values()) {
                    ayanamshaNames.add(rosetta.getText(ayanamsha.nameForRB))
                }
                return FXCollections.observableArrayList(ayanamshaNames)
            }

        @JvmStatic
        val indexMappings: IndexMappingsList
            get() {
                val idList: MutableList<IndexMapping> = ArrayList()
                for ((runningIndex, ayanamsha) in values().withIndex()) {
                    idList.add(IndexMapping(runningIndex, ayanamsha.id))
                }
                return IndexMappingsList(idList)
            }
    }
}