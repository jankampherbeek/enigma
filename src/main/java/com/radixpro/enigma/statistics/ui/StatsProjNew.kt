/*
 * Jan Kampherbeek, (c) 2021.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.Rosetta.getHelpText
import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.Ayanamshas.Companion.getAyanamshaForId
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.EclipticProjections.Companion.getProjectionForId
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.HouseSystems.Companion.getSystemForId
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.references.ObserverPositions.Companion.getObserverPositionForId
import com.radixpro.enigma.share.ui.domain.AstronConfigFe
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe
import com.radixpro.enigma.ui.creators.*
import com.radixpro.enigma.ui.screens.InputScreen
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
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import java.util.*

/**
 * Input screen for new projects for statistics.
 */
class StatsProjNew(
    private val dataSearch: StatsDataSearch,
    private val facade: StatsFacade
) : InputScreen() {

    // texts
    private lateinit var lblAyanamsha: Label
    private lateinit var lblDescription: Label
    private lateinit var lblEclipticProj: Label
    private lateinit var lblHouseSystem: Label
    private lateinit var lblName: Label
    private lateinit var lblObserverPos: Label
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

    // choiceboxes
    private lateinit var choiceBoxHouseSystem: ChoiceBox<*>
    private lateinit var choiceBoxObserverPos: ChoiceBox<*>
    private lateinit var choiceBoxEclipticProj: ChoiceBox<*>
    private lateinit var choiceBoxAyanamsha: ChoiceBox<*>

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
        defineChoiceBoxes()
        defineTextFields()
        defineTableView()
    }

    private fun defineTexts() {
        lblAyanamsha = LabelBuilder(getText("ui.general.ayanamsha")).build()
        lblDescription = LabelBuilder(getText("ui.stats.newproj.lbldescription")).build()
        lblEclipticProj = LabelBuilder(getText("ui.general.eclipticprojection")).build()
        lblHouseSystem = LabelBuilder(getText("ui.general.housesystem")).build()
        lblName = LabelBuilder(getText("ui.stats.newproj.lblname")).build()
        lblObserverPos = LabelBuilder(getText("ui.general.observerposition")).build()
        lblSubTitleDataFiles = LabelBuilder(getText("ui.stats.datafiles.subtitle")).setPrefWidth(INPUT_WIDTH).setPrefHeight(SUBTITLE_HEIGHT)
            .setStyleClass("subtitletext").build()
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
        tfDescr.textProperty().addListener { _: ObservableValue<out String>, _: String, _: String -> onChange() }
    }

    private fun defineChoiceBoxes() {
        choiceBoxHouseSystem = ChoiceBoxBuilder().setPrefWidth(INPUT_DATA_WIDTH).setItems(HouseSystems.observableList).build()
        choiceBoxHouseSystem.selectionModel.select(0)
        choiceBoxObserverPos = ChoiceBoxBuilder().setPrefWidth(INPUT_DATA_WIDTH).setItems(ObserverPositions.observableList).build()
        choiceBoxObserverPos.selectionModel.select(0)
        choiceBoxEclipticProj = ChoiceBoxBuilder().setPrefWidth(INPUT_DATA_WIDTH).setItems(EclipticProjections.observableList).build()
        choiceBoxEclipticProj.selectionModel.select(0)
        choiceBoxEclipticProj.selectionModel.selectedIndexProperty().addListener { _: ObservableValue<out Number>, _: Number, _: Number -> onEclipticChange() }
        choiceBoxAyanamsha = ChoiceBoxBuilder().setPrefWidth(INPUT_DATA_WIDTH).setItems(Ayanamshas.observableList).build()
        choiceBoxAyanamsha.selectionModel.select(0)
        choiceBoxAyanamsha.isDisable = true
    }

    private fun defineTableView() {
        tvDataFiles = TableView<DataFileDescription>()
        tvDataFiles.prefWidth = 572.0
        tvDataFiles.prefHeight = 120.0
        tvDataFiles.placeholder = Label(getText("ui.stats.placeholder.datafiles"))
        colName = TableColumn(getText("ui.general.name"))
        colDescr = TableColumn(getText("ui.general.description"))
        colName.cellValueFactory = PropertyValueFactory("name")
        colDescr.cellValueFactory = PropertyValueFactory("description")
        colName.prefWidth = 286.0
        colDescr.prefWidth = 286.0
        tvDataFiles.columns.add(colName)
        tvDataFiles.columns.add(colDescr)
        val selectionModel: TableViewSelectionModel<DataFileDescription> = tvDataFiles.selectionModel
        selectionModel.selectionMode = SelectionMode.SINGLE
        selectedDataFiles = selectionModel.selectedItems
        selectedDataFiles.addListener(ListChangeListener { onSelectFile() })
    }

    private fun createVBox(): VBox {
        return VBoxBuilder().setWidth(INPUT_WIDTH + 2 * GAP).setHeight(500.0 + 2 * GAP).setPadding(GAP).setChildren(createGridPane()).build()
    }

    private fun createGridPane(): GridPane {  // col, row
        val gridPane = GridPaneBuilder().setPrefHeight(500.0).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).setStyleSheet(STYLESHEET).build()
        gridPane.add(createPaneTitle(), 0, 0, 3, 1)
        gridPane.add(lblName, 0, 1, 1, 1)
        gridPane.add(tfName, 1, 1, 2, 1)
        gridPane.add(lblDescription, 0, 2, 1, 1)
        gridPane.add(tfDescr, 1, 2, 2, 1)
        gridPane.add(lblHouseSystem, 0, 3, 1, 1)
        gridPane.add(choiceBoxHouseSystem, 1, 3, 2, 1)
        gridPane.add(lblObserverPos, 0, 4, 1, 1)
        gridPane.add(choiceBoxObserverPos, 1, 4, 2, 1)
        gridPane.add(lblEclipticProj, 0, 5, 1, 1)
        gridPane.add(choiceBoxEclipticProj, 1, 5, 2, 1)
        gridPane.add(lblAyanamsha, 0, 6, 1, 1)
        gridPane.add(choiceBoxAyanamsha, 1, 6, 2, 1)
        gridPane.add(createPaneSubTitleData(), 0, 7, 3, 1)
        gridPane.add(createPaneDataFiles(), 0, 8, 3, 1)
        gridPane.add(createBtnBarData(), 2, 9, 1, 1)
        gridPane.add(createBtnBar(), 0, 10, 2, 1)
        return gridPane
    }

    private fun createPaneTitle(): Pane {
        val label = LabelBuilder(txtTitle).setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build()
        return PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build()
    }

    private fun createPaneSubTitleData(): Pane {
        return PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(lblSubTitleDataFiles).build()
    }

    private fun createPaneDataFiles(): Pane {
        return PaneBuilder().setHeight(120.0).setWidth(INPUT_DATA_WIDTH).setChildren(tvDataFiles).build()
    }

    override fun createBtnBar(): ButtonBar {
        return ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnSave).build()
    }

    private fun createBtnBarData(): ButtonBar {
        return ButtonBarBuilder().setButtons(btnSearch, btnRemove).build()
    }

    private fun onEclipticChange() {
        val selectedIndex = choiceBoxEclipticProj.selectionModel.selectedIndex
        if (selectedIndex >= 0) {
            val eclipticProjectionSelectedIndex = EclipticProjections.indexMappings.getEnumIdForSequenceId(selectedIndex)
            choiceBoxAyanamsha.isDisable = eclipticProjectionSelectedIndex != EclipticProjections.SIDEREAL.id
        }
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

    private fun getHouseSystem(): HouseSystems {
        val index = choiceBoxHouseSystem.selectionModel.selectedIndex
        return getSystemForId(HouseSystems.indexMappings.getEnumIdForSequenceId(index))
    }

    private fun getObserverPosition(): ObserverPositions {
        val index = choiceBoxObserverPos.selectionModel.selectedIndex
        return getObserverPositionForId(ObserverPositions.indexMappings.getEnumIdForSequenceId(index))
    }

    private fun getEclipticProjection(): EclipticProjections {
        val index = choiceBoxEclipticProj.selectionModel.selectedIndex
        return getProjectionForId(EclipticProjections.indexMappings.getEnumIdForSequenceId(index))
    }

    private fun getAyanamsha(): Ayanamshas {
        val index = choiceBoxAyanamsha.selectionModel.selectedIndex
        choiceBoxAyanamsha.isDisable = true
        return getAyanamshaForId(Ayanamshas.indexMappings.getEnumIdForSequenceId(index))
    }

    private fun createProjectFe(): StatsProjectFe {
        val name = tfName.text
        val descr = tfDescr.text
        val dataFiles: MutableList<String> = ArrayList()
        for (obj in tvDataFiles.items) {
            val (name1) = obj as DataFileDescription
            dataFiles.add(name1)
        }
        val houseSystem = getHouseSystem()
        val ayanamsha = getAyanamsha()
        val obsPos = getObserverPosition()
        val eclProj = getEclipticProjection()
        val config = AstronConfigFe(houseSystem.name, ayanamsha.name, eclProj.name, obsPos.name)
        return StatsProjectFe(name, descr, dataFiles[0], config)
    }

}