/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class StageBuilderTest {

   private final double minWidth = 100.0;
   private final double minHeight = 300.0;
   private final String title = "Stage Title";
   private final Modality modality = Modality.WINDOW_MODAL;
   private final Scene scene = new Scene(new VBox());

   @Test
   public void setMinWidth() {
      Platform.runLater(() -> {
         Stage stage = new StageBuilder().setMinWidth(minWidth).build();
         assertEquals(minWidth, stage.getMinWidth(), DELTA_8_POS);
      });
   }

   @Test
   public void setMinHeight() {
      Platform.runLater(() -> {
         Stage stage = new StageBuilder().setMinHeight(minHeight).build();
         assertEquals(minHeight, stage.getMinHeight(), DELTA_8_POS);
      });
   }

   @Test
   public void setTitle() {
      Platform.runLater(() -> {
         Stage stage = new StageBuilder().setTitle(title).build();
         assertEquals(title, stage.getTitle());
      });
   }

   @Test
   public void setModality() {
      Platform.runLater(() -> {
         Stage stage = new StageBuilder().setModality(modality).build();
         assertEquals(modality, stage.getModality());
      });
   }

   @Test
   public void setScene() {
      Platform.runLater(() -> {
         Stage stage = new StageBuilder().setScene(scene).build();
         assertEquals(scene, stage.getScene());
      });
   }
}