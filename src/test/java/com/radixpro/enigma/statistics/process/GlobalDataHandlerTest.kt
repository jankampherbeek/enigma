/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.persistency.GlobalDataDao
import com.radixpro.enigma.statistics.persistency.InputDataReader
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GlobalDataHandlerTest {

    private val daoMock: GlobalDataDao = mockk()
    private val propHandlerMock: PropertyHandler = mockk()
    private val expectedDescriptions = createDataFileDescriptions()
    private val inputDataSetMock: InputDataSet = mockk()
    private val inputDataReaderMock: InputDataReader = mockk()
    private val jsonWriterMock: JsonWriter = mockk()
    private val propertyList = createPropertyList()
    private val pathConstructorMock: StatsPathConstructor = mockk()
    private lateinit var handler: GlobalDataHandler

    @BeforeEach
    fun init() {
        every { daoMock.readDataFileList() } returns expectedDescriptions
        every { daoMock.readData(any()) } returns inputDataSetMock
        every { propHandlerMock.retrieve(any()) } returns propertyList
        handler = GlobalDataHandler(daoMock, inputDataReaderMock, jsonWriterMock, pathConstructorMock)
    }

    @Test
    fun `Handler should return correct list of DataDefinitions`() {
        handler.readDataFileDesciptions() shouldBe expectedDescriptions
    }

    @Test
    fun `Handler should return correct InputDataSet for given filename`() {
        handler.readDataCharts("some file") shouldBe inputDataSetMock
    }


    private fun createDataFileDescriptions(): List<DataFileDescription> {
        val descriptions = mutableListOf<DataFileDescription>()
        descriptions.add(DataFileDescription("name1", "descr1", 100))
        descriptions.add(DataFileDescription("name2", "descr2", 200))
        return descriptions.toList()
    }

    private fun createPropertyList(): List<Property> {
        val props = mutableListOf<Property>()
        props.add(Property("key1", "value1"))
        return props.toList()
    }
}

