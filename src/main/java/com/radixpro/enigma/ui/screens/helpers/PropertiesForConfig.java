/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of properties from a configuration, in the form of preentable properties.
 */
public class PropertiesForConfig {

   public List<PresentableProperty> getProperties(@NotNull final Configuration config, @NotNull final CelObjectsInConfig celObjectsInConfig,
                                                  @NotNull final AspectsInConfig aspectsInConfig) {
      return createProperties(config, celObjectsInConfig, aspectsInConfig);
   }

   private List<PresentableProperty> createProperties(final Configuration config, final CelObjectsInConfig celObjectsInConfig, final AspectsInConfig aspectsInConfig) {
      List<PresentableProperty> properties = new ArrayList<>();
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.name"), config.getName()));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.description"), config.getDescription()));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.housesystem"), Rosetta.getText(config.getAstronConfiguration().getHouseSystem().getNameForRB())));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.observerposition"), Rosetta.getText(config.getAstronConfiguration().getObserverPosition().getNameForRB())));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.eclipticprojection"), Rosetta.getText(config.getAstronConfiguration().getEclipticProjection().getNameForRB())));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.ayanamsha"), Rosetta.getText(config.getAstronConfiguration().getAyanamsha().getNameForRB())));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.celobjects"), ""));
      properties.addAll(celObjectsInConfig.constructProperties(config.getAstronConfiguration().getCelObjects()));
      // add aspects
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.aspects"), ""));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.aspect.orbstructure"), config.getDelinConfiguration().getAspectConfiguration().getOrbStructure().name()));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.aspect.baseorb"), Double.toString(config.getDelinConfiguration().getAspectConfiguration().getBaseOrb())));
      properties.add(new PresentableProperty(Rosetta.getText("ui.general.aspect.drawinoutgoing"), config.getDelinConfiguration().getAspectConfiguration().isDrawInOutGoing() ? "Yes" : "No"));
      properties.addAll(aspectsInConfig.constructProperties(config.getDelinConfiguration().getAspectConfiguration().getAspects()));
      return properties;
   }

}
