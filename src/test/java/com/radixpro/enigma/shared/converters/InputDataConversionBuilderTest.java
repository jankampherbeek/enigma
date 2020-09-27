/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.references.TimeZones;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputDataConversionBuilderTest {

   private InputDataConversionBuilder builder;

   @Before
   public void setUp() throws Exception {
      builder = new InputDataConversionBuilder();
   }

   @Test
   public void build() {
      builder.setDate(1953, 1, 29, "G");
      builder.setTime(8, 37, 30, TimeZones.CET, false);
      builder.setLocationName("Enschede");
      builder.setLatitude(52, 13, 0, "N");
      builder.setLongitude(6, 54, 0, "O");
      builder.setSource("Corrected");
      assertEquals("Enschede,52°13′0″N,0°54′0″O\n" + "1953/1/29 G 0:37:30 +01:00: CET/Central European Time. No DST\n" + "Corrected", builder.build());
   }

   @Test
   public void buildWithOffset() {
      builder.setDate(1953, 1, 29, "G");
      builder.setTime(8, 37, 30, TimeZones.LMT, false);
      builder.setLocationName("Enschede");
      builder.setLatitude(52, 13, 0, "N");
      builder.setLongitude(6, 54, 0, "O");
      builder.setSource("Corrected");
      builder.setOffsetLmt(7, 0, 0, "O");
      assertEquals("Enschede,52°13′0″N,0°54′0″O\n" + "1953/1/29 G 0:37:30 LMT: Local Mean Time (define geographic longitude). No DST\n" +
            "Offset 0°0′0″O\n" + "Corrected", builder.build());
   }

}