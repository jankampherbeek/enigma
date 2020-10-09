/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.HouseSystems;
import com.radixpro.enigma.references.ObserverPositions;
import com.radixpro.enigma.ui.creators.ChoiceBoxBuilder;
import com.radixpro.enigma.ui.creators.GridPaneBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * VBox with basic elements of a configuration.
 */
public class BaseConfigInputBlock extends InputBlock {
   private static final double DATA_TEXT_WIDTH = 150.0;
   private static final double DATA_INPUT_WIDTH = 350.0;
   private static final double HEIGHT = 180.0;
   private ChoiceBox choiceBoxHouseSystem;
   private ChoiceBox choiceBoxObserverPos;
   private ChoiceBox choiceBoxEclipticProj;
   private ChoiceBox choiceBoxAyanamsha;

   public BaseConfigInputBlock() {
      super();
   }

   public GridPane getGridPane() {
      initialize();
      return createGridPane();
   }

   @Override
   protected void initialize() {
      createChoiceBoxes();
   }

   private void createChoiceBoxes() {
      choiceBoxHouseSystem = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(HouseSystems.getObservableList()).build();
      choiceBoxHouseSystem.getSelectionModel().select(0);
      choiceBoxObserverPos = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(ObserverPositions.getObservableList()).build();
      choiceBoxObserverPos.getSelectionModel().select(0);
      choiceBoxEclipticProj = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(EclipticProjections.getObservableList()).build();
      choiceBoxEclipticProj.getSelectionModel().select(0);
      choiceBoxEclipticProj.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> onEclipticChange());
      choiceBoxAyanamsha = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(Ayanamshas.getObservableList()).build();
      choiceBoxAyanamsha.getSelectionModel().select(0);
      choiceBoxAyanamsha.setDisable(true);
   }

   private GridPane createGridPane() {
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(HEIGHT).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).build();
      gridPane.add(createPaneSubTitle(), 0, 0, 2, 1);
      gridPane.add(new LabelBuilder("ui.general.housesystem").setPrefWidth(DATA_TEXT_WIDTH).build(), 0, 1, 1, 1);
      gridPane.add(choiceBoxHouseSystem, 1, 1, 1, 1);
      gridPane.add(new LabelBuilder("ui.general.observerposition").setPrefWidth(DATA_TEXT_WIDTH).build(), 0, 2, 1, 1);
      gridPane.add(choiceBoxObserverPos, 1, 2, 1, 1);
      gridPane.add(new LabelBuilder("ui.general.eclipticprojection").setPrefWidth(DATA_TEXT_WIDTH).build(), 0, 3, 1, 1);
      gridPane.add(choiceBoxEclipticProj, 1, 3, 1, 1);
      gridPane.add(new LabelBuilder("ui.general.ayanamsha").setPrefWidth(DATA_TEXT_WIDTH).build(), 0, 4, 1, 1);
      gridPane.add(choiceBoxAyanamsha, 1, 4, 1, 1);
      return gridPane;
   }

   private Pane createPaneSubTitle() {
      Label label = new LabelBuilder("ui.stats.baseconfig.title").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      return new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(label).build();
   }

   public HouseSystems getHouseSystem() {
      final IndexMappingsList indexMappings = HouseSystems.getIndexMappings();
      final int index = choiceBoxHouseSystem.getSelectionModel().getSelectedIndex();
      return HouseSystems.getSystemForId(indexMappings.getEnumIdForSequenceId(index));
   }

   public ObserverPositions getObserverPosition() {
      final IndexMappingsList indexMappings = ObserverPositions.getIndexMappings();
      final int index = choiceBoxObserverPos.getSelectionModel().getSelectedIndex();
      return ObserverPositions.getObserverPositionForId(indexMappings.getEnumIdForSequenceId(index));
   }

   public EclipticProjections getEclipticProjection() {
      final IndexMappingsList indexMappings = EclipticProjections.getIndexMappings();
      final int index = choiceBoxEclipticProj.getSelectionModel().getSelectedIndex();
      return EclipticProjections.getProjectionForId(indexMappings.getEnumIdForSequenceId(index));
   }

   public Ayanamshas getAyanamsha() {
      final IndexMappingsList indexMappings = Ayanamshas.getIndexMappings();
      final int index = choiceBoxAyanamsha.getSelectionModel().getSelectedIndex();
      choiceBoxAyanamsha.setDisable(true);
      return Ayanamshas.getAyanamshaForId(indexMappings.getEnumIdForSequenceId(index));
   }

   private void onEclipticChange() {
      int selectedIndex = choiceBoxEclipticProj.getSelectionModel().getSelectedIndex();
      if (selectedIndex >= 0) {
         int eclipticProjectionSelectedIndex = EclipticProjections.getIndexMappings().getEnumIdForSequenceId(selectedIndex);
         choiceBoxAyanamsha.setDisable(eclipticProjectionSelectedIndex != EclipticProjections.SIDEREAL.getId());
      }
   }


}
