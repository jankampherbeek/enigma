/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.factories.ChartsAspectsFactory;
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
import com.radixpro.enigma.xchg.api.*;
import com.radixpro.enigma.xchg.domain.CalculationSettings;
import com.radixpro.enigma.xchg.domain.ChartData;
import com.radixpro.enigma.xchg.domain.FullChart;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.xchg.domain.calculatedobjects.IObjectVo;
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
   private MenuItem miShowChart;
   private MenuItem miDeleteChart;
   private MenuItem miAspects;
   private MenuItem miMidpoints;
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
      vBox.getChildren().addAll(createMenuBar(), createPaneTitle(), createPaneSubTitleCharts(), tvCharts,
            createPaneChartBtns(), createPaneSeparator(), createPaneSubTitleConfigs(), createPaneConfigDetails(),
            createPaneSeparator(), createPaneGeneralButtons());
      return vBox;
   }

   private MenuBar createMenuBar() {
      Menu menuGeneral = new Menu(rosetta.getText("menu.general"));
      MenuItem miExit = new MenuItem(rosetta.getText("menu.general.exit"));
      miExit.setOnAction(e -> stage.close());
      menuGeneral.getItems().add(miExit);
      Menu menuCharts = new Menu(rosetta.getText("menu.charts"));
      MenuItem miNewChart = new MenuItem(rosetta.getText("menu.charts.new"));
      miNewChart.setOnAction(e -> onNewChart());
      MenuItem miSearchChart = new MenuItem(rosetta.getText("menu.charts.search"));
      miSearchChart.setOnAction(e -> onSearchChart());
      miShowChart = new MenuItem(rosetta.getText("menu.charts.show"));
      miShowChart.setDisable(true);
      miShowChart.setOnAction(e -> onShowSelectedChart());
      miDeleteChart = new MenuItem(rosetta.getText("menu.charts.delete"));
      miDeleteChart.setDisable(true);
      miDeleteChart.setOnAction(e -> onDeleteChart());
      menuCharts.getItems().addAll(miNewChart, miSearchChart, miShowChart, miDeleteChart);
      Menu menuConfigs = new Menu(rosetta.getText("menu.charts.configs"));
      MenuItem miConfigScreen = new MenuItem(rosetta.getText("menu.charts.configs.overview"));
      miConfigScreen.setOnAction(e -> onConfig());
      menuConfigs.getItems().add(miConfigScreen);
      Menu menuAnalysis = new Menu(rosetta.getText("menu.charts.analysis"));
      miAspects = new MenuItem(rosetta.getText("menu.charts.analysis.aspects"));
      miAspects.setDisable(true);
      miAspects.setOnAction(e -> onAspects());
      miMidpoints = new MenuItem(rosetta.getText("menu.charts.analysis.midpoints"));
      miMidpoints.setDisable(true);
      menuAnalysis.getItems().addAll(miAspects, miMidpoints);
      Menu menuProg = new Menu(rosetta.getText("menu.charts.progressive"));
      MenuItem miTransits = new MenuItem(rosetta.getText("menu.charts.progressive.transits"));
      MenuItem miPrimary = new MenuItem(rosetta.getText("menu.charts.progressive.primary"));
      MenuItem miSecondary = new MenuItem(rosetta.getText("menu.charts.progressive.secondary"));
      MenuItem miSolar = new MenuItem(rosetta.getText("menu.charts.progressive.solar"));
      menuProg.getItems().addAll(miTransits, miPrimary, miSecondary, miSolar);
      Menu menuHelp = new Menu(rosetta.getText("menu.general.help"));
      MenuItem miShowHelp = new MenuItem(rosetta.getText("menu.general.help.showhelp"));
      miShowHelp.setOnAction(e -> onHelp());
      menuHelp.getItems().add(miShowHelp);
      MenuBar menuBar = new MenuBar();
      menuBar.getMenus().addAll(menuGeneral, menuCharts, menuConfigs, menuAnalysis, menuProg, menuHelp);
      return menuBar;
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
      boolean emptySelection = selectedCharts.isEmpty();
      btnDeleteChart.setDisable(emptySelection);
      miDeleteChart.setDisable(emptySelection);
      btnShowChart.setDisable(emptySelection);
      miShowChart.setDisable(emptySelection);
      miAspects.setDisable(emptySelection);
      miMidpoints.setDisable(emptySelection);
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

   private void onAspects() {
      AspectsApi api = new AspectsApiFactory().createApi();
      PresentableChartData presChartData = selectedCharts.get(0);
      ChartData chartData = presChartData.getOriginalData();
      CalculationSettings settings = new CalculationSettings(currentConfig);
      FullChart fullChart = new CalculatedFullChart(chartData.getFullDateTime(), chartData.getLocation(), settings).getFullChart();
      List<IObjectVo> celObjectList = fullChart.getAllCelBodyPositions();
      List<IObjectVo> fullHousesList = fullChart.getAllHousePositions();
      List<IObjectVo> housesList = new ArrayList<>();
      housesList.add(fullHousesList.get(0));
      housesList.add(fullHousesList.get(1));
      final List<IAnalyzedPair> aspects = api.analyzeAspects(celObjectList, housesList, currentConfig.getDelinConfiguration().getAspectConfiguration());
      MetaDataForAnalysis meta = new MetaDataForAnalysis(presChartData.getChartName(), currentConfig.getName(), currentConfig.getDelinConfiguration().getAspectConfiguration().getBaseOrb());
      final ChartsAspects chartsAspects = new ChartsAspectsFactory().getChartsAspects(aspects, meta);
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
      chartsDrawing2d.setDrawingInfo(currentFullChart, currentConfig);
   }

}
