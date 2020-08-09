/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.GridPaneBuilder;
import javafx.scene.layout.GridPane;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class GridPaneBuilderTest {

   @Test
   public void setPrefHeight() {
      double prefHeight = 333.33;
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(prefHeight).build();
      assertEquals(prefHeight, gridPane.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setPrefWidth() {
      double prefWidth = 555.55;
      GridPane gridPane = new GridPaneBuilder().setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, gridPane.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setPadding() {
      double padding = 5.5;
      GridPane gridPane = new GridPaneBuilder().setPadding(padding).build();
      assertEquals(padding, gridPane.getPadding().getBottom(), DELTA_8_POS);
   }

   @Test
   public void setVGap() {
      double vGap = 4.8;
      GridPane gridPane = new GridPaneBuilder().setVGap(vGap).build();
      assertEquals(vGap, gridPane.getVgap(), DELTA_8_POS);
   }

   @Test
   public void setHGap() {
      double hGap = 5.0;
      GridPane gridPane = new GridPaneBuilder().setHGap(hGap).build();
      assertEquals(hGap, gridPane.getHgap(), DELTA_8_POS);
   }
}