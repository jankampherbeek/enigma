/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.be.calc.assist.HousePosition;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.main.CelObjectPosition;
import com.radixpro.enigma.be.calc.main.MundaneValues;
import com.radixpro.enigma.be.calc.main.Obliquity;
import com.radixpro.enigma.xchg.domain.analysis.MundanePoints;
import com.radixpro.enigma.xchg.domain.calculatedobjects.CelCoordinateVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.HouseCoordinateVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.IObjectVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.ObjectVo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A 'full' chart with information on all positions.
 */
public class FullChart {  // TODO split into calculation and VO (CelOBjectSinglePosition ??? ).

   private final FullDateTime fullDateTime;
   private final Location location;
   private final CalculationSettings settings;
   private final SeFrontend seFrontend;
   private final double jdUt;
   private MundaneValues mundaneValues;
   private List<CelObjectPosition> bodies;
   private double obliquity;
   private long flagsValue;
   private List<SeFlags> allFlags;
   private final List<IObjectVo> allCelBodyPositions;
   private final List<IObjectVo> allHousePositions;


   public FullChart(final FullDateTime fullDateTime, final Location location, final CalculationSettings settings) {
      this.fullDateTime = checkNotNull(fullDateTime);
      this.location = checkNotNull(location);
      this.settings = checkNotNull(settings);
      this.jdUt = fullDateTime.getJdUt();
      seFrontend = SeFrontend.getFrontend();
      allCelBodyPositions = new ArrayList<>();
      allHousePositions = new ArrayList<>();
      calculateFlags();
      calculateHouses();
      calculateBodies();
      calculateObliquity();
   }

   private void calculateFlags() {
      allFlags = new ArrayList<>();
      allFlags.add(SeFlags.SWISSEPH);
      allFlags.add(SeFlags.SPEED);
      if (settings.isSidereal()) {
         allFlags.add(SeFlags.SIDEREAL);
      }
      if (settings.isTopocentric()) {
         allFlags.add(SeFlags.TOPOCENTRIC);
      }
      flagsValue = new CombinedFlags().getCombinedValue(allFlags);
   }

   private void calculateHouses() {
      mundaneValues = new MundaneValues(seFrontend, jdUt, (int) flagsValue, location, settings.getHouseSystem());
      allHousePositions.add(createHouseObjectVo(mundaneValues.getMc(), MundanePoints.MC));
      allHousePositions.add(createHouseObjectVo(mundaneValues.getAscendant(), MundanePoints.ASC));
      allHousePositions.add(createHouseObjectVo(mundaneValues.getEastpoint(), MundanePoints.EAST_POINT));
      allHousePositions.add(createHouseObjectVo(mundaneValues.getVertex(), MundanePoints.VERTEX));
      for (HousePosition pos : mundaneValues.getCusps()) {
         allHousePositions.add(createHouseObjectVo(pos, MundanePoints.CUSP));
      }
   }

   private void calculateBodies() {
      bodies = new ArrayList<>();
      for (int i = 0; i < settings.getCelBodies().size(); i++) {
         CelObjectPosition newPos = new CelObjectPosition(seFrontend, jdUt, settings.getCelBodies().get(i), location, allFlags);
         bodies.add(newPos);
         allCelBodyPositions.add(createCelObjectVo(newPos));
      }
   }

   // TODO: temporary solution, replace List<CelObjectPosition> with List<CelObjectVo>
   private IObjectVo createCelObjectVo(CelObjectPosition pos) {
      CelCoordinateElementVo eclPos = new CelCoordinateElementVo(
            pos.getEclipticalPosition().getMainPosition(),
            pos.getEclipticalPosition().getDeviationPosition(),
            pos.getEclipticalPosition().getDistancePosition());
      CelCoordinateElementVo eclSpeed = new CelCoordinateElementVo(
            pos.getEclipticalPosition().getMainSpeed(),
            pos.getEclipticalPosition().getDeviationSpeed(),
            pos.getEclipticalPosition().getDistanceSpeed());
      CelCoordinateVo eclCoordinates = new CelCoordinateVo(eclPos, eclSpeed);
      CelCoordinateElementVo equaPos = new CelCoordinateElementVo(
            pos.getEquatorialPosition().getMainPosition(),
            pos.getEquatorialPosition().getDeviationPosition(),
            pos.getEquatorialPosition().getDistancePosition());
      CelCoordinateElementVo equaSpeed = new CelCoordinateElementVo(
            pos.getEquatorialPosition().getMainSpeed(),
            pos.getEquatorialPosition().getDeviationSpeed(),
            pos.getEquatorialPosition().getDistanceSpeed());
      CelCoordinateVo equaCoordinates = new CelCoordinateVo(equaPos, equaSpeed);
      CelCoordinateElementVo horiPos = new CelCoordinateElementVo(
            pos.getHorizontalPosition().getAzimuth(),
            pos.getHorizontalPosition().getAltitude(),
            0.0);
      CelCoordinateElementVo horiSpeed = new CelCoordinateElementVo(0.0, 0.0, 0.0);
      CelCoordinateVo horiCoordinates = new CelCoordinateVo(horiPos, horiSpeed);
      return new ObjectVo(eclCoordinates, equaCoordinates, horiCoordinates, pos.getCelestialBody());
   }

   // TODO: temporary solution, replace List<HousePosition> wiht List<CelOBjectVo>
   private IObjectVo createHouseObjectVo(HousePosition pos, MundanePoints mundanePoint) {
      CelCoordinateElementVo eclCoord = new CelCoordinateElementVo(pos.getLongitude(), 0.0, 0.0);
      CelCoordinateElementVo equaCoord = new CelCoordinateElementVo(
            pos.getEquatorialPositionForHouses().getRightAscension(), pos.getEquatorialPositionForHouses().getDeclination(), 0.0);
      CelCoordinateElementVo horiCoord = new CelCoordinateElementVo(
            pos.getHorizontalPosition().getAzimuth(), pos.getHorizontalPosition().getAltitude(), 0.0);
      HouseCoordinateVo eclPos = new HouseCoordinateVo(eclCoord);
      HouseCoordinateVo equaPos = new HouseCoordinateVo(equaCoord);
      HouseCoordinateVo horiPos = new HouseCoordinateVo(horiCoord);
      return new ObjectVo(eclPos, equaPos, horiPos, mundanePoint);
   }

   private void calculateObliquity() {
      obliquity = new Obliquity(seFrontend, jdUt).getTrueObliquity();
   }

   public double getJulianDayForUt() {
      return jdUt;
   }

   public FullDateTime getFullDateTime() {
      return fullDateTime;
   }

   public Location getLocation() {
      return location;
   }

   public CalculationSettings getSettings() {
      return settings;
   }

   public MundaneValues getMundaneValues() {
      return mundaneValues;
   }

   public List<CelObjectPosition> getBodies() {
      return bodies;
   }

   public double getObliquity() {
      return obliquity;
   }

   public List<IObjectVo> getAllCelBodyPositions() {
      return allCelBodyPositions;
   }

   public List<IObjectVo> getAllHousePositions() {
      return allHousePositions;
   }
}
