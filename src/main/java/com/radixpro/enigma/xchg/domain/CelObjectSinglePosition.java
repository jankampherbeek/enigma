/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.be.astron.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.astron.core.SeFrontend;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculated positions for a specific coordinateset for a celestial body.
 */
public class CelObjectSinglePosition {

   private final Location location;
   private String errorMsg = "";
   private double mainPosition;
   private double deviationPosition;
   private double distancePosition;
   private double mainSpeed;
   private double deviationSpeed;
   private double distanceSpeed;

   public CelObjectSinglePosition(final SeFrontend seFrontend, final double jdUt, final CelestialObjects celBody,
                                  final int flags, final Location location) {
      this.location = location;
      calculate(checkNotNull(seFrontend), jdUt, checkNotNull(celBody), flags);
   }

   private void calculate(final SeFrontend seFrontend, final double jdUt,
                          final CelestialObjects celBody, final int flags) {
      SePositionResultCelObjects sePositionResult = seFrontend.getPositionsForCelBody(jdUt, (int) celBody.getSeId(), flags, location);
      errorMsg = sePositionResult.getErrorMsg();
      mainPosition = sePositionResult.getAllPositions()[0];
      deviationPosition = sePositionResult.getAllPositions()[1];
      distancePosition = sePositionResult.getAllPositions()[2];
      mainSpeed = sePositionResult.getAllPositions()[3];
      deviationSpeed = sePositionResult.getAllPositions()[4];
      distanceSpeed = sePositionResult.getAllPositions()[5];

   }

   public String getErrorMsg() {
      return errorMsg;
   }

   public double getMainPosition() {
      return mainPosition;
   }

   public double getDeviationPosition() {
      return deviationPosition;
   }

   public double getDistancePosition() {
      return distancePosition;
   }

   public double getMainSpeed() {
      return mainSpeed;
   }

   public double getDeviationSpeed() {
      return deviationSpeed;
   }

   public double getDistanceSpeed() {
      return distanceSpeed;
   }
}
