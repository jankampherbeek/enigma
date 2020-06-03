/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class TextFieldBuilderTest {

   private final double width = 33.3;
   private final double height = 100.0;
   private final String styleClass = "anotherStyle";
   private final String text = "some text";

   @Test
   public void setHeight() {
      TextField textField = new TextFieldBuilder().setPrefHeight(height).build();
      assertEquals(height, textField.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setWidth() {
      TextField textField = new TextFieldBuilder().setPrefWidth(width).build();
      assertEquals(width, textField.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      TextField textField = new TextFieldBuilder().setStyleClass(styleClass).build();
      assertTrue(textField.getStyleClass().contains(styleClass));
   }

   @Test
   public void setText() {
      TextField textField = new TextFieldBuilder().setText(text).build();
      assertEquals(text, textField.getText());
   }
}