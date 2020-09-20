/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.xchg.domain.IChartPoints

enum class MundanePoints(private val id: Int, private val rbKey: String) : IChartPoints {
    MC(1, "ui.shared.mc"),
    ASC(2, "ui.shared.asc"),
    VERTEX(3, "ui.shared.vertex"),
    ANTI_VERTEX(4, "ui.shared.antivertex"),
    EAST_POINT(5, "ui.shared.eastpoint"),
    CUSP(6, "ui.shared.cusps");

    private val pointType: ChartPointTypes = ChartPointTypes.MUNDANE_POINTS

    override fun getItemForId(id: Int): IChartPoints {
        for (point in values()) {
            if (point.getId() == id) {
                return point
            }
        }
        throw IllegalArgumentException("Could not find MundanePoint for id : $id")
    }

    override fun getPointType(): ChartPointTypes {
        return pointType
    }

    override fun getId(): Int {
        return id
    }

    override fun getRbKey(): String {
        return rbKey
    }

}