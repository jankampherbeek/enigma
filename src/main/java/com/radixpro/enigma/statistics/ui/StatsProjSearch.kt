/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.Rosetta.getHelpText
import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.ui.creators.*
import com.radixpro.enigma.ui.shared.Help
import com.radixpro.enigma.ui.shared.UiDictionary.*
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class StatsProjSearch(private val statsFacade: StatsFacade) {

    // texts
    private lateinit var txtBtnCancel: String
    private lateinit var txtBtnHelp: String
    private lateinit var txtBtnOk: String
    private lateinit var txtBtnSearch: String
    private lateinit var txtInstruction: String
    private lateinit var txtTitle: String


    // buttons
    private lateinit var btnCancel: Button
    private lateinit var btnHelp: Button
    private lateinit var btnOk: Button
    private lateinit var btnSearch: Button


    private var tfSearchArg: TextField = TextField()
    private lateinit var lvSearchResults: ListView<String>
    private lateinit var stage: Stage
    private lateinit var statsProjNames: List<String>
    var selectedItem: StatsProject? = null
//        private set
    var isSelectionMade = false
//        private set

    fun show() {
        initialize()
        stage = Stage()
        stage.title = getText("ui.stats.projsearch.title")
        stage.width = INPUT_WIDTH
        stage.scene = Scene(createVBox())
        stage.showAndWait()
    }

    private fun initialize() {
        statsProjNames = ArrayList()
        defineTexts()
        defineButtons()
        defineLeafs()
    }

    private fun defineTexts() {
        txtBtnCancel = getText("ui.shared.btn.cancel")
        txtBtnHelp = getText("ui.shared.btn.help")
        txtBtnOk = getText("ui.shared.btn.ok")
        txtBtnSearch = getText("ui.shared.btn.search")
        txtInstruction = getText("ui.stats.projsearch.lblinstruction")
        txtTitle = getText("ui.stats.projsearch.lbltitle")
    }

    private fun defineButtons() {
        btnCancel = ButtonBuilder(txtBtnCancel).setDisabled(false).build()
        btnHelp = ButtonBuilder(txtBtnHelp).setDisabled(false).build()
        btnOk = ButtonBuilder(txtBtnOk).setDisabled(false).build()
        btnSearch = ButtonBuilder(txtBtnSearch).setDisabled(false).build()
        btnCancel.onAction = EventHandler { onCancel() }
        btnHelp.onAction = EventHandler { onHelp() }
        btnOk.onAction = EventHandler { onOk() }
        btnSearch.onAction = EventHandler { onSearch() }
    }


    private fun defineLeafs() {
        tfSearchArg = TextFieldBuilder().setPrefWidth(SMALL_INPUT_WIDTH).setStyleClass("inputDefault").build()
        lvSearchResults = ListView<String>()
        lvSearchResults.prefHeight = 180.0
        lvSearchResults.prefWidth = SMALL_INPUT_WIDTH
        lvSearchResults.styleClass.add("inputDefault")
    }

    private fun createVBox(): VBox {
        return VBoxBuilder().setHeight(HEIGHT + 2 * GAP).setWidth(INPUT_WIDTH + 2 * GAP).setPadding(GAP).setChildren(createGridPane()).build()
    }

    private fun createGridPane(): GridPane {  // col, row
        val gridPane = GridPaneBuilder().setPrefHeight(HEIGHT).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).setStyleSheet(STYLESHEET).build()
        gridPane.add(createPaneTitle(), 0, 0, 2, 1)
        gridPane.add(createLabelInstruction(), 0, 1, 2, 1)
        gridPane.add(tfSearchArg, 0, 2, 1, 1)
        gridPane.add(btnSearch, 1, 2, 1, 1)
        gridPane.add(lvSearchResults, 0, 3, 1, 1)
        gridPane.add(createButtonBar(), 0, 4, 2, 1)
        return gridPane
    }

    private fun createPaneTitle(): Pane {
        val label = LabelBuilder(txtTitle).setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build()
        return PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build()
    }

    private fun createLabelInstruction(): Label {
        return LabelBuilder(txtInstruction).setPrefWidth(INPUT_WIDTH).build()
    }


    private fun createButtonBar(): ButtonBar {
        return ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnOk).build()
    }

    private fun onSearch() {
        lvSearchResults.items.clear()
        val arg: String = tfSearchArg.text
        statsProjNames = if (arg.isEmpty()) statsFacade.readAllProjects() else statsFacade.searchProjects(arg)
        if (!statsProjNames.isNullOrEmpty()) {
            for (projName in statsProjNames) {
                lvSearchResults.items.add(projName)
            }
        }
    }

    private fun onCancel() {
        lvSearchResults.items.clear()
        stage.close()
    }

    private fun onHelp() {
        Help(getHelpText("help.statsprojsearch.title"), getHelpText("help.statsprojsearch.content"))
    }

    private fun onOk() {
        val index = lvSearchResults.selectionModel.selectedIndex
        if (index >= 0) {
            val projName = statsProjNames[index]
            val result = statsFacade.readProject(projName)
            if (result is StatsProject) {
                selectedItem = result
                isSelectionMade = true
            }
        }
        stage.close()
    }

    companion object {
        private const val HEIGHT = 400.0
    }
}