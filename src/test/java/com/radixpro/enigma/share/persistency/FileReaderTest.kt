/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.persistency

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FileReaderTest {

    private val fileReader = FileReader()
    val fullPath = "c:\\dev\\projectE\\TestProjDir\\Proj\\Test 1\\"            // FIXME read location from properties

    @Test
    fun `Reading files with a prefix gives list with expected filenames`() {
        fileReader.readFileItems(fullPath, "scen_").shouldBe(createFullList())
    }

    @Test
    fun `Reading files with both a prefix and a postfix gives list with expected filenames`() {
        fileReader.readFileItems(fullPath, "scen_", ".json").shouldBe(createFullList())
    }

    @Test
    fun `Reading files with only a postfix gives list with correct size`() {
        fileReader.readFileItems(path = fullPath, postFix = ".json") shouldHaveSize 5
    }

    @Test
    fun `Reading files with no postfix nor prefix gives list with correct size`() {
        fileReader.readFileItems(fullPath) shouldHaveSize 5
    }

    @Test
    fun `Reading files with wrong path gives empty list`() {
        fileReader.readFileItems("does not exist") shouldHaveSize 0
    }

    @Test
    fun `Reading files with wrong postfix gives empty list`() {
        fileReader.readFileItems(fullPath, preFix = "does not exist") shouldHaveSize 0
    }

    private fun createFullList(): List<String> {
        return listOf("scen_XXX.json", "scen_YYY.json")
    }
}