/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfiguredCelObjectTest {

   private static final double DELTA = 0.00000001;
   private final CelestialObjects celestialObject = CelestialObjects.JUPITER;
   private final String glyph = "g";
   private final int orbPercentage = 100;
   private final boolean showInDrawing = true;
   private ConfiguredCelObject configuredCelObject;

   @Before
   public void setUp() {
      configuredCelObject = new ConfiguredCelObject(celestialObject, glyph, orbPercentage, showInDrawing);
   }

   @Test
   public void getCelObject() {
      assertEquals(celestialObject, configuredCelObject.getCelObject());
   }

   @Test
   public void getGlyph() {
      assertEquals(glyph, configuredCelObject.getGlyph());
   }

   @Test
   public void getOrbPercentage() {
      assertEquals(orbPercentage, configuredCelObject.getOrbPercentage(), DELTA);
   }

   @Test
   public void isShowInDrawing() {
      assertEquals(showInDrawing, configuredCelObject.isShowInDrawing());
   }
}