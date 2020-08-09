/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class TextFieldBuilderTest {

   @Test
   public void setHeight() {
      double height = 100.0;
      TextField textField = new TextFieldBuilder().setPrefHeight(height).build();
      assertEquals(height, textField.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setWidth() {
      double width = 33.3;
      TextField textField = new TextFieldBuilder().setPrefWidth(width).build();
      assertEquals(width, textField.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      String styleClass = "anotherStyle";
      TextField textField = new TextFieldBuilder().setStyleClass(styleClass).build();
      assertTrue(textField.getStyleClass().contains(styleClass));
   }

   @Test
   public void setText() {
      String text = "some text";
      TextField textField = new TextFieldBuilder().setText(text).build();
      assertEquals(text, textField.getText());
   }
}