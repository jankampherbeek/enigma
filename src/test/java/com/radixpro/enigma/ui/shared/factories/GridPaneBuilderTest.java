/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.layout.GridPane;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class GridPaneBuilderTest {

   private final double prefHeight = 333.33;
   private final double prefWidth = 555.55;
   private final double padding = 5.5;
   private final double hGap = 5.0;
   private final double vGap = 4.8;

   @Test
   public void setPrefHeight() {
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(prefHeight).build();
      assertEquals(prefHeight, gridPane.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setPrefWidth() {
      GridPane gridPane = new GridPaneBuilder().setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, gridPane.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setPadding() {
      GridPane gridPane = new GridPaneBuilder().setPadding(padding).build();
      assertEquals(padding, gridPane.getPadding().getBottom(), DELTA_8_POS);
   }

   @Test
   public void setVGap() {
      GridPane gridPane = new GridPaneBuilder().setVGap(vGap).build();
      assertEquals(vGap, gridPane.getVgap(), DELTA_8_POS);
   }

   @Test
   public void setHGap() {
      GridPane gridPane = new GridPaneBuilder().setHGap(hGap).build();
      assertEquals(hGap, gridPane.getHgap(), DELTA_8_POS);
   }
}