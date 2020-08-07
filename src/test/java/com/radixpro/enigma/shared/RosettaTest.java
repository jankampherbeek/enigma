/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RosettaTest {

   private PersistedPropertyApi api;

   @Test
   public void getRosetta() {
      var rosetta = Rosetta.getRosetta();
      assertNotNull(rosetta);
   }

   @Test
   public void setLanguageAndGetText() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
      rosetta.setLanguage("du");
      assertEquals("Tropisch", rosetta.getText("eclipticprojections.tropical"));
   }

   @Test
   public void setLanguageAndGetHelpText() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Help for data input for charts", rosetta.getHelpText("help.chartsinput.title"));
      rosetta.setLanguage("du");
      assertEquals("Help voor invoer data voor horoscopen", rosetta.getHelpText("help.chartsinput.title"));


   }

   @Test
   public void setLanguageUnsupportedLang() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
      rosetta.setLanguage("es");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
   }

   @Test
   public void getLocale() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("EN", rosetta.getLocale().getCountry());
      rosetta.setLanguage("du");
      assertEquals("DU", rosetta.getLocale().getCountry());
   }


}