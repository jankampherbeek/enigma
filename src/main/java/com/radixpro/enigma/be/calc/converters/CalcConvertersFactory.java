/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.converters;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import swisseph.SwissLib;

public class CalcConvertersFactory {

   public EclipticEquatorialConversions getEclipticalEquatorialConversions() {
      return new EclipticEquatorialConversions(new SwissLib());
   }

   public EclipticHorizontalConverter getEclipticHorizontalConverter() {
      return new EclipticHorizontalConverter(SeFrontend.getFrontend());
   }

}
