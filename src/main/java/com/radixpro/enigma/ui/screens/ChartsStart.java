/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.reqresp.CalculatedChartRequest;
import com.radixpro.enigma.domain.reqresp.CalculatedChartResponse;
import com.radixpro.enigma.references.*;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.ui.charts.screens.ChartsData;
import com.radixpro.enigma.ui.creators.ButtonFactory;
import com.radixpro.enigma.ui.creators.LabelFactory;
import com.radixpro.enigma.ui.creators.PaneFactory;
import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.ui.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.ui.screens.helpers.PropertiesTableForConfig;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableChartData;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import com.radixpro.enigma.xchg.api.CalculatedChartApi;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.ChartData;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import com.radixpro.enigma.xchg.domain.Location;
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
import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

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
   private final ChartsAspects chartsAspects;
   private final Rosetta rosetta;
   private final ChartsTetenburg chartsTetenburg;
   private final ChartsDrawing2d chartsDrawing2d;
   private final ChartsMidpoints chartsMidpoints;
   private final ChartsTransitsInput chartsTransitsInput;
   private final ChartsSearch chartsSearch;
   private final ChartsInput chartsInput;
   private final ConfigOverview configOverview;
   private final SessionState state;
   private final PersistedPropertyApi propApi;
   private final PersistedConfigurationApi confApi;
   private final PersistedChartDataApi chartDataApi;
   private final CalculatedChartApi calculatedChartApi;
   private final PropertiesForConfig propertiesForConfig;
   private final PropertiesTableForConfig propertiesTableForConfig;
   private final CelObjectsInConfig celObjectsInConfig;
   private final AspectsInConfig aspectsInConfig;
   private ObservableList<PresentableChartData> selectedCharts;
   private List<PresentableChartData> availableCharts;
   private Stage stage;
   private Button btnDeleteChart;
   private Button btnShowChart;
   private MenuItem miShowChart;
   private MenuItem miDeleteChart;
   private MenuItem miAspects;
   private MenuItem miMidpoints;
   private MenuItem miTransits;
   private MenuItem miPrimary;
   private MenuItem miSecondary;
   private MenuItem miSolar;
   private MenuItem miTetenburg;
   private TableView<PresentableChartData> tvCharts;
   private FullChart currentFullChart;
   private TableColumn<PresentableChartData, String> colName;
   private TableColumn<PresentableChartData, String> colData;
   private Configuration currentConfig;

   public ChartsStart(final Rosetta rosetta, final SessionState state, final CalculatedChartApi calculatedChartApi,
                      final ChartsTetenburg chartsTetenburg, final ChartsAspects chartsAspects, final ChartsMidpoints chartsMidpoints,
                      final ChartsTransitsInput chartsTransitsInput, final ChartsSearch chartsSearch, final ChartsInput chartsInput,
                      final PersistedChartDataApi chartDataApi, final PersistedConfigurationApi confApi, final PersistedPropertyApi propApi,
                      final ConfigOverview configOverview, final PropertiesForConfig propertiesForConfig, final CelObjectsInConfig celObjectsInConfig,
                      final AspectsInConfig aspectsInConfig, final PropertiesTableForConfig propertiesTableForConfig, final ChartsDrawing2d chartsDrawing2d) {
      this.rosetta = checkNotNull(rosetta);
      this.state = checkNotNull(state);
      this.calculatedChartApi = checkNotNull(calculatedChartApi);
      this.chartsTetenburg = checkNotNull(chartsTetenburg);
      this.chartsAspects = chartsAspects;
      this.chartsMidpoints = chartsMidpoints;
      this.chartsTransitsInput = chartsTransitsInput;
      this.chartsSearch = chartsSearch;
      this.chartsInput = chartsInput;
      this.configOverview = configOverview;
      this.chartDataApi = chartDataApi;
      this.propApi = propApi;
      this.confApi = confApi;
      this.propertiesForConfig = propertiesForConfig;
      this.celObjectsInConfig = celObjectsInConfig;
      this.aspectsInConfig = aspectsInConfig;
      this.propertiesTableForConfig = propertiesTableForConfig;
      this.chartsDrawing2d = chartsDrawing2d;
   }

   public void show() {
      stage = new Stage();
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
      miMidpoints.setOnAction(e -> onMidpoints());
      menuAnalysis.getItems().addAll(miAspects, miMidpoints);
      Menu menuProg = new Menu(rosetta.getText("menu.charts.progressive"));
      miTransits = new MenuItem(rosetta.getText("menu.charts.progressive.transits"));
      miPrimary = new MenuItem(rosetta.getText("menu.charts.progressive.primary"));
      miSecondary = new MenuItem(rosetta.getText("menu.charts.progressive.secondary"));
      miSolar = new MenuItem(rosetta.getText("menu.charts.progressive.solar"));
      miTetenburg = new MenuItem(rosetta.getText("menu.charts.progressive.tetenburg"));
      miTransits.setDisable(true);
      miTransits.setOnAction(e -> onTransits());
      miSecondary.setDisable(true);
      miPrimary.setDisable(true);
      miSolar.setDisable(true);
      miTetenburg.setDisable(true);
      miTetenburg.setOnAction(e -> onTetenburg());
      menuProg.getItems().addAll(miTransits, miPrimary, miSecondary, miSolar, miTetenburg);
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
      tableView.setPlaceholder(new Label(rosetta.getText("ui.charts.start.placeholdercharts")));
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
      TableView<PresentableProperty> tvConfigs = propertiesTableForConfig.getTableView(TV_HEIGHT, WIDTH,
            propertiesForConfig.getProperties(currentConfig, celObjectsInConfig, aspectsInConfig));
      pane.getChildren().add(tvConfigs);
      return pane;
   }

   void onConfig() {
      configOverview.show();
      currentConfig = state.getSelectedConfig();
      initialize();
      showIt();
   }

   void onNewChart() {
      chartsInput.show();
      if (chartsInput.getInputStatus() == InputStatus.READY) {
         int newChartId = chartsInput.getNewChartId();
         ChartData chartData = addChart(newChartId);
//         showChart();    // TODO show automatically or change user manual    If shown, make sure it is saved in the state
      }
   }

   void onSearchChart() {
      chartsSearch.show();
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
      miTransits.setDisable(emptySelection);
      miSecondary.setDisable(emptySelection);
      miPrimary.setDisable(emptySelection);
      miSolar.setDisable(emptySelection);
      miTetenburg.setDisable(emptySelection);
      if (!emptySelection) {
         // todo calculate chart and save fullchart in state.
         // TODO add creation of settings to Config class
         Configuration config = state.getSelectedConfig();
         List<ConfiguredCelObject> confPoints = config.getAstronConfiguration().getCelObjects();
         List<IChartPoints> points = new ArrayList<>();
         for (ConfiguredCelObject cPoint : confPoints) {
            points.add(cPoint.getCelObject());
         }

         ObserverPositions obsPos = config.getAstronConfiguration().getObserverPosition();
         EclipticProjections eclProj = config.getAstronConfiguration().getEclipticProjection();
         Ayanamshas ayanamsha = config.getAstronConfiguration().getAyanamsha();
         HouseSystems houseSystem = config.getAstronConfiguration().getHouseSystem();
         ChartCalcSettings settings = new ChartCalcSettings(points, obsPos, eclProj, ayanamsha, houseSystem);
         final PresentableChartData presentableChartData = selectedCharts.get(0);
         ChartData chartData = presentableChartData.getOriginalData();
         FullDateTime dateTime = presentableChartData.getOriginalData().getFullDateTime();
         Location location = presentableChartData.getOriginalData().getLocation();
         CalculatedChartRequest request = new CalculatedChartRequest(settings, dateTime, location);

         CalculatedChartResponse response = calculatedChartApi.calcChart(request);
         CalculatedChart calculatedChart = response.getCalculatedChart();
         // TODO check resultMsg
         FullChart fullChart = new FullChart(chartData, calculatedChart);
         state.setSelectedChart(fullChart);
      }
   }


   private void initialize() {
      int currentConfigId;
      List<Property> configs = propApi.read("config");
      if (!configs.isEmpty()) {
         currentConfigId = Integer.parseInt(propApi.read("config").get(0).getValue());
         currentConfig = confApi.read(currentConfigId).get(0);
         state.setSelectedConfig(currentConfig);
      } else {
         LOG.error("Could not read config as api returned empty list. Calling FailFastHandler");
         new FailFastHandler().terminate("Database did not return configurations.");
      }
   }


   private ChartData addChart(final int chartId) {
      ChartData chartData = chartDataApi.read(chartId).get(0);
      PresentableChartData presentableChartData = new PresentableChartData(chartData);
      colName.setCellValueFactory(new PropertyValueFactory<>("chartName"));
      colData.setCellValueFactory(new PropertyValueFactory<>("chartDataDescr"));
      tvCharts.getItems().add(presentableChartData);
      return chartData;
   }

   private void onDeleteChart() {
      PresentableChartData presChartData = selectedCharts.get(0);
      tvCharts.getItems().remove(presChartData);
      state.deSelectChart();
      chartDataApi.delete(presChartData.getOriginalData().getId());
   }

   private void onShowSelectedChart() {
      PresentableChartData presChartData = selectedCharts.get(0);
      showChart();
   }

   private void onAspects() {
      chartsAspects.show();
   }

   // TODO combine logic of onAspects and onMidpoints
   private void onMidpoints() {
      chartsMidpoints.show();
   }

   private void onTetenburg() {
      PresentableChartData presChartData = selectedCharts.get(0);
      MetaDataForAnalysis meta = new MetaDataForAnalysis(presChartData.getChartName(), currentConfig.getName(), 1.0);  // orb is not used
      chartsTetenburg.show(meta);
   }

   private void onTransits() {
      chartsTransitsInput.show();
   }


   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsstart.title"), rosetta.getHelpText("help.chartsstart.content"));
   }

   private void showChart() {
      currentFullChart = state.getSelectedChart();
      showPositions();
      drawChart2D();
   }

   private void showPositions() {
      new ChartsData(state);     // TODO use factory
   }

   private void drawChart2D() {
      String chartName = state.getSelectedChart().getChartData().getChartMetaData().getName();
//      ChartsDrawing2d chartsDrawing2d = new ChartsDrawing2d(state);    // todo use factory
      chartsDrawing2d.setDrawingInfo();
   }

}
