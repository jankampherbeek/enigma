/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens.helpers;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableProperty;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Set of properties from a configuration, in the form of preentable properties.
 */
public class PropertiesForConfig {

   private final Configuration config;
   private final CelObjectsInConfig celObjectsInConfig;
   private final AspectsInConfig aspectsInConfig;
   private final Rosetta rosetta;


   public PropertiesForConfig(Configuration config, CelObjectsInConfig celObjectsInConfig,
                              AspectsInConfig aspectsInConfig, Rosetta rosetta) {
      this.config = checkNotNull(config);
      this.celObjectsInConfig = checkNotNull(celObjectsInConfig);
      this.aspectsInConfig = checkNotNull(aspectsInConfig);
      this.rosetta = checkNotNull(rosetta);
   }

   public List<PresentableProperty> getProperties() {
      return createProperties();
   }

   private List<PresentableProperty> createProperties() {
      List<PresentableProperty> properties = new ArrayList<>();
      properties.add(new PresentableProperty(rosetta.getText("ui.general.name"), config.getName()));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.description"), config.getDescription()));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.housesystem"), rosetta.getText(config.getAstronConfiguration().getHouseSystem().getNameForRB())));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.observerposition"), rosetta.getText(config.getAstronConfiguration().getObserverPosition().getNameForRB())));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.eclipticprojection"), rosetta.getText(config.getAstronConfiguration().getEclipticProjection().getNameForRB())));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.ayanamsha"), rosetta.getText(config.getAstronConfiguration().getAyanamsha().getNameForRB())));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.celobjects"), ""));
      properties.addAll(celObjectsInConfig.constructProperties(config.getAstronConfiguration().getCelObjects()));
      // add aspects
      properties.add(new PresentableProperty(rosetta.getText("ui.general.aspects"), ""));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.aspect.orbstructure"), config.getDelinConfiguration().getAspectConfiguration().getOrbStructure().name()));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.aspect.baseorb"), Double.toString(config.getDelinConfiguration().getAspectConfiguration().getBaseOrb())));
      properties.add(new PresentableProperty(rosetta.getText("ui.general.aspect.drawinoutgoing"), config.getDelinConfiguration().getAspectConfiguration().isDrawInOutGoing() ? "Yes" : "No"));
      properties.addAll(aspectsInConfig.constructProperties(config.getDelinConfiguration().getAspectConfiguration().getAspects()));
      return properties;
   }
}
