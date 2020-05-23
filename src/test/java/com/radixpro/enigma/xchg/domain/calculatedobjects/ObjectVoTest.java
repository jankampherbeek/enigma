/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectVoTest {

   private ObjectVo vo;
   private ICoordinateVo eclCoords;
   private ICoordinateVo equaCoords;
   private ICoordinateVo horiCoords;
   private IChartPoints chartPoint;

   @Before
   public void setUp() throws Exception {
      eclCoords = new CelCoordinateVoFake();
      equaCoords = new CelCoordinateVoFake();
      horiCoords = new CelCoordinateVoFake();
      chartPoint = CelestialObjects.SATURN;
      vo = new ObjectVo(eclCoords, equaCoords, horiCoords, chartPoint);
   }

   @Test
   public void getEclipticCoords() {
      assertEquals(eclCoords, vo.getEclipticCoords());
   }

   @Test
   public void getEquatorialCoords() {
      assertEquals(equaCoords, vo.getEquatorialCoords());
   }

   @Test
   public void getHorizontalCoords() {
      assertEquals(horiCoords, vo.getHorizontalCoords());
   }

   @Test
   public void getChartPoint() {
      assertEquals(chartPoint, vo.getChartPoint());
   }
}