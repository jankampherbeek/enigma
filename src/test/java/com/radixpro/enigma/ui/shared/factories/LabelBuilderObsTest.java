/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class LabelBuilderObsTest {

   private final String rbKey = "ui.shared.no";
   private final Pos alignment = Pos.BASELINE_CENTER;

   @Test
   public void constructOnly() throws Exception {
      Label label = new LabelBuilderObs(rbKey).build();
      assertEquals("No", label.getText());
   }

   @Test
   public void setPrefWidth() {
      double prefWidth = 333.3;
      Label label = new LabelBuilderObs(rbKey).setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, label.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setPrefHeight() {
      double prefHeight = 222.2;
      Label label = new LabelBuilderObs(rbKey).setPrefHeight(prefHeight).build();
      assertEquals(prefHeight, label.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setLayoutX() {
      double layoutX = 150.0;
      Label label = new LabelBuilderObs(rbKey).setLayoutX(layoutX).build();
      assertEquals(layoutX, label.getLayoutX(), DELTA_8_POS);
   }

   @Test
   public void setLayoutY() {
      double layoutY = 88.88;
      Label label = new LabelBuilderObs(rbKey).setLayoutY(layoutY).build();
      assertEquals(layoutY, label.getLayoutY(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      String styleClass = "myStyle";
      Label label = new LabelBuilderObs(rbKey).setStyleClass(styleClass).build();
      assertTrue(label.getStyleClass().contains(styleClass));
   }

   @Test
   public void setAlignment() {
      Label label = new LabelBuilderObs(rbKey).setAlignment(alignment).build();
      assertEquals(alignment, label.getAlignment());
   }

}