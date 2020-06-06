/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class HBoxBuilderTest {

   private Label label1;
   private Label label2;

   @Before
   public void setUp() throws Exception {
      label1 = new Label("first");
      label2 = new Label("second");
   }

   @Test
   public void setPrefWidth() {
      double prefWidth = 300.0;
      HBox hBox = new HBoxBuilder().setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, hBox.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setPrefHeight() {
      double prefHeight = 400.0;
      HBox hBox = new HBoxBuilder().setPrefHeight(prefHeight).build();
      assertEquals(prefHeight, hBox.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setChildren() {
      HBox hBox = new HBoxBuilder().setChildren(label1, label2).build();
      assertEquals(2, hBox.getChildren().size());
   }
}