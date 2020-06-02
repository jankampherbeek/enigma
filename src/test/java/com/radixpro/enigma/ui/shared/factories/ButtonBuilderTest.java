/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Button;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class ButtonBuilderTest {

   private final String text = "A button";

   @Test
   public void onlyConstructor() {
      Button button = new ButtonBuilder(text).build();
      assertEquals(text, button.getText());
   }

   @Test
   public void setDisabled() {
      Button button = new ButtonBuilder(text).setDisabled(true).build();
      assertTrue(button.isDisabled());
   }
}