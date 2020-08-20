/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.ui.creators.ButtonBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class StatsStart {

   private static final double WIDTH = 600.0;
   private static final double PROJ_HEIGHT = 200.0;
   private final static String KEY_PROJDIR = "projdir";
   private final PersistedPropertyApi propApi;
   private final DirectoryChooser dirChooser;
   private final StatsInputData statsInputData;
   private Button btnDefineProjDir;
   private Button btnInputData;
   private String fullPathProjDir;
   private Stage stage;
   private Label lblPageTitle;
   private Label lblSubTitleProjects;
   private Label lblSubTitleNoProjdir;
   private Label lblDefineProjDir;
   private Pane panePageTitle;
   private Pane paneSubTitleProjects;
   private Pane paneProjects;


   public StatsStart(final PersistedPropertyApi propApi, final DirectoryChooser dirChooser, final StatsInputData statsInputData) {
      this.propApi = checkNotNull(propApi);
      this.dirChooser = checkNotNull(dirChooser);
      this.statsInputData = statsInputData;
   }

   public void show() {
      stage = new Stage();
      stage.setWidth(WIDTH);
      List<Property> properties = propApi.read(KEY_PROJDIR);
      fullPathProjDir = (0 == properties.size() ? "" : properties.get(0).getValue());
      defineLeafs();
      definePanes();
      defineStructure();

      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblPageTitle = new LabelBuilder("ui.stats.start.pagetitle").setStyleClass("titletext").setPrefWidth(INPUT_WIDTH).build();
      lblSubTitleProjects = new LabelBuilder("ui.stats.start.projtitle").setStyleClass("subtitletext").setPrefWidth(INPUT_WIDTH).build();
      lblSubTitleNoProjdir = new LabelBuilder("ui.stats.start.noprojdirtitle").setStyleClass("subtitletext").setPrefWidth(INPUT_WIDTH).build();
      lblDefineProjDir = new LabelBuilder("ui.stats.start.noprojdirtext").setPrefWidth(INPUT_WIDTH).build();
      btnDefineProjDir = new ButtonBuilder("ui.stats.start.noprojdirbtn").setDisabled(false).build();
      btnInputData = new ButtonBuilder("ui.stats.start.inputdatabtn").setDisabled(false).build();

   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("titlepane").build();
      paneSubTitleProjects = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).build();
      paneProjects = new PaneBuilder().setHeight(PROJ_HEIGHT).setWidth(INPUT_WIDTH).build();
   }

   private void defineStructure() {
      btnDefineProjDir.setOnAction(click -> onDefineProjDir());

      panePageTitle.getChildren().add(lblPageTitle);
      if (fullPathProjDir.isEmpty()) {
         paneSubTitleProjects.getChildren().add(lblSubTitleNoProjdir);
         paneProjects.getChildren().add(createVBoxDefineProjDir());
      } else {
         paneSubTitleProjects.getChildren().add(lblSubTitleProjects);
         // define PaneProjects with overview of projects
      }

   }

   private VBox createVBoxDefineProjDir() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getChildren().addAll(lblDefineProjDir, btnDefineProjDir);
      return vBox;
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.getChildren().addAll(panePageTitle, paneSubTitleProjects, paneProjects, createBtnBar());
      return vBox;
   }

   private ButtonBar createBtnBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button exitBtn = new Button("Exit");
      Button searchBtn = new Button("Search project");
      Button newButton = new Button("New project");
      Button editButton = new Button("Edit project");
      Button runButton = new Button("Run test");
      btnInputData.setOnAction(click -> onInputData());
      searchBtn.setOnAction(click -> onSearch());
      buttonBar.getButtons().addAll(exitBtn, searchBtn, newButton, editButton, runButton, btnInputData);
      return buttonBar;
   }

   private void onDefineProjDir() {
      File selectedDirectory = dirChooser.showDialog(stage);
      if (null != selectedDirectory) {
         fullPathProjDir = selectedDirectory.getAbsolutePath();
         Property prop = new Property(KEY_PROJDIR, fullPathProjDir);
         propApi.insert(prop);
         stage.close();
      }

   }

   private String defineProjDir() {
      File selectedDirectory = dirChooser.showDialog(stage);
      if (null != selectedDirectory) return selectedDirectory.getAbsolutePath();
      return "";
   }

   private void onInputData() {
      statsInputData.show();
   }

   private void onSearch() {
      new StatsSearch();
   }


}
