/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.astronpos.FullChart;
import com.radixpro.enigma.domain.astronpos.FullPointPosition;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.ui.creators.ButtonBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.presentationmodel.*;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

@SuppressWarnings("rawtypes")
public class ChartsData {

   public static final double GAP = 6.0;
   private static final double WIDTH = 1000.0;
   private static final double HALF_WIDTH = 490;
   private static final double TV_CELOBJECTS_HEIGHT = 400.0;
   private static final double TV_MUNDOBJECTS_HEIGHT = 500.0;
   private static final double LEGENDA_HEIGHT = 300.0;
   private static final double BUTTONBAR_HEIGHT = 200.0;
   private static final double DATA_HEIGHT = 850.0;
   private final Stage stage;
   private final CalculatedChart calculatedChart;
   private final String glyphFont;
   private final String dataFont;
   private final FullChartInputData fullChartInputData;
   private TableView tvMundaneData;
   private TableColumn<String, PresentableMundanePosition> tvMundColName;
   private TableColumn<String, PresentableMundanePosition> tvMundColLongitude;
   private TableColumn<String, PresentableMundanePosition> tvMundColSignGlyph;
   private TableColumn<String, PresentableMundanePosition> tvMundColRa;
   private TableColumn<String, PresentableMundanePosition> tvMundColDecl;
   private TableColumn<String, PresentableMundanePosition> tvMundColAzimuth;
   private TableColumn<String, PresentableMundanePosition> tvMundColAltitude;

   public ChartsData(@NotNull final SessionState state) {
      checkArgument(state.selectedConfigIsSet());
      FullChart fullChart = state.getSelectedChart();
      this.fullChartInputData = fullChart.getChartData();
      this.calculatedChart = fullChart.getCalculatedChart();
      stage = new Stage();
      glyphFont = " -fx-font-family: \"EnigmaAstrology\";  -fx-font-size: 14;";
      dataFont = "-fx-font-family: \"Courier\";";
      stage.setScene(new Scene(createGridPane()));
      stage.show();
   }

   private GridPane createGridPane() {
      GridPane gridPane = new GridPane();
      gridPane.setPrefHeight(DATA_HEIGHT);
      gridPane.setHgap(GAP);
      gridPane.setVgap(GAP);
      gridPane.setPadding(new Insets(GAP, GAP, GAP, GAP));
      String title = Rosetta.getText("ui.charts.data.pagetitleprefix") + " "
            + fullChartInputData.getChartMetaData().getName();
      gridPane.add(new LabelBuilder("").setText(title).setPrefWidth(WIDTH).build(), 0, 0, 3, 1);
      TableView tvCelObjects = createTVCelObjectData();
      gridPane.add(tvCelObjects, 0, 1, 3, 1);
      createTVMundaneData();
      gridPane.add(tvMundaneData, 0, 2, 1, 2);
      gridPane.add(createPaneData(), 2, 2, 1, 1);
      gridPane.add(createPaneBtnBar(), 2, 3, 1, 1);
      return gridPane;
   }


   @SuppressWarnings("unchecked")
   private TableView createTVCelObjectData() {
      TableView tableView = new TableView();
      tableView.setPrefHeight(TV_CELOBJECTS_HEIGHT);
      tableView.setPrefWidth(WIDTH);
      TableColumn<String, PresentableEclipticPosition> tvEclColBodyGlyph = new TableColumn<>();
      TableColumn<String, PresentableEclipticPosition> tvEclColLongitude = new TableColumn<>(Rosetta.getText("ui.charts.data.tvecliptic.longitude"));
      TableColumn<String, PresentableEclipticPosition> tvEclColSignGlyph = new TableColumn<>();
      TableColumn<String, PresentableEclipticPosition> tvEclColLongSpeed = new TableColumn<>(Rosetta.getText("ui.charts.data.tvecliptic.longspeed"));
      TableColumn<String, PresentableEclipticPosition> tvEclColLatitude = new TableColumn<>(Rosetta.getText("ui.charts.data.tvecliptic.latitude"));
      TableColumn<String, PresentableEclipticPosition> tvEclColLatSpeed = new TableColumn<>(Rosetta.getText("ui.charts.data.tvecliptic.latspeed"));
      TableColumn<String, PresentableEquatorialPosition> tvEquColRa = new TableColumn<>(Rosetta.getText("ui.charts.data.tvequator.ra"));
      TableColumn<String, PresentableEquatorialPosition> tvEquColRaSpeed = new TableColumn<>(Rosetta.getText("ui.charts.data.tvequator.raspeed"));
      TableColumn<String, PresentableEquatorialPosition> tvEquColDecl = new TableColumn<>(Rosetta.getText("ui.charts.data.tvequator.decl"));
      TableColumn<String, PresentableEquatorialPosition> tvEquColDeclSpeed = new TableColumn<>(Rosetta.getText("ui.charts.data.tvequator.declspeed"));
      TableColumn<String, PresentableHorizontalPosition> tvHorColAzimuth = new TableColumn<>(Rosetta.getText("ui.charts.data.tvhorizontal.azimuth"));
      TableColumn<String, PresentableHorizontalPosition> tvHorColAltitude = new TableColumn<>(Rosetta.getText("ui.charts.data.tvhorizontal.altitude"));
      TableColumn<String, PresentableDistancePosition> tvDistColDistance = new TableColumn<>(Rosetta.getText("ui.charts.data.tvdistance.radv"));
      TableColumn<String, PresentableDistancePosition> tvDistColDistSpeed = new TableColumn<>(Rosetta.getText("ui.charts.data.tvdistance.radvspeed"));

      tvEclColBodyGlyph.setStyle(glyphFont);
      tvEclColSignGlyph.setStyle(glyphFont);
      tvEclColLongitude.setStyle(dataFont);
      tableView.getColumns().add(tvEclColBodyGlyph);
      tableView.getColumns().add(tvEclColLongitude);
      tableView.getColumns().add(tvEclColSignGlyph);
      tableView.getColumns().add(tvEclColLongSpeed);
      tableView.getColumns().add(tvEclColLatitude);
      tableView.getColumns().add(tvEclColLatSpeed);
      tableView.getColumns().add(tvEquColRa);
      tableView.getColumns().add(tvEquColRaSpeed);
      tableView.getColumns().add(tvEquColDecl);
      tableView.getColumns().add(tvEquColDeclSpeed);
      tableView.getColumns().add(tvHorColAzimuth);
      tableView.getColumns().add(tvHorColAltitude);
      tableView.getColumns().add(tvDistColDistance);
      tableView.getColumns().add(tvDistColDistSpeed);
      final List<IPosition> bodies = calculatedChart.getCelPoints();
      int count = bodies.size();
      // ecliptical
      for (int i = 0; i < count; i++) {
         IPosition celObjectPosition = calculatedChart.getCelPoints().get(i);
         FullPointPosition fpp = (FullPointPosition) celObjectPosition;
         final double[] horPos = {fpp.getHorPos().getMainCoord(), fpp.getHorPos().getDeviation()};
         PresentableCelObjectPosition presPos = new PresentableCelObjectPosition(fpp, horPos);
         tvEclColBodyGlyph.setCellValueFactory(new PropertyValueFactory<>("celBodyGlyph"));
         tvEclColLongitude.setCellValueFactory(new PropertyValueFactory<>("formattedLongitude"));
         tvEclColSignGlyph.setCellValueFactory(new PropertyValueFactory<>("signGlyph"));
         tvEclColLongSpeed.setCellValueFactory(new PropertyValueFactory<>("formattedLongSpeed"));
         tvEclColLatitude.setCellValueFactory(new PropertyValueFactory<>("formattedLatitude"));
         tvEclColLatSpeed.setCellValueFactory(new PropertyValueFactory<>("formattedLatSpeed"));
         tvEquColRa.setCellValueFactory(new PropertyValueFactory<>("formattedRightAscension"));
         tvEquColRaSpeed.setCellValueFactory(new PropertyValueFactory<>("formattedRaSpeed"));
         tvEquColDecl.setCellValueFactory(new PropertyValueFactory<>("formattedDeclination"));
         tvEquColDeclSpeed.setCellValueFactory(new PropertyValueFactory<>("formattedDeclSpeed"));
         tvHorColAzimuth.setCellValueFactory(new PropertyValueFactory<>("formattedAzimuth"));
         tvHorColAltitude.setCellValueFactory(new PropertyValueFactory<>("formattedAltitude"));
         tvDistColDistance.setCellValueFactory(new PropertyValueFactory<>("formattedDistance"));
         tvDistColDistSpeed.setCellValueFactory(new PropertyValueFactory<>("formattedDistSpeed"));
         tableView.getItems().add(presPos);
      }
      return tableView;
   }

   @SuppressWarnings("unchecked")
   private void createTVMundaneData() {
      tvMundaneData = new TableView();
      tvMundaneData.setPrefHeight(TV_MUNDOBJECTS_HEIGHT);
      tvMundaneData.setPrefWidth(HALF_WIDTH);

      tvMundColName = new TableColumn<>();
      tvMundColLongitude = new TableColumn<>(Rosetta.getText("ui.charts.data.tvmundane.longitude"));
      tvMundColSignGlyph = new TableColumn<>();
      tvMundColRa = new TableColumn<>(Rosetta.getText("ui.charts.data.tvmundane.ra"));
      tvMundColDecl = new TableColumn<>(Rosetta.getText("ui.charts.data.tvmundane.decl"));
      tvMundColAzimuth = new TableColumn<>(Rosetta.getText("ui.charts.data.tvmundane.azimuth"));
      tvMundColAltitude = new TableColumn<>(Rosetta.getText("ui.charts.data.tvmundane.altitude"));

      tvMundColSignGlyph.setStyle(glyphFont);

      tvMundaneData.getColumns().add(tvMundColName);
      tvMundaneData.getColumns().add(tvMundColLongitude);
      tvMundaneData.getColumns().add(tvMundColSignGlyph);
      tvMundaneData.getColumns().add(tvMundColRa);
      tvMundaneData.getColumns().add(tvMundColDecl);
      tvMundaneData.getColumns().add(tvMundColAzimuth);
      tvMundaneData.getColumns().add(tvMundColAltitude);

      handlePresMundPos(Rosetta.getText("ui.shared.mc"), calculatedChart.getMundPoints().getMc());
      handlePresMundPos(Rosetta.getText("ui.shared.asc"), calculatedChart.getMundPoints().getAsc());
      final List<IPosition> cusps = calculatedChart.getMundPoints().getCusps();
      int count = cusps.size() - 1;  // index for houses runs from 1..12
      for (int i = 1; i <= count; i++) {
         handlePresMundPos(Integer.toString(i), cusps.get(i));
      }
      handlePresMundPos(Rosetta.getText("ui.shared.vertex"), calculatedChart.getMundPoints().getVertex());
      handlePresMundPos(Rosetta.getText("ui.shared.eastpoint"), calculatedChart.getMundPoints().getEastPoint());

   }

   @SuppressWarnings("unchecked")
   private void handlePresMundPos(@NotNull final String name, @NotNull final IPosition pos) {
      PresentableMundanePosition presMundPos = new PresentableMundanePosition(name, pos);
      tvMundColName.setCellValueFactory(new PropertyValueFactory<>("name"));
      tvMundColLongitude.setCellValueFactory(new PropertyValueFactory<>("formattedLongitude"));
      tvMundColSignGlyph.setCellValueFactory(new PropertyValueFactory<>("signGlyph"));
      tvMundColRa.setCellValueFactory(new PropertyValueFactory<>("formattedRa"));
      tvMundColDecl.setCellValueFactory(new PropertyValueFactory<>("formattedDeclination"));
      tvMundColAzimuth.setCellValueFactory(new PropertyValueFactory<>("formattedAzimuth"));
      tvMundColAltitude.setCellValueFactory(new PropertyValueFactory<>("formattedAltitude"));
      tvMundaneData.getItems().add(presMundPos);
   }

   private Pane createPaneData() {
      Pane pane = new Pane();
      pane.setPrefHeight(LEGENDA_HEIGHT);
      pane.setPrefWidth(HALF_WIDTH);
      pane.getChildren().add(createDataOverview());
      return pane;
   }

   private VBox createDataOverview() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(HALF_WIDTH);
      vBox.setPrefHeight(TV_MUNDOBJECTS_HEIGHT);
      vBox.getChildren().add(0, new LabelBuilder("").setText(fullChartInputData.getChartMetaData().getName()).build());
      vBox.getChildren().add(1, new LabelBuilder("").setText(fullChartInputData.getChartMetaData().getDescription()).build());
      vBox.getChildren().add(2, new LabelBuilder(fullChartInputData.getChartMetaData().getChartType().getNameForRB()).build());
      vBox.getChildren().add(3, new LabelBuilder(fullChartInputData.getChartMetaData().getRating().getNameForRB()).build());
      vBox.getChildren().add(4, new LabelBuilder("").setText(fullChartInputData.getChartMetaData().getDataInput()).build());
      return vBox;
   }

   private Pane createPaneBtnBar() {
      Pane pane = new Pane();
      pane.setPrefHeight(BUTTONBAR_HEIGHT);
      pane.setPrefWidth(HALF_WIDTH);
      pane.getChildren().add(createBtnBar());
      return pane;
   }

   private ButtonBar createBtnBar() {
      ButtonBar btnBar = new ButtonBar();
      Button helpBtn = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      Button exitBtn = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
      helpBtn.setOnAction(click -> onHelp());
      exitBtn.setOnAction(click -> stage.close());
      btnBar.getButtons().add(helpBtn);
      btnBar.getButtons().add(exitBtn);
      return btnBar;
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.chartsdata.title"), Rosetta.getHelpText("help.chartsdata.content"));
   }

}
