/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.ConfiguredAspect;
import com.radixpro.enigma.references.AspectCategory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Overview of aspects in a configuration.
 */
public class AspectsInConfig {

   private static final Logger LOG = Logger.getLogger(AspectsInConfig.class);

   private final Rosetta rosetta;


   public AspectsInConfig() {
      this.rosetta = Rosetta.getRosetta();
   }

   /**
    * Create presentable properties for aspects.
    *
    * @param aspects Aspects as defined in the configuration.
    * @return The resulting presentable properties.
    */
   public List<PresentableProperty> constructProperties(@NotNull final List<ConfiguredAspect> aspects) {
      List<PresentableProperty> presentableProperties = new ArrayList<>();
      AspectCategory category;
      String nameText;
      StringBuilder majorAspectsAsText = new StringBuilder();
      StringBuilder minorAspectsAsText = new StringBuilder();
      StringBuilder microAspectsAsText = new StringBuilder();
      StringBuilder declinationAspectsAsText = new StringBuilder();
      for (ConfiguredAspect aspect : aspects) {
         category = aspect.getAspect().getAspectCategory();
         nameText = rosetta.getText(aspect.getAspect().getFullRbId()) + " ";
         switch (category.getId()) {
            case 0 -> majorAspectsAsText.append(nameText);
            case 1 -> minorAspectsAsText.append(nameText);
            case 2 -> microAspectsAsText.append(nameText);
            case 3 -> declinationAspectsAsText.append(nameText);
            default -> LOG.error("Invalid category for aspect body while constructing details of configuration." +
                  "Received category with Id: " + category + ". Aspect was ignored.");
         }
      }
      if (majorAspectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText("aspectcat.major"), majorAspectsAsText.toString()));
      if (minorAspectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText("aspectcat.minor"), minorAspectsAsText.toString()));
      if (microAspectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText("aspectcat.micro"), microAspectsAsText.toString()));
      if (declinationAspectsAsText.length() > 0) presentableProperties.add(new PresentableProperty(
            rosetta.getText("aspectcat.declination"), declinationAspectsAsText.toString()));
      return presentableProperties;
   }

}
