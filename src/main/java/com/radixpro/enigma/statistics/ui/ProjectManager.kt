/*
 * Jan Kampherbeek, (c) 2020, 2021.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.share.ui.domain.TableViewString
import com.radixpro.enigma.ui.creators.*
import com.radixpro.enigma.ui.shared.Help
import com.radixpro.enigma.ui.shared.UiDictionary
import com.radixpro.enigma.ui.shared.UiDictionary.GAP
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class ProjectManager(
    private val facade: StatsFacade,
    private val scenarioNew: ScenarioNew,
    private val scenarioDetails: ScenarioDetails,
    private val processingResult: ProcessingResult
) {
    // texts
    private lateinit var lblName: Label
    private lateinit var projName: String
    private lateinit var txtBtnClose: String
    private lateinit var txtBtnCtrl: String
    private lateinit var txtBtnDetails: String
    private lateinit var txtBtnHelp: String
    private lateinit var txtBtnNew: String
    private lateinit var txtBtnRun: String
    private lateinit var txtSubTitleScenarios: String
    private lateinit var txtTitle: String

    //buttons
    private lateinit var btnClose: Button
    private lateinit var btnCtrl: Button
    private lateinit var btnDetails: Button
    private lateinit var btnHelp: Button
    private lateinit var btnNew: Button
    private lateinit var btnRun: Button


    private lateinit var stage: Stage
    private lateinit var scenarios: List<String>
    private lateinit var tvProj: TableView<TableViewString>

    fun show(projName: String) {
        this.projName = projName
        initialize()
        stage = Stage()
        stage.title = txtTitle
        stage.scene = Scene(createVBox())
        stage.showAndWait()
    }

    private fun initialize() {
        defineTexts()
        defineButtons()
        defineTableView()
    }

    private fun defineTexts() {
        txtBtnClose = getText("ui.shared.btn.exit")
        txtBtnCtrl = getText("ui.stats.projman.ctrl")
        txtBtnDetails = getText("ui.shared.btn.details")
        txtBtnHelp = getText("ui.shared.btn.help")
        txtBtnNew = getText("ui.shared.btn.new")
        txtBtnRun = getText("ui.stats.projman.run")
        txtSubTitleScenarios = getText("ui.stats.projman.subtitlescen")
        txtTitle = getText("ui.stats.projman.title")
        lblName = LabelBuilder(projName).build()
    }

    private fun defineButtons() {
        btnClose = ButtonBuilder(txtBtnClose).setDisabled(false).setFocusTraversable(true).build()
        btnCtrl = ButtonBuilder(txtBtnCtrl).setPrefWidth(180.0).setDisabled(false).setFocusTraversable(true).build()
        btnDetails = ButtonBuilder(txtBtnDetails).setDisabled(false).setFocusTraversable(true).build()
        btnHelp = ButtonBuilder(txtBtnHelp).setDisabled(false).setFocusTraversable(true).build()
        btnNew = ButtonBuilder(txtBtnNew).setDisabled(false).setFocusTraversable(true).build()
        btnRun = ButtonBuilder(txtBtnRun).setDisabled(true).setFocusTraversable(false).build()
        btnClose.onAction = EventHandler { onClose() }
        btnCtrl.onAction = EventHandler { onCtrl() }
        btnDetails.onAction = EventHandler { onDetails() }
        btnHelp.onAction = EventHandler { onHelp() }
        btnNew.onAction = EventHandler { onNewScenario() }
        btnRun.onAction = EventHandler { onRun() }
    }

    private fun createVBox(): VBox {
        return VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            createPaneTitle(),
            createPaneName(),
            createPaneSubTitleScenarios(),
            tvProj,
            PaneBuilder().setHeight(12.0).build(),
            createPaneBtnBarScenarios(),
            PaneBuilder().setHeight(12.0).build(),
            createPaneButtonBarGeneral()
        ).build()
    }

    private fun createPaneTitle(): Pane {
        val label = LabelBuilder(txtTitle).setPrefWidth(UiDictionary.INPUT_WIDTH).setPrefHeight(UiDictionary.TITLE_HEIGHT).setStyleClass("titletext").build()
        return PaneBuilder().setWidth(UiDictionary.INPUT_WIDTH).setHeight(UiDictionary.TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build()
    }

    private fun createPaneSubTitleScenarios(): Pane {
        val label = LabelBuilder(txtSubTitleScenarios).setPrefWidth(WIDTH).setStyleClass("subtitletext").build()
        return PaneBuilder().setWidth(WIDTH).setHeight(UiDictionary.SUBTITLE_HEIGHT).setStyleClass("subtitlepane").setChildren(label).build()
    }

    private fun createPaneName(): Pane {
        return PaneBuilder().setWidth(WIDTH).setHeight(25.0).setChildren(lblName).build()
    }

    private fun defineTableView() {
        val colName: TableColumn<TableViewString, String> = TableColumn(getText("ui.general.name"))
        colName.prefWidth = 550.0
        colName.cellValueFactory = PropertyValueFactory("text")
        tvProj = TableView<TableViewString>()
        tvProj.prefHeight = 200.0
        tvProj.prefWidth = WIDTH
        tvProj.columns.add(colName)
        tvProj.selectionModel.selectionMode = SelectionMode.SINGLE
        tvProj.items.addAll(scenarioNames())
    }

    private fun scenarioNames(): List<TableViewString> {
        scenarios = facade.readScenarioNames(projName)
        val scenariosForTv: MutableList<TableViewString> = ArrayList()
        for (scenario in scenarios) {
            scenariosForTv.add(TableViewString(scenario))
        }
        return scenariosForTv
    }

    private fun createPaneBtnBarScenarios(): Pane {
        val buttonBar = ButtonBarBuilder().setButtons(btnDetails, btnRun, btnNew, btnCtrl).build()
        return PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build()
    }

    private fun createPaneButtonBarGeneral(): Pane {
        val buttonBar = ButtonBarBuilder().setButtons(btnHelp, btnClose).build()
        return PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build()
    }

    private fun onDetails() {
        val index = tvProj.selectionModel.selectedIndex
        val selectedScenario = scenarios[index]
        scenarioDetails.show(selectedScenario, projName)
        stage.close()
        show(projName)
    }

    private fun onNewScenario() {
        scenarioNew.show(projName)
    }

    private fun onRun() {
        val index = tvProj.selectionModel.selectedIndex
        val selectedScenario = scenarios[index]
        processingResult.show(selectedScenario, projName, "TEST")
    }

    private fun onCtrl() {
        val index = tvProj.selectionModel.selectedIndex
        val selectedScenario = scenarios[index]
        processingResult.show(selectedScenario, projName, "CONTROL")
    }

    private fun onClose() {
        stage.close()
    }

    private fun onHelp() {
        Help(Rosetta.getHelpText("help.statsprojmanager.title"), Rosetta.getHelpText("help.statsprojmanager.content"))
    }

    companion object {
        private const val HEIGHT = 400.0
        private const val WIDTH = 600.0
    }
}