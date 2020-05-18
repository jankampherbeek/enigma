/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.configs.screens.ConfigOverview;
import com.radixpro.enigma.ui.configs.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesTableForConfig;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableChartData;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import com.radixpro.enigma.xchg.api.CalculatedFullChart;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.domain.CalculationSettings;
import com.radixpro.enigma.xchg.domain.ChartData;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.STYLESHEET;

public class ChartsStart {

   private static final Logger LOG = Logger.getLogger(ChartsStart.class);

   private static final double WIDTH = 700.0;
   private static final double HEIGHT = 700.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SUBTITLE_HEIGHT = 30.0;
   private static final double TV_HEIGHT = 200.0;
   private static final double TV_COL_WIDTH = 350.0;
   private static final double BTN_PANE_HEIGHT = 30.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private final Stage stage;
   private final Rosetta rosetta;
   private final List<PresentableChartData> availableCharts;
   private final PersistedPropertyApi propApi;
   private final PersistedConfigurationApi confApi;
   private ObservableList<PresentableChartData> selectedCharts;
   private Button btnDeleteChart;
   private Button btnShowChart;
   private TableView<PresentableChartData> tvCharts;
   private CalculatedFullChart currentFullChart;
   private TableColumn<PresentableChartData, String> colName;
   private TableColumn<PresentableChartData, String> colData;
   private Configuration currentConfig;

   public ChartsStart(Stage stage, Rosetta rosetta) {
      this.stage = checkNotNull(stage);
      this.rosetta = checkNotNull(rosetta);
      propApi = new PersistedPropertyApi();
      confApi = new PersistedConfigurationApi();
      availableCharts = new ArrayList<>();
      initialize();
      showChartOverview();
      stage.showAndWait();
      LOG.info("ChartsStart initialized.");
   }

   private void showChartOverview() {
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.charts.start.pagetitle"));
      showIt();
   }

   private void showIt() {
      stage.setScene(new Scene(createVBox()));
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      tvCharts = createTableViewCharts();
      vBox.getChildren().add(0, createPaneTitle());
      vBox.getChildren().add(1, createPaneSubTitleCharts());
      vBox.getChildren().add(2, tvCharts);
      vBox.getChildren().add(3, createPaneChartBtns());
      vBox.getChildren().add(4, createPaneSeparator());
      vBox.getChildren().add(5, createPaneSubTitleConfigs());
      vBox.getChildren().add(6, createPaneConfigDetails());
      vBox.getChildren().add(7, createPaneSeparator());
      vBox.getChildren().add(8, createPaneGeneralButtons());
      return vBox;
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.start.pagetitle"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneSubTitleCharts() {
      final Pane pane = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.start.chartstitle"), "subtitletext", WIDTH));
      return pane;
   }

   private Pane createPaneSubTitleConfigs() {
      final Pane pane = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.start.configtitle"), "subtitletext", WIDTH));
      return pane;
   }

   @SuppressWarnings({"unchecked", "rawtypes"})
   private TableView<PresentableChartData> createTableViewCharts() {
      TableView<PresentableChartData> tableView = new TableView<>();
      tableView.setPlaceholder(new Label(Rosetta.getRosetta().getText("ui.charts.start.placeholdercharts")));
      tableView.setPrefHeight(TV_HEIGHT);
      tableView.setPrefWidth(WIDTH);
      colName = new TableColumn<>(rosetta.getText("ui.charts.start.colheaderchartname"));
      colData = new TableColumn<>(rosetta.getText("ui.charts.start.colheaderchartdata"));
      colName.setCellValueFactory(new PropertyValueFactory("chartName"));
      colData.setCellValueFactory(new PropertyValueFactory("chartDataDescr"));
      colName.setPrefWidth(TV_COL_WIDTH);
      colData.setPrefWidth(TV_COL_WIDTH);
      tableView.getColumns().add(colName);
      tableView.getColumns().add(colData);
      for (PresentableChartData chartData : availableCharts) {
         tableView.getItems().add(chartData);
      }
      TableViewSelectionModel<PresentableChartData> selectionModel = tableView.getSelectionModel();
      selectionModel.setSelectionMode(SelectionMode.SINGLE);
      selectedCharts = selectionModel.getSelectedItems();
      selectedCharts.addListener((ListChangeListener<PresentableChartData>) change -> onSelectChart());
      return tableView;
   }

   private Pane createPaneChartBtns() {
      final Pane pane = PaneFactory.createPane(BTN_PANE_HEIGHT, WIDTH);
      pane.getChildren().add(createButtonBarCharts());
      return pane;
   }

   private ButtonBar createButtonBarCharts() {
      ButtonBar buttonBar = new ButtonBar();
      btnShowChart = ButtonFactory.createButton(rosetta.getText("ui.charts.start.btn.show"), true);
      btnDeleteChart = ButtonFactory.createButton(rosetta.getText("ui.charts.start.btn.delete"), true);
      Button btnNewChart = ButtonFactory.createButton(rosetta.getText("ui.charts.start.btn.new"), false);
      Button btnSearchChart = ButtonFactory.createButton(rosetta.getText("ui.charts.start.btn.search"), false);

      btnShowChart.setOnAction(click -> onShowSelectedChart());
      btnDeleteChart.setOnAction(click -> onDeleteChart());
      btnNewChart.setOnAction(click -> onNewChart());
      btnSearchChart.setOnAction(click -> onSearchChart());

      buttonBar.getButtons().add(btnShowChart);
      buttonBar.getButtons().add(btnDeleteChart);
      buttonBar.getButtons().add(btnNewChart);
      buttonBar.getButtons().add(btnSearchChart);
      return buttonBar;
   }

   private Pane createPaneGeneralButtons() {
      final Pane pane = PaneFactory.createPane(BTN_PANE_HEIGHT, WIDTH);
      pane.getChildren().add(createButtonBarGeneral());
      return pane;
   }

   private ButtonBar createButtonBarGeneral() {
      ButtonBar buttonBar = new ButtonBar();
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnConfig = ButtonFactory.createButton(rosetta.getText("ui.charts.start.btn.config"), false);
      Button btnExit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.exit"), false);

      btnConfig.setOnAction(click -> onConfig());
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> stage.close());

      buttonBar.getButtons().addAll(btnHelp, btnConfig, btnExit);
      return buttonBar;
   }

   private Pane createPaneSeparator() {
      return PaneFactory.createPane(SEPARATOR_HEIGHT, WIDTH);
   }

   private Pane createPaneConfigDetails() {
      Pane pane = PaneFactory.createPane(TV_HEIGHT, WIDTH);

      // TODO Release 2020.2: replace with DI
      PropertiesForConfig prop4Config = new PropertiesForConfig(currentConfig,
            new CelObjectsInConfig(rosetta),
            new AspectsInConfig(rosetta), rosetta);
      TableView<PresentableProperty> tvConfigs = new PropertiesTableForConfig().getTableView(TV_HEIGHT, WIDTH, prop4Config.getProperties());
      pane.getChildren().add(tvConfigs);
      return pane;
   }

   void onConfig() {
      new ConfigOverview();
      initialize();
      showIt();
   }

   void onNewChart() {
      ChartsInput chartsInput = new ChartsInput();
      if (chartsInput.getInputStatus() == InputStatus.READY) {
         long newChartId = chartsInput.getNewChartId();
         ChartData chartData = addChart(newChartId);
         showChart(chartData);
      }
   }

   void onSearchChart() {
      ChartsSearch chartsSearch = new ChartsSearch();
      if (chartsSearch.isSelectionMade()) {
         ChartData chartData = chartsSearch.getSelectedItem();
         PresentableChartData presentableChartData = new PresentableChartData(chartData);
         availableCharts.add(presentableChartData);
         colName.setCellValueFactory(new PropertyValueFactory<>("chartName"));
         colData.setCellValueFactory(new PropertyValueFactory<>("chartDataDescr"));
         tvCharts.getItems().add(presentableChartData);
      }
   }


   private void onSelectChart() {
      if (selectedCharts.isEmpty()) {
         btnDeleteChart.setDisable(true);
         btnShowChart.setDisable(true);
      } else {
         btnDeleteChart.setDisable(false);
         btnShowChart.setDisable(false);
      }
   }


   private void initialize() {
      int currentConfigId;
      List<Property> configs = propApi.read("config");
      if (configs.size() > 0) {
         currentConfigId = Integer.parseInt(propApi.read("config").get(0).getValue());
         currentConfig = confApi.read(currentConfigId).get(0);
      } else {
         LOG.error("Could not read config as api returned empty list. Calling FailFastHandler");
         new FailFastHandler().terminate("Database did not return configurations.");
      }
   }


   private ChartData addChart(final long chartId) {
      PersistedChartDataApi api = new PersistedChartDataApi();
      ChartData chartData = api.read(chartId).get(0);
      PresentableChartData presentableChartData = new PresentableChartData(chartData);
      colName.setCellValueFactory(new PropertyValueFactory<>("chartName"));
      colData.setCellValueFactory(new PropertyValueFactory<>("chartDataDescr"));
      tvCharts.getItems().add(presentableChartData);
      return chartData;
   }

   private void onDeleteChart() {
      PresentableChartData presChartData = selectedCharts.get(0);
      tvCharts.getItems().remove(presChartData);
      PersistedChartDataApi api = new PersistedChartDataApi();
      api.delete(presChartData.getOriginalData());

   }

   private void onShowSelectedChart() {
      PresentableChartData presChartData = selectedCharts.get(0);
      showChart(presChartData.getOriginalData());
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsstart.title"), rosetta.getHelpText("help.chartsstart.content"));
   }

   private void showChart(final ChartData chartData) {
      CalculationSettings settings = new CalculationSettings(currentConfig);
      currentFullChart = new CalculatedFullChart(chartData.getFullDateTime(), chartData.getLocation(), settings);
      showPositions(chartData);
      drawChart2D(chartData.getChartMetaData().getName());
   }

   private void showPositions(ChartData chartData) {
      new ChartsData(currentFullChart, chartData);
   }

   private void drawChart2D(final String name) {
      ChartsDrawing2d chartsDrawing2d = new ChartsDrawing2d();
      chartsDrawing2d.setName(name);
      chartsDrawing2d.setFullChart(currentFullChart);

   }

}
