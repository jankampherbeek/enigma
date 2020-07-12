/*
 *
 *  * Jan Kampherbeek, (c) 2020.
 *  * Enigma is open source.
 *  * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.factories;


import com.radixpro.enigma.be.calc.assist.EquatorialPositionForHouses;
import com.radixpro.enigma.be.calc.converters.EclipticEquatorialConversions;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import swisseph.SwissLib;

/**
 * Factory for EquatorialPositionForHouses.
 * TODO remove obsolete class
 */
public class EquatorialPositionForHousesFactory {

   public EquatorialPositionForHouses createInstance(final double longitude, final double jdUt) {
      SwissLib swissLib = new SwissLib();
      EclipticEquatorialConversions conversions = new EclipticEquatorialConversions(swissLib);
      SeFrontend seFrontend = SeFrontend.getFrontend();
      return new EquatorialPositionForHouses(seFrontend, conversions, longitude, jdUt);
   }

}
