/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.shared.creators.TableViewBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class TableViewBuilderTest {

   private TableColumn col1;
   private TableColumn col2;

   @Before
   public void setUp() throws Exception {
      col1 = new TableColumn();
      col2 = new TableColumn();
   }

   @Test
   public void setPrefHeight() {
      double height = 222.2;
      TableView tv = new TableViewBuilder().setPrefHeight(height).build();
      assertEquals(height, tv.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setPrefWidth() {
      double width = 444.4;
      TableView tv = new TableViewBuilder().setPrefWidth(width).build();
      assertEquals(width, tv.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setColumns() {
      TableView tv = new TableViewBuilder().setColumns(col1, col2).build();
      assertEquals(2, tv.getColumns().size());
   }
}