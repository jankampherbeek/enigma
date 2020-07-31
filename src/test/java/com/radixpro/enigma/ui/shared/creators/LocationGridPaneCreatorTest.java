/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;

import com.radixpro.enigma.testsupport.JfxTestRunner;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(JfxTestRunner.class)
public class LocationGridPaneCreatorTest {

   private final TextField tfLocationName = new TextField();
   private final TextField tfLocationLongitude = new TextField();
   private final TextField tfLocationLatitude = new TextField();
   private final ChoiceBox cbEastWest = new ChoiceBox<>();
   private final ChoiceBox cbNorthSouth = new ChoiceBox<>();
   private LocationGridPaneCreator creator;

   @Before
   public void setUp() {
      creator = new LocationGridPaneCreator();
   }

   @Test
   public void createGridPane() {
      final GridPane gridPane = creator.createGridPane(tfLocationName, tfLocationLongitude, tfLocationLatitude, cbEastWest, cbNorthSouth);
      final ObservableList<Node> children = gridPane.getChildren();
      assertTrue(children.contains(tfLocationName));
      assertTrue(children.contains(tfLocationLongitude));
      assertTrue(children.contains(tfLocationLatitude));
      assertTrue(children.contains(cbEastWest));
      assertTrue(children.contains(cbNorthSouth));
   }
}