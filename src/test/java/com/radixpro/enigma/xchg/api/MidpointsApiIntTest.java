/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.MidpointTypes;
import com.radixpro.enigma.references.MundanePoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MidpointsApiIntTest {

   @Mock
   private IPosition sunPosMock;
   @Mock
   private IPosition moonPosMock;
   @Mock
   private IPosition mercPosMock;
   @Mock
   private IPosition venPosMock;
   @Mock
   private IPosition marsPosMock;
   @Mock
   private IPosition jupPosMock;
   @Mock
   private IPosition satPosMock;
   @Mock
   private IPosition uraPosMock;
   @Mock
   private IPosition mcPosMock;
   @Mock
   private IPosition ascPosMock;

   private List<IPosition> celObjects;
   private List<IPosition> mundaneValues;
   private MidpointsApi api;

   @Before
   public void setUp() {
      when(sunPosMock.getLongitude()).thenReturn(20.0);
      when(moonPosMock.getLongitude()).thenReturn(100.0);
      when(mercPosMock.getLongitude()).thenReturn(30.0);
      when(venPosMock.getLongitude()).thenReturn(10.0);
      when(marsPosMock.getLongitude()).thenReturn(206.0);
      when(jupPosMock.getLongitude()).thenReturn(207.0);
      when(satPosMock.getLongitude()).thenReturn(150.5);
      when(uraPosMock.getLongitude()).thenReturn(175.5);
      when(sunPosMock.getChartPoint()).thenReturn(CelestialObjects.SUN);
      when(moonPosMock.getChartPoint()).thenReturn(CelestialObjects.MOON);
      when(mercPosMock.getChartPoint()).thenReturn(CelestialObjects.MERCURY);
      when(venPosMock.getChartPoint()).thenReturn(CelestialObjects.VENUS);
      when(marsPosMock.getChartPoint()).thenReturn(CelestialObjects.MARS);
      when(jupPosMock.getChartPoint()).thenReturn(CelestialObjects.JUPITER);
      when(satPosMock.getChartPoint()).thenReturn(CelestialObjects.SATURN);
      when(uraPosMock.getChartPoint()).thenReturn(CelestialObjects.URANUS);

      celObjects = new ArrayList<>();
      celObjects.add(sunPosMock);
      celObjects.add(moonPosMock);
      celObjects.add(mercPosMock);
      celObjects.add(venPosMock);
      celObjects.add(marsPosMock);
      celObjects.add(jupPosMock);
      celObjects.add(satPosMock);
      celObjects.add(uraPosMock);
      when(mcPosMock.getLongitude()).thenReturn(159.0);
      when(ascPosMock.getLongitude()).thenReturn(250.0);
      when(mcPosMock.getChartPoint()).thenReturn(MundanePoints.MC);
      when(ascPosMock.getChartPoint()).thenReturn(MundanePoints.ASC);
      mundaneValues = new ArrayList<>();
      mundaneValues.add(mcPosMock);
      mundaneValues.add(ascPosMock);
      api = XchgApiInjector.injectMidpointsApi();
   }

   @Test
   public void analyseMidpoints() {
      List<IAnalyzedPair> results = api.analyseMidpoints(celObjects, mundaneValues);
      assertEquals(136, results.size());
      AnalyzedMidpoint result0 = (AnalyzedMidpoint) results.get(0);
      assertEquals(CelestialObjects.SUN, result0.getFirst().getChartPoint());
      assertEquals(CelestialObjects.MOON, result0.getSecond().getChartPoint());
      assertEquals(CelestialObjects.MARS, result0.getCenterPoint().getChartPoint());
      assertEquals(MidpointTypes.SIXTEENTH, result0.getMidpointType());
      assertEquals(0.25, result0.getActualOrb(), DELTA_8_POS);
      assertEquals(15.625, result0.getPercOrb(), DELTA_8_POS);
   }

}