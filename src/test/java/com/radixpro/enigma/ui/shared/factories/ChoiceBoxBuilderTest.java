/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import com.radixpro.enigma.ui.creators.ChoiceBoxBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JfxTestRunner.class)
public class ChoiceBoxBuilderTest {

   private ObservableList<String> items;

   @Before
   public void setUp() {
      final List itemNames = new ArrayList<>();
      itemNames.add("one");
      itemNames.add("two");
      items = FXCollections.observableArrayList(itemNames);
   }

   @Test
   public void setPrefHeight() {
      double prefHeight = 800.0;
      ChoiceBox choiceBox = new ChoiceBoxBuilder().setPrefHeight(prefHeight).build();
      assertEquals(prefHeight, choiceBox.getPrefHeight(), DELTA_8_POS);
   }

   @Test
   public void setPrefWidth() {
      double prefWidth = 300.5;
      ChoiceBox choiceBox = new ChoiceBoxBuilder().setPrefWidth(prefWidth).build();
      assertEquals(prefWidth, choiceBox.getPrefWidth(), DELTA_8_POS);
   }

   @Test
   public void setStyleClass() {
      String styleClass = "myStyle";
      ChoiceBox choiceBox = new ChoiceBoxBuilder().setStyleClass(styleClass).build();
      assertTrue(choiceBox.getStyleClass().contains(styleClass));
   }

   @Test
   public void setItems() {
      ChoiceBox choiceBox = new ChoiceBoxBuilder().setItems(items).build();
      assertEquals(items, choiceBox.getItems());
   }
}