/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import com.radixpro.enigma.xchg.domain.Ayanamshas;
import com.radixpro.enigma.xchg.domain.EclipticProjections;
import com.radixpro.enigma.xchg.domain.HouseSystems;
import com.radixpro.enigma.xchg.domain.ObserverPositions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AstronConfigurationTest {

   private List<ConfiguredCelObject> selectedCelestialObjects;
   private AstronConfiguration astronConfiguration;

   @Before
   public void setUp() {
      selectedCelestialObjects = new ArrayList<>();
      astronConfiguration = new AstronConfiguration(HouseSystems.CAMPANUS, Ayanamshas.NONE, EclipticProjections.TROPICAL,
            ObserverPositions.TOPOCENTRIC, selectedCelestialObjects);
   }

   @Test
   public void getHouseSystem() {
      assertEquals(HouseSystems.CAMPANUS, astronConfiguration.getHouseSystem());
   }

   @Test
   public void getAyanamsha() {
      assertEquals(Ayanamshas.NONE, astronConfiguration.getAyanamsha());
   }

   @Test
   public void getEclipticProjection() {
      assertEquals(EclipticProjections.TROPICAL, astronConfiguration.getEclipticProjection());
   }

   @Test
   public void getObserverPosition() {
      assertEquals(ObserverPositions.TOPOCENTRIC, astronConfiguration.getObserverPosition());
   }

   @Test
   public void getCelestialObjects() {
      assertEquals(selectedCelestialObjects, astronConfiguration.getCelObjects());

   }
}