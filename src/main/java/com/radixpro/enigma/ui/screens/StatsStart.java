/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.blocks.StatsDataBlock;
import com.radixpro.enigma.ui.screens.blocks.StatsProjBlock;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class StatsStart {

   private static final double PROJ_HEIGHT = 200.0;
   private static final String KEY_PROJDIR = "projdir";
   private final Rosetta rosetta;
   private final PersistedPropertyApi propApi;
   private final DirectoryChooser dirChooser;
   private final StatsDataBlock dataBlock;
   private final StatsProjBlock projBlock;
   private boolean projDirDefined;
   private String fullPathProjDir;
   private Stage stage;
   private Label lblPageTitle;
   private Label lblSubTitleNoProjdir;
   private Label lblDefineProjDir;
   private Button btnDefineProjDir;
   private Button btnInputData;
   private Pane panePageTitle;
   private Pane paneProjects;
   private Pane paneBtnBar;


   public StatsStart(@NotNull final Rosetta rosetta, @NotNull final StatsDataBlock dataBlock, @NotNull final StatsProjBlock projBlock,
                     @NotNull final PersistedPropertyApi propApi, @NotNull final DirectoryChooser dirChooser) {
      this.rosetta = rosetta;
      this.dataBlock = dataBlock;
      this.projBlock = projBlock;
      this.propApi = propApi;
      this.dirChooser = dirChooser;
   }

   public void show() {
      stage = new Stage();
      stage.setWidth(START_WIDTH);
      List<Property> properties = propApi.read(KEY_PROJDIR);
      if (0 == properties.size()) {
         fullPathProjDir = "";
         projDirDefined = false;
      } else {
         fullPathProjDir = properties.get(0).getValue();
         projDirDefined = true;
      }
      defineLeafs();
      definePanes();
      defineStructure();

      if (projDirDefined) stage.setScene(new Scene(createVBox()));
      else stage.setScene(new Scene(createVBoxDefineProjDir()));
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblPageTitle = new LabelBuilder("ui.stats.start.pagetitle").setStyleClass("titletext").setPrefWidth(START_WIDTH).build();
      lblSubTitleNoProjdir = new LabelBuilder("ui.stats.start.noprojdirtitle").setStyleClass("subtitletext").setPrefWidth(START_WIDTH).build();
      lblDefineProjDir = new LabelBuilder("ui.stats.start.noprojdirtext").setPrefWidth(START_WIDTH).build();
      btnDefineProjDir = new ButtonBuilder("ui.stats.start.noprojdirbtn").setDisabled(false).build();
      btnInputData = new ButtonBuilder("ui.stats.start.inputdatabtn").setDisabled(false).build();
   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(START_WIDTH).setStyleClass("titlepane").build();
      paneProjects = new PaneBuilder().setHeight(PROJ_HEIGHT).setWidth(START_WIDTH).build();
      paneBtnBar = new PaneBuilder().setHeight(BUTTONBAR_HEIGHT).setWidth(START_WIDTH).setChildren(createBtnBar()).build();
   }

   private void defineStructure() {
      btnDefineProjDir.setOnAction(click -> onDefineProjDir());

      panePageTitle.getChildren().add(lblPageTitle);
      if (fullPathProjDir.isBlank()) {
//         paneSubTitleProjects.getChildren().add(lblSubTitleNoProjdir);
         paneProjects.getChildren().add(createVBoxDefineProjDir());
      } else {
//         paneSubTitleProjects.getChildren().add(lblSubTitleProjects);
         // define PaneProjects with overview of projects
      }

   }

   private VBox createVBoxDefineProjDir() {
      return new VBoxBuilder().setPadding(GAP).setWidth(START_WIDTH).setChildren(lblDefineProjDir, btnDefineProjDir).build();
   }

   private VBox createVBox() {
      return new VBoxBuilder().setWidth(START_WIDTH).setChildren(createMenuBar(), panePageTitle, dataBlock.getVBox(), projBlock.getVBox(), paneBtnBar).build();
   }

   private MenuBar createMenuBar() {
      Menu menuGeneral = new Menu(rosetta.getText("menu.general"));
      MenuItem miExit = new MenuItem(rosetta.getText("menu.general.exit"));
      miExit.setOnAction(e -> stage.close());
      menuGeneral.getItems().add(miExit);
      Menu menuData = new Menu(rosetta.getText("menu.stats.data"));
      MenuItem miNewData = new MenuItem(rosetta.getText("menu.stats.data.new"));
      miNewData.setOnAction(click -> dataBlock.onNew());
      MenuItem miSearchData = new MenuItem(rosetta.getText("menu.stats.data.search"));
      miSearchData.setOnAction((click -> dataBlock.onSearch()));
      MenuItem miDetailsData = new MenuItem(rosetta.getText("menu.stats.data.details"));
      menuData.getItems().addAll(miNewData, miSearchData, miDetailsData);
      Menu menuProjects = new Menu(rosetta.getText("menu.stats.projects"));
      MenuItem miNewProject = new MenuItem(rosetta.getText("menu.stats.projects.new"));
      MenuItem miSearchProject = new MenuItem(rosetta.getText("menu.stats.projects.search"));
      MenuItem miEditProject = new MenuItem(rosetta.getText("menu.stats.projects.edit"));
      MenuItem miOpenProject = new MenuItem(rosetta.getText("menu.stats.projects.open"));
      menuProjects.getItems().addAll(miNewProject, miSearchProject, miEditProject, miOpenProject);
      Menu menuHelp = new Menu(rosetta.getText("menu.general.help"));
      MenuItem miShowHelp = new MenuItem(rosetta.getText("menu.general.help.showhelp"));
      menuHelp.getItems().add(miShowHelp);
      MenuBar menuBar = new MenuBar();
      menuBar.getMenus().addAll(menuGeneral, menuData, menuProjects, menuHelp);
      return menuBar;
   }

   private ButtonBar createBtnBar() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      Button btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
      return new ButtonBarBuilder().setButtons(btnHelp, btnExit).build();
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


   private void onSearch() {
      new StatsProjSearch();
   }


}
