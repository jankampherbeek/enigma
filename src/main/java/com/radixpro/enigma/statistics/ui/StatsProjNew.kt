/*
 * Jan Kampherbeek, (c) 2021.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.Rosetta.getHelpText
import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.share.ui.domain.AstronConfigFe
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe
import com.radixpro.enigma.ui.creators.*
import com.radixpro.enigma.ui.screens.InputScreen
import com.radixpro.enigma.ui.screens.blocks.BaseConfigInputBlock
import com.radixpro.enigma.ui.shared.Help
import com.radixpro.enigma.ui.shared.UiDictionary.*
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.TableView.TableViewSelectionModel
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import java.util.*

/**
 * Input screen for new projects for statistics.
 */
class StatsProjNew(
    private val configBlock: BaseConfigInputBlock,
    private val dataSearch: StatsDataSearch,
    private val facade: StatsFacade
) : InputScreen() {

    // texts
    private lateinit var lblDescription: Label
    private lateinit var lblName: Label
    private lateinit var lblSubTitleDataFiles: Label
    private lateinit var txtBtnCancel: String
    private lateinit var txtBtnHelp: String
    private lateinit var txtBtnRemove: String
    private lateinit var txtBtnSave: String
    private lateinit var txtBtnSearch: String

    // buttons
    private lateinit var btnHelp: Button
    private lateinit var btnCancel: Button
    private lateinit var btnRemove: Button
    private lateinit var btnSave: Button
    private lateinit var btnSearch: Button
    private lateinit var txtTitle: String


    private lateinit var tvDataFiles: TableView<DataFileDescription>
    private lateinit var tfName: TextField
    private lateinit var tfDescr: TextField
    private lateinit var dataFileDescription: DataFileDescription
    private lateinit var colName: TableColumn<DataFileDescription, String>
    private lateinit var colDescr: TableColumn<DataFileDescription, String>
    private lateinit var selectedDataFiles: ObservableList<DataFileDescription>

    fun show() {
        initialize()
        stage.title = txtTitle
        stage.scene = Scene(createVBox())
        stage.showAndWait()
    }

    override fun checkStatus() {
        btnSave.isDisable = null == tfName.text || tfDescr.text.isBlank() || null == tfDescr.text || tfDescr.text.isBlank() || tvDataFiles.items.isEmpty()
        btnRemove.isDisable = tvDataFiles.selectionModel.selectedIndex == -1
    }

    private fun initialize() {
        defineTexts()
        defineButtons()
        defineTextFields()
        defineTableView()
    }

    private fun defineTexts() {
        lblSubTitleDataFiles = LabelBuilder(getText("ui.stats.datafiles.subtitle")).setPrefWidth(INPUT_WIDTH)
            .setPrefHeight(SUBTITLE_HEIGHT).setStyleClass("subtitletext").build()
        lblName = LabelBuilder(getText("ui.stats.newproj.lblname")).build()
        lblDescription = LabelBuilder(getText("ui.stats.newproj.lbldescription")).build()
        txtBtnCancel = getText("ui.shared.btn.cancel")
        txtBtnHelp = getText("ui.shared.btn.help")
        txtBtnRemove = getText("ui.stats.datafiles.btnremove")
        txtBtnSave = getText("ui.shared.btn.save")
        txtBtnSearch = getText("ui.shared.btn.search")
        txtTitle = getText("ui.stats.newproj.title")
    }

    private fun defineButtons() {
        btnCancel = ButtonBuilder(txtBtnCancel).setDisabled(false).build()
        btnHelp = ButtonBuilder(txtBtnHelp).setDisabled(false).build()
        btnRemove = ButtonBuilder(txtBtnRemove).setDisabled(true).build()
        btnSave = ButtonBuilder(txtBtnSave).setDisabled(true).build()
        btnSearch = ButtonBuilder(txtBtnSearch).setDisabled(false).build()
        btnCancel.onAction = EventHandler { stage.close() }
        btnHelp.onAction = EventHandler { onHelp() }
        btnRemove.onAction = EventHandler { onRemove() }
        btnSave.onAction = EventHandler { onSave() }
        btnSearch.onAction = EventHandler { onSearch() }
    }

    private fun defineTextFields() {
        tfName = TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build()
        tfName.textProperty().addListener { _: ObservableValue<out String>, _: String, _: String -> onChange() }
        tfDescr = TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build()
        tfDescr.textProperty().addListener { _: ObservableValue<out String?>?, _: String?, _: String? -> onChange() }
    }

    private fun defineTableView() {
        tvDataFiles = TableView<DataFileDescription>()
        tvDataFiles.prefWidth = INPUT_WIDTH
        tvDataFiles.prefHeight = 120.0
        tvDataFiles.placeholder = Label(getText("ui.stats.placeholder.datafiles"))
        colName = TableColumn(getText("ui.general.name"))
        colDescr = TableColumn(getText("ui.general.description"))
        colName.cellValueFactory = PropertyValueFactory("name")
        colDescr.cellValueFactory = PropertyValueFactory("description")
        colName.prefWidth = 290.0
        colDescr.prefWidth = 290.0
        tvDataFiles.columns.add(colName)
        tvDataFiles.columns.add(colDescr)
        val selectionModel: TableViewSelectionModel<DataFileDescription> = tvDataFiles.getSelectionModel()
        selectionModel.selectionMode = SelectionMode.SINGLE
        selectedDataFiles = selectionModel.selectedItems
        selectedDataFiles.addListener(ListChangeListener { onSelectFile() })
    }

    private fun createVBox(): VBox {
        return VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(650.0).setPadding(GAP).setChildren(
            createPaneTitle(),
            lblName,
            tfName,
            lblDescription,
            tfDescr,
            PaneBuilder().setHeight(20.0).build(),
            configBlock.gridPane,
            createVBoxData(),
            createPaneBtnBar()
        ).build()
    }

    private fun createPaneTitle(): Pane {
        val label = LabelBuilder(txtTitle).setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build()
        return PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build()
    }

    private fun createPaneSubTitleData(): Pane {
        return PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(lblSubTitleDataFiles).build()
    }

    private fun createPaneDataFiles(): Pane {
        return PaneBuilder().setHeight(120.0).setWidth(INPUT_WIDTH).setChildren(tvDataFiles).build()
    }

    private fun createVBoxData(): VBox {
        return VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(230.0).setChildren(
            createPaneSubTitleData(),
            createPaneDataFiles(),
            createBtnBarData()
        ).build()
    }

    override fun createBtnBar(): ButtonBar {
        return ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnSave).build()
    }

    private fun createBtnBarData(): ButtonBar {
        return ButtonBarBuilder().setButtons(btnSearch, btnRemove).build()
    }

    private fun onRemove() {
        val id = tvDataFiles.selectionModel.selectedIndex
        tvDataFiles.items.removeAt(id)
        checkStatus()
    }

    private fun onSelectFile() {
        checkStatus()
    }

    private fun onSave() {
        facade.saveProject(createProjectFe())
        stage.close()
    }

    private fun onChange() {
        checkStatus()
    }

    private fun onSearch() {
        dataSearch.show()
        if (dataSearch.isSelectionMade) {
            dataFileDescription = dataSearch.selectedItem
            tvDataFiles.items.add(dataFileDescription)
        }
        checkStatus()
    }

    private fun onHelp() {
        Help(getHelpText("help.statsprojnew.title"), getHelpText("help.statsprojnew.content"))
    }

    private fun createProjectFe(): StatsProjectFe {
        val name = tfName.text
        val descr = tfDescr.text
        val dataFiles: MutableList<String> = ArrayList()
        for (obj in tvDataFiles.items) {
            val (name1) = obj as DataFileDescription
            dataFiles.add(name1)
        }
        val houseSystem = configBlock.houseSystem.name
        val ayanamsha = configBlock.ayanamsha.name
        val obsPos = configBlock.observerPosition.name
        val eclProj = configBlock.eclipticProjection.name
        val config = AstronConfigFe(houseSystem, ayanamsha, eclProj, obsPos)
        return StatsProjectFe(name, descr, dataFiles[0], config)
    }


}