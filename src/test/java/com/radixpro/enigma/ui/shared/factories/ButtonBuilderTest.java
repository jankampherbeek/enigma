/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.ButtonBuilder;
import javafx.scene.control.Button;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JfxTestRunner.class)
public class ButtonBuilderTest {

   private final String text = "A button";
   private final String rbKey = "ui.stats.start.noprojdirbtn";

   @Test
   public void onlyConstructor() {
      Button button = new ButtonBuilder(rbKey).build();
      assertEquals("Define folder", button.getText());
   }

   @Test
   public void setText() {
      Button button = new ButtonBuilder(rbKey).setText(text).build();
      assertEquals(text, button.getText());
   }

   @Test
   public void setDisabled() {
      Button button = new ButtonBuilder(text).build();
      assertFalse(button.isDisabled());
      button = new ButtonBuilder(text).setDisabled(true).build();
      assertTrue(button.isDisabled());
   }

   @Test
   public void setFocusTraversable() {
      Button button = new ButtonBuilder(text).build();
      assertFalse(button.isFocusTraversable());
      button = new ButtonBuilder(text).setFocusTraversable(true).build();
      assertTrue(button.isFocusTraversable());
   }
}