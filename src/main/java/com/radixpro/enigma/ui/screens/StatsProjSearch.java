/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.AppScope;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class StatsProjSearch {

   private static final double WIDTH = 600.0;
   private final AppScope scope;

   public StatsProjSearch(@NotNull final AppScope scope) {
      this.scope = scope;
   }

   public void show() {
      Stage stage = new Stage();
      stage.setWidth(WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getChildren().addAll(createTitle(), createButtonBar());
      return vBox;
   }

   private Label createTitle() {
      return new Label("Stats Search");
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button cancelBtn = new Button("Cancel");
      buttonBar.getButtons().addAll(cancelBtn);
      return buttonBar;
   }

}
