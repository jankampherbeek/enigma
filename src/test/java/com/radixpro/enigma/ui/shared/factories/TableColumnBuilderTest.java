/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JfxTestRunner.class)
public class TableColumnBuilderTest {

   private final String text = "column name";
   private PropertyValueFactory cellValueFactory = new PropertyValueFactory<>("someValue");

   @Test
   public void setText() {
      TableColumn col = new TableColumnBuilder().setText(text).build();
      assertEquals(text, col.getText());
   }

   @Test
   public void setCellValueFactory() {
      TableColumn col = new TableColumnBuilder().setCellValueFactory(cellValueFactory).build();
      assertEquals(cellValueFactory, col.getCellValueFactory());
   }
}