/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class ButtonBarBuilderTest {

   private Button button1;
   private Button button2;

   @Before
   public void setUp() throws Exception {
      button1 = new Button("nr. 1");
      button2 = new Button("nr. 2");
   }

   @Test
   public void setButtons() {
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(button1, button2).build();
      assertEquals(2, buttonBar.getButtons().size());
   }
}