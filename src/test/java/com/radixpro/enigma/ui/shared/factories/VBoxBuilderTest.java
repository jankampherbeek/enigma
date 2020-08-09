/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.VBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class VBoxBuilderTest {

   private Label label1;
   private Label label2;

   @Before
   public void setUp() {
      label1 = new Label("one");
      label2 = new Label("two");
   }

   @Test
   public void setWidth() {
      double width = 111.1;
      VBox vBox = new VBoxBuilder().setWidth(width).build();
      assertEquals(width, vBox.getPrefWidth(), DELTA_8_POS);
      assertTrue(vBox.getStylesheets().contains("css/enigma.css"));
   }

   @Test
   public void setHeight() {
      double height = 222.2;
      VBox vBox = new VBoxBuilder().setHeight(height).build();
      assertEquals(height, vBox.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setPadding() {
      double padding = 6.0;
      VBox vBox = new VBoxBuilder().setPadding(padding).build();
      assertEquals(padding, vBox.getPadding().getBottom(), DELTA_8_POS);
   }

   @Test
   public void setChildren() {
      VBox vBox = new VBoxBuilder().setChildren(label1, label2).build();
      assertEquals(2, vBox.getChildren().size());
   }
}