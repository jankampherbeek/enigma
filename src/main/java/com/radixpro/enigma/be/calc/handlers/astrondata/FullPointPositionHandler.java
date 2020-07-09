/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.astrondata;

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.astrondata.CoordinateSet;
import com.radixpro.enigma.xchg.domain.astrondata.CoordinateSet3D;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointCoordinate;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointPosition;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.*;

/**
 * Takes care of calculating a full position for all relevant coördinates for a specific celestial body.
 */
public class FullPointPositionHandler {

   private final SeFrontend seFrontend;

   /**
    * Initialization via AstronDataHandlersFactory.
    *
    * @param seFrontend instance of SeFrontend. PRE: not null.
    * @see AstronDataHandlersFactory
    */
   public FullPointPositionHandler(final SeFrontend seFrontend) {
      this.seFrontend = checkNotNull(seFrontend);
   }

   /**
    * Defines a full position.
    *
    * @param jdUt     Julian Day for UT.
    * @param obsPos   Position of the observer. PRE: not null.
    * @param eclProj  Type of projection to the ecliptic. PRE: not null.
    * @param location Location. PRE: not null.
    * @return calculated values.
    */
   public FullPointPosition definePosition(final CelestialObjects celObject, final double jdUt, final ObserverPositions obsPos,
                                           final EclipticProjections eclProj, final Ayanamshas ayanamsha, final Location location) {
      checkNotNull(obsPos);
      checkNotNull(eclProj);
      checkNotNull(location);
      final int seId = (int) celObject.getSeId();
      int seFlags = SEFLG_SWIEPH | SEFLG_SPEED;
      if (obsPos == ObserverPositions.TOPOCENTRIC) seFlags = seFlags | SEFLG_TOPOCTR;
      // TODO release 2020.2: check for heliocentric
      if (eclProj == EclipticProjections.SIDEREAL) seFlags = seFlags | SEFLG_SIDEREAL;
      // TODO release 2020.2: include ayanamsha and support for sidereal
      final SePositionResultCelObjects positionResultEcl = seFrontend.getPositionsForCelBody(jdUt, seId, seFlags, location);
      FullPointCoordinate eclPos = convert(positionResultEcl);
      seFlags = seFlags | SEFLG_EQUATORIAL;
      final SePositionResultCelObjects positionResultEq = seFrontend.getPositionsForCelBody(jdUt, seId, seFlags, location);
      FullPointCoordinate eqPos = convert(positionResultEq);
      final double[] coords = {eclPos.getPosition().getMainCoord(), eclPos.getPosition().getDeviation()};
      double[] positionsHor = seFrontend.getHorizontalPosition(jdUt, coords, location, seFlags);
      final CoordinateSet horPos = convert(positionsHor);
      return new FullPointPosition(celObject, eclPos, eqPos, horPos);
   }

   private FullPointCoordinate convert(final SePositionResultCelObjects positionResult) {
      if (!positionResult.getErrorMsg().isEmpty()) new FailFastHandler().terminate("Error when calculating cel point. " +
            positionResult.getErrorMsg());
      double[] allPositions = positionResult.getAllPositions();
      double mainPos = allPositions[0];
      double devPos = allPositions[1];
      double distPos = allPositions[2];
      double mainSpeed = allPositions[3];
      double devSpeed = allPositions[4];
      double distSpeed = allPositions[5];
      CoordinateSet3D position = new CoordinateSet3D(mainPos, devPos, distPos);
      CoordinateSet3D speed = new CoordinateSet3D(mainSpeed, devSpeed, distSpeed);
      return new FullPointCoordinate(position, speed);
   }

   private CoordinateSet convert(final double[] positions) {
      return new CoordinateSet(positions[0], positions[1]);
   }


}
