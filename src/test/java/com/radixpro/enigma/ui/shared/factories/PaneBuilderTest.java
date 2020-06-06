/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class PaneBuilderTest {

   private Label label1;
   private Label label2;


   @Before
   public void setUp() throws Exception {
      label1 = new Label("first label");
      label2 = new Label("second label");
   }

   @Test
   public void setWidth() {
      double width = 234.567;
      Pane pane = new PaneBuilder().setWidth(width).build();
      assertEquals(width, pane.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setHeight() {
      double height = 123.456;
      Pane pane = new PaneBuilder().setHeight(height).build();
      assertEquals(height, pane.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      String styleClass = "myStyle";
      Pane pane = new PaneBuilder().setStyleClass(styleClass).build();
      assertTrue(pane.getStyleClass().contains(styleClass));
   }

   @Test
   public void setChildren() {
      Pane pane = new PaneBuilder().setChildren(label1, label2).build();
      assertEquals(2, pane.getChildren().size());
   }
}