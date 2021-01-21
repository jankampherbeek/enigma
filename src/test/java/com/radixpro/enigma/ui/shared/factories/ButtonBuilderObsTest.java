/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.ButtonBuilderObs;
import javafx.scene.control.Button;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JfxTestRunner.class)
public class ButtonBuilderObsTest {

   private final String rbKey = "ui.shared.btn.ok";
   private final String text = "Also OK";

   @Test
   public void onlyConstructor() {
      Button button = new ButtonBuilderObs(rbKey).build();
      assertEquals("OK", button.getText());
   }

   @Test
   public void setText() {
      Button button = new ButtonBuilderObs(rbKey).setText(text).build();
      assertEquals(text, button.getText());
   }

   @Test
   public void setDisabled() {
      Button button = new ButtonBuilderObs(rbKey).build();
      assertFalse(button.isDisabled());
      button = new ButtonBuilderObs(rbKey).setDisabled(true).build();
      assertTrue(button.isDisabled());
   }

   @Test
   public void setFocusTraversable() {
      Button button = new ButtonBuilderObs(rbKey).build();
      assertFalse(button.isFocusTraversable());
      button = new ButtonBuilderObs(rbKey).setFocusTraversable(true).build();
      assertTrue(button.isFocusTraversable());
   }
}