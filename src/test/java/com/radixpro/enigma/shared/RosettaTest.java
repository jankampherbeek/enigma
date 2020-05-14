/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RosettaTest {

   private PersistedPropertyApi api;

   @Before
   public void setUp() {

   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getRosetta() {
      var rosetta = Rosetta.getRosetta();
      assertNotNull(rosetta);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void setLanguageAndGetText() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
      rosetta.setLanguage("du");
      assertEquals("Tropisch", rosetta.getText("eclipticprojections.tropical"));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void setLanguageAndGetHelpText() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Help for data input for charts", rosetta.getHelpText("help.chartsinput.title"));
      rosetta.setLanguage("du");
      assertEquals("Help voor invoer data voor horoscopen", rosetta.getHelpText("help.chartsinput.title"));


   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void setLanguageUnsupportedLang() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
      rosetta.setLanguage("es");
      assertEquals("Tropical", rosetta.getText("eclipticprojections.tropical"));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getLocale() {
      var rosetta = Rosetta.getRosetta();
      rosetta.setLanguage("en");
      assertEquals("EN", rosetta.getLocale().getCountry());
      rosetta.setLanguage("du");
      assertEquals("DU", rosetta.getLocale().getCountry());
   }


}