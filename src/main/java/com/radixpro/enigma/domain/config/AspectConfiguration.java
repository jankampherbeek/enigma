/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import com.radixpro.enigma.references.AspectOrbStructures;

import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Configuration for aspects.
 */
public class AspectConfiguration implements Serializable {

   private final double baseOrb;
   private final AspectOrbStructures orbStructure;
   private List<ConfiguredAspect> aspects;
   private final boolean drawInOutGoing;

   /**
    * Constructor defines all members.
    *
    * @param aspects        Supported aspects including orb-percentage. PRE: not null.
    * @param baseOrb        The base orb. Pre: > 0.
    * @param orbStructure   Structure to be used for orbs. PRE: not null.
    * @param drawInOutGoing Indicates if separate glyphs will be used for in- and outgoing aspects.
    */
   public AspectConfiguration(final List<ConfiguredAspect> aspects, final double baseOrb,
                              final AspectOrbStructures orbStructure, final boolean drawInOutGoing) {
      checkArgument(baseOrb > 0.0);
      this.aspects = checkNotNull(aspects);
      this.baseOrb = baseOrb;
      this.orbStructure = checkNotNull(orbStructure);
      this.drawInOutGoing = drawInOutGoing;
   }

   public double getBaseOrb() {
      return baseOrb;
   }

   public AspectOrbStructures getOrbStructure() {
      return orbStructure;
   }

   public List<ConfiguredAspect> getAspects() {
      return aspects;
   }

   public void setAspects(List<ConfiguredAspect> aspects) {
      this.aspects = aspects;
   }

   public boolean isDrawInOutGoing() {
      return drawInOutGoing;
   }
}
