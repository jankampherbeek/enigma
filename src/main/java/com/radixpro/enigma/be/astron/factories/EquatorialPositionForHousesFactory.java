/*
 *
 *  * Jan Kampherbeek, (c) 2020.
 *  * Enigma is open source.
 *  * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.astron.factories;


import com.radixpro.enigma.be.astron.assist.EquatorialPositionForHouses;
import com.radixpro.enigma.be.astron.converters.EclipticEquatorialConversions;
import com.radixpro.enigma.be.astron.core.SeFrontend;
import swisseph.SwissLib;

/**
 * Factory for EquatorialPositionForHouses.
 */
public class EquatorialPositionForHousesFactory {

   public EquatorialPositionForHouses createInstance(final double longitude, final double jdUt) {
      SwissLib swissLib = new SwissLib();
      EclipticEquatorialConversions conversions = new EclipticEquatorialConversions(swissLib);
      SeFrontend seFrontend = SeFrontend.getFrontend();
      return new EquatorialPositionForHouses(seFrontend, conversions, longitude, jdUt);
   }

}
