/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Label;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class LabelBuilderTest {

   private final String text = "Just a text.";
   private final double width = 333.3;
   private final double layoutX = 150.0;
   private final double layoutY = 88.88;
   private final String styleClass = "myStyle";

   @Test
   public void constructOnly() throws Exception {
      Label label = new LabelBuilder(text).build();
      assertEquals(text, label.getText());
   }

   @Test
   public void setPrefWidth() {
      Label label = new LabelBuilder(text).setPrefWidth(width).build();
      assertEquals(width, label.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setLayoutX() {
      Label label = new LabelBuilder(text).setLayoutX(layoutX).build();
      assertEquals(layoutX, label.getLayoutX(), DELTA_8_POS);
   }

   @Test
   public void setLayoutY() {
      Label label = new LabelBuilder(text).setLayoutY(layoutY).build();
      assertEquals(layoutY, label.getLayoutY(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      Label label = new LabelBuilder(text).setStyleClass(styleClass).build();
      assertTrue(label.getStyleClass().contains(styleClass));
   }
}