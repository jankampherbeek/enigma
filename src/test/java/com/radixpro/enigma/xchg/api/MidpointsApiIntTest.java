/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.api.factories.ApiAnalysisFactory;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.MundanePoints;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MidpointTypes;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;
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

   private List<IObjectVo> celObjects;
   private List<IObjectVo> mundaneValues;
   private MidpointsApi api;

   @Before
   public void setUp() {
      when(sunPosMock.getLongitude()).thenReturn(20.0);
      when(moonPosMock.getLongitude()).thenReturn(100.0);


      celObjects = createCelObjects();
      mundaneValues = createMundaneValues();
      api = new ApiAnalysisFactory().createMidpointsApi();
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

   private List<IObjectVo> createCelObjects() {


      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(30.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.MERCURY));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(10.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.VENUS));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(206.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.MARS));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(207.0),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.JUPITER));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(150.5),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.SATURN));
      newCelObjects.add(new ObjectVo(
            createCelCoordinateVo(175.5),
            createCelCoordinateVo(0.0),
            createCelCoordinateVo(0.0),
            CelestialObjects.URANUS));
      return newCelObjects;
   }

   private List<IObjectVo> createMundaneValues() {
      List<IObjectVo> newHouses = new ArrayList<>();
      newHouses.add(new ObjectVo(
            createHouseCoordinateVo(159.0),
            createHouseCoordinateVo(0.0),
            createHouseCoordinateVo(0.0),
            MundanePoints.MC));
      newHouses.add(new ObjectVo(
            createHouseCoordinateVo(250.0),
            createHouseCoordinateVo(0.0),
            createHouseCoordinateVo(0.0),
            MundanePoints.ASC));
      return newHouses;
   }

   private CelCoordinateVo createCelCoordinateVo(double basePos) {
      CelCoordinateElementVo posCoordinate = new CelCoordinateElementVo(basePos, 0.0, 0.0);
      CelCoordinateElementVo speedCoordinate = new CelCoordinateElementVo(0.0, 0.0, 0.0);
      return new CelCoordinateVo(posCoordinate, speedCoordinate);
   }

   private HouseCoordinateVo createHouseCoordinateVo(double basePos) {
      CelCoordinateElementVo posCoordinate = new CelCoordinateElementVo(basePos, 0.0, 0.0);
      return new HouseCoordinateVo(posCoordinate);
   }
}