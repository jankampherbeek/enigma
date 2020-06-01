/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.layout.Pane;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class PaneFactoryTest {

   private final double height = 220.5;
   private final double width = 111.1;
   private final String styleClass = "paneStyle";

   @Test
   public void createPaneHeightWidthStyle() {
      Pane result = PaneFactory.createPane(height, width, styleClass);
      Pane paneResult = result;
      assertEquals(height, paneResult.getPrefHeight(), DELTA_8_POS);
      assertEquals(width, paneResult.getPrefWidth(), DELTA_8_POS);
      assertTrue(paneResult.getStyleClass().contains(styleClass));
   }

   @Test
   public void testCreatePaneHeightWidth() {
      Pane result = PaneFactory.createPane(height, width);
      Pane paneResult = result;
      assertEquals(height, paneResult.getPrefHeight(), DELTA_8_POS);
      assertEquals(width, paneResult.getPrefWidth(), DELTA_8_POS);
   }
}