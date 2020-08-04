/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.screens;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.shared.common.SessionState;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import static com.radixpro.enigma.ui.shared.UiDictionary.BUTTONBAR_HEIGHT;
import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_WIDTH;

public abstract class InputScreen {

   protected Stage stage;
   protected Rosetta rosetta;
   protected SessionState state;

   public InputScreen() {
      state = SessionState.getInstance();
      stage = new Stage();
      stage.setWidth(INPUT_WIDTH);
      rosetta = Rosetta.getRosetta();
   }

   protected Pane createPaneBtnBar() {
      Pane pane = new Pane();
      pane.setPrefHeight(BUTTONBAR_HEIGHT);
      pane.setPrefWidth(INPUT_WIDTH);
      pane.getChildren().add(createBtnBar());
      return pane;
   }

   protected void popupCheckErrors() {
      Label label = new Label("Please correct input errors. \nPress ESC to close.");  // TODO use resource bundle
      label.setMinWidth(150);
      label.setMinHeight(50);
      label.setStyle(" -fx-background-color: yellow;");
      Popup popup = new Popup();
      popup.getContent().add(label);
      popup.setHideOnEscape(true);
      popup.show(stage);
   }

   protected void onCancel() {
      stage.close();
   }

   protected abstract ButtonBar createBtnBar();

}
