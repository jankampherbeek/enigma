/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
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
public class LabelFactoryTest {

   private final String text = "labelText";
   private final String styleClass = "styleClassName";
   private final double layoutX = 2.0;
   private final double layoutY = 30.5;
   private final double width = 250.0;

   @Test
   public void createLabelTextXYStyle() {
      Label result = LabelFactory.createLabel(text, layoutX, layoutY, styleClass);
      Label lblResult = result;
      assertEquals(text, lblResult.getText());
      assertEquals(layoutX, lblResult.getLayoutX(), DELTA_8_POS);
      assertEquals(layoutY, lblResult.getLayoutY(), DELTA_8_POS);
      assertTrue(lblResult.getStyleClass().contains(styleClass));
   }

   @Test
   public void testCreateLabelTextXY() {
      Label result = LabelFactory.createLabel(text, layoutX, layoutY);
      Label lblResult = result;
      assertEquals(text, lblResult.getText());
      assertEquals(layoutX, lblResult.getLayoutX(), DELTA_8_POS);
      assertEquals(layoutY, lblResult.getLayoutY(), DELTA_8_POS);
   }

   @Test
   public void testCreateLabelTextStyleWidth() {
      Label result = LabelFactory.createLabel(text, styleClass, width);
      Label lblResult = result;
      assertEquals(text, lblResult.getText());
      assertTrue(lblResult.getStyleClass().contains(styleClass));
      assertEquals(width, lblResult.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void testCreateLabelTextStyle() {
      Label result = LabelFactory.createLabel(text, styleClass);
      Label lblResult = result;
      assertEquals(text, lblResult.getText());
      assertTrue(lblResult.getStyleClass().contains(styleClass));
   }

   @Test
   public void testCreateLabelText() {
      Label result = LabelFactory.createLabel(text);
      Label lblResult = result;
      assertEquals(text, lblResult.getText());
   }


}