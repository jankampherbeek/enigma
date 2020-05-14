/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens.helpers;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import com.radixpro.enigma.xchg.domain.CelObjectCategory;
import com.radixpro.enigma.xchg.domain.config.ConfiguredCelObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Overview of celestial objects in a configuration.
 */
public class CelObjectsInConfig {

   private static final Logger LOG = Logger.getLogger(CelObjectsInConfig.class);
   private final Rosetta rosetta;

   /**
    * Constructor is used by factory for ConfigDetails.
    *
    * @param rosetta instance of Rosetta(i18n).
    */
   public CelObjectsInConfig(final Rosetta rosetta) {
      this.rosetta = checkNotNull(rosetta);
   }

   /**
    * Create presentable properties for celstial objects.
    *
    * @param celObjects Celestial objects as defined in the configuration.
    * @return The resulting presentable properties.
    */
   public List<PresentableProperty> constructProperties(final List<ConfiguredCelObject> celObjects) {
      checkNotNull(celObjects);
      List<PresentableProperty> presentableProperties = new ArrayList<>();
      StringBuilder classicCelObjectsAsText = new StringBuilder();
      StringBuilder modernCelObjectsAsText = new StringBuilder();
      StringBuilder extraplutCelObjectsAsText = new StringBuilder();
      StringBuilder asteroidCelObjectsAsText = new StringBuilder();
      StringBuilder centaurCelObjectsAsText = new StringBuilder();
      StringBuilder intersectionsCelObjectsAsText = new StringBuilder();
      StringBuilder hypothetsCelObjectsAsText = new StringBuilder();
      int category;
      String nameText;
      for (ConfiguredCelObject celObject : celObjects) {
         category = celObject.getCelObject().getCategory().getId();
         nameText = rosetta.getText(celObject.getCelObject().getNameForRB()) + " ";
         switch (category) {
            case 1:
               classicCelObjectsAsText.append(nameText);
               break;
            case 2:
               modernCelObjectsAsText.append(nameText);
               break;
            case 3:
               extraplutCelObjectsAsText.append(nameText);
               break;
            case 4:
               asteroidCelObjectsAsText.append(nameText);
               break;
            case 5:
               centaurCelObjectsAsText.append(nameText);
               break;
            case 6:
               intersectionsCelObjectsAsText.append(nameText);
               break;
            case 7:
               hypothetsCelObjectsAsText.append(nameText);
               break;
            default:
               LOG.error("Invalid category for celestial body while constructing details of configuration." +
                     "Received category with Id : " + category + ". Celestial object was ignored.");
         }
      }
      if (classicCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.CLASSICS.getNameForRB()), classicCelObjectsAsText.toString()));
      if (modernCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.MODERN.getNameForRB()), modernCelObjectsAsText.toString()));
      if (extraplutCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.EXTRA_PLUT.getNameForRB()), extraplutCelObjectsAsText.toString()));
      if (asteroidCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.ASTEROIDS.getNameForRB()), asteroidCelObjectsAsText.toString()));
      if (centaurCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.CENTAURS.getNameForRB()), centaurCelObjectsAsText.toString()));
      if (intersectionsCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.INTERSECTIONS.getNameForRB()), intersectionsCelObjectsAsText.toString()));
      if (hypothetsCelObjectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText(CelObjectCategory.HYPOTHETS.getNameForRB()), hypothetsCelObjectsAsText.toString()));
      return presentableProperties;
   }

}
