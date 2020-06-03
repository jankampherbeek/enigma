/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import org.controlsfx.control.CheckComboBox;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class CheckComboBoxBuilderTest {

   private final double prefWidth = 339.8;

   @Test
   public void setPrefWidth() {
      CheckComboBox box = new CheckComboBoxBuilder().setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, box.getPrefWidth(), DELTA_8_POS);
   }
}