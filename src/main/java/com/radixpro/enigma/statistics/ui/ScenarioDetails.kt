/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.statistics.ui.helpers.ScenDetailsText
import com.radixpro.enigma.ui.creators.*
import com.radixpro.enigma.ui.shared.Help
import com.radixpro.enigma.ui.shared.UiDictionary
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class ScenarioDetails(
    private val facade: StatsFacade,
    private val scenDetailsText: ScenDetailsText
) {
    // texts
    private lateinit var projName: String
    private lateinit var scenName: String
    private lateinit var txtBtnClose: String
    private lateinit var txtBtnDelete: String
    private lateinit var txtBtnHelp: String
    private lateinit var txtTitle: String

    // buttons
    private lateinit var btnClose: Button
    private lateinit var btnDelete: Button
    private lateinit var btnHelp: Button

    private lateinit var stage: Stage


    fun show(scenName: String, projName: String) {
        this.projName = projName
        this.scenName = scenName
        stage = Stage()
        initialize()
        stage.title = txtTitle
        stage.scene = Scene(createVBox())
        stage.showAndWait()
    }

    private fun initialize() {
        defineTexts()
        defineButtons()
    }

    private fun defineTexts() {
        txtBtnClose = getText("ui.shared.btn.exit")
        txtBtnDelete = getText("ui.shared.btn.delete")
        txtBtnHelp = getText("ui.shared.btn.help")
        txtTitle = getText("ui.stats.scendetail.title")
    }

    private fun defineButtons() {
        btnClose = ButtonBuilder(txtBtnClose).setDisabled(false).setFocusTraversable(true).build()
        btnDelete = ButtonBuilder(txtBtnDelete).setDisabled(false).setFocusTraversable(true).build()
        btnHelp = ButtonBuilder(txtBtnHelp).setDisabled(false).setFocusTraversable(true).build()
        btnClose.onAction = EventHandler { onClose() }
        btnDelete.onAction = EventHandler { onDelete() }
        btnHelp.onAction = EventHandler { onHelp() }
    }

    private fun createVBox(): VBox {
        return VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(UiDictionary.GAP).setChildren(
            createPaneTitle(),
            createPaneDetails(),
            createPaneButtonBar()
        ).build()
    }

    private fun createPaneTitle(): Pane {
        val label = LabelBuilder(txtTitle).setPrefWidth(WIDTH).setPrefHeight(UiDictionary.TITLE_HEIGHT).setStyleClass("titletext").build()
        return PaneBuilder().setWidth(UiDictionary.INPUT_WIDTH).setHeight(UiDictionary.TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build()
    }

    private fun createPaneDetails(): Pane {
        val scenario = facade.readScenario(scenName, projName)
        val detailText = scenDetailsText.createText(scenario)
        val lblDetails = LabelBuilderObs("").setText(detailText).setPrefWidth(WIDTH).build()
        lblDetails.isWrapText = true
        return PaneBuilder().setHeight(HEIGHT_DETAILS).setWidth(WIDTH).setChildren(lblDetails).build()
    }

    private fun createPaneButtonBar(): Pane {
        val buttonBar = ButtonBarBuilder().setButtons(btnDelete, btnHelp, btnClose).build()
        return PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build()
    }

    private fun onDelete() {
        facade.deleteScenario(scenName, projName)
    }

    private fun onHelp() {
        Help(Rosetta.getHelpText("help.statsscendetails.title"), Rosetta.getHelpText("help.statsscendetails.content"))
    }

    private fun onClose() {
        stage.close()
    }

    companion object {
        private const val HEIGHT = 500.0
        private const val HEIGHT_DETAILS = 300.0
        private const val WIDTH = 600.0
    }
}