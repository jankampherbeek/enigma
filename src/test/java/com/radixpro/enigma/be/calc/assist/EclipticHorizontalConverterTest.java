/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.be.calc.converters.CalcConvertersFactory;
import com.radixpro.enigma.be.calc.converters.EclipticHorizontalConverter;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EclipticHorizontalConverterTest {

   private final double delta = 0.00000001;
   private final double jdUt = 123456.789;
   private final double[] eclipticalCoordinates = new double[]{100.1, 2.1, 3.1};
   @Mock
   private Location locationMock;
   private EclipticHorizontalConverter eclipticHorizontalConverter;

   @Before
   public void setUp() {
      int flags = 0;
      eclipticHorizontalConverter = new CalcConvertersFactory().getEclipticHorizontalConverter();
   }

   @Test
   public void getAzimuth() {
      assertEquals(92.195865705034, eclipticHorizontalConverter.convert(jdUt, eclipticalCoordinates, locationMock)[0], delta);
   }

   @Test
   public void getAltitude() {
      assertEquals(16.9886538212, eclipticHorizontalConverter.convert(jdUt, eclipticalCoordinates, locationMock)[1], delta);
   }

}