/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.astronomy.ui.domain


enum class CelObjects(val rbKey: String) {
    SUN("celobject.sun"),
    MOON("celobject.moon"),
    MERCURY("celobject.mercury"),
    VENUS("celobject.venus"),
    MARS("celobject.mars"),
    JUPITER("celobject.jupiter"),
    SATURN("celobject.saturn"),
    URANUS("celobject.uranus"),
    NEPTUNE("celobject.neptune"),
    PLUTO("celobject.pluto"),
    CHEIRON("celobject.cheiron"),
    MEAN_NODE("celobject.meannode"),
    TRUE_NODE("celobject.truenode")
}


enum class MundanePoints(val rbKey: String) {
    MC("mundpoint.mc"),
    ASC("mundpoint.asc"),
    VERTEX("mundpoint.vertex"),
    EASTPOINT("mundpoint.eastpoint"),
    CUSP01("mundpoint.c01"),
    CUSP02("mundpoint.c02"),
    CUSP03("mundpoint.c03"),
    CUSP04("mundpoint.c04"),
    CUSP05("mundpoint.c05"),
    CUSP06("mundpoint.c06"),
    CUSP07("mundpoint.c07"),
    CUSP08("mundpoint.c08"),
    CUSP09("mundpoint.c09"),
    CUSP10("mundpoint.c10"),
    CUSP11("mundpoint.c11"),
    CUSP12("mundpoint.c12")
}
