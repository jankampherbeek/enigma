/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JfxTestRunner.class)
public class ButtonFactoryTest {

   @Before
   public void setUp() throws Exception {
   }

   @Test
   public void createButton() {
      Object result = ButtonFactory.createButton("Dummy", false);
      assertFalse(((Button) result).isDisabled());
      assertEquals("Dummy", ((Button) result).getText());

   }
}