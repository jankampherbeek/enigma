/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Finds the Julian Day (for UT) for a given position within a time range. Should only be used if retrogradation cannot occur (Sun, Moon, Mean Node, or
 * helicoentric positions).
 */
public class JdFromPosCalc {

   private final CoordSetForDateTimeCalc coordSetCalc;

   /**
    * Use ProgCalcFactory to instantiate.
    *
    * @param coordSetCalc instance of CoordSetForDAteTimeCalc, used to calculat the positions.
    * @see com.radixpro.enigma.be.calc.handlers.CaHandlersFactory
    */
   public JdFromPosCalc(final CoordSetForDateTimeCalc coordSetCalc) {
      this.coordSetCalc = coordSetCalc;
   }

   /**
    * Finds Julian Day (for UT).
    *
    * @param startJd  Julian Day to start with. PRE: < endJd.
    * @param endJd    Julian Day to end with. PRE: > startJd.
    * @param position The calculated position.
    * @param point    The celestial point to calculate.
    * @param flags    Flags for the SE, this also indicates what corodinatesystem is used, tropical/sidereal, etc.
    * @param location The location, this is only used if the flags indicate that parallax is used.
    * @return The calculated Julian Day number.
    * @throws NoPositionFoundException if position could not be found.
    */
   public double findJd(final double startJd, final double endJd, final double position, @NotNull final CelestialObjects point, final int flags,
                        @NotNull final Location location)
         throws NoPositionFoundException {
      checkArgument(startJd < endJd);
      return jdForPosition(startJd, endJd, position, point, flags, location);
   }

   private double jdForPosition(double startJd, double endJd, double position, CelestialObjects point, int flags, Location location)
         throws NoPositionFoundException {
      double posEnd;
      double posCheck;
      double tempStart = startJd;
      double tempEnd = endJd;
      double tempCheck = startJd + (endJd - startJd) / 2.0;
      int counter = 0;
      boolean found = false;
      while (counter < 10000 && !found) {
         counter++;
         posEnd = coordSetCalc.calcSet(tempEnd, (int) point.getSeId(), flags, location).getMainCoord();
         posCheck = coordSetCalc.calcSet(tempCheck, (int) point.getSeId(), flags, location).getMainCoord();
         if ((posEnd - posCheck) > 180.0) posCheck += 180.0;
         if (Math.abs(posCheck - position) < 0.000001) found = true;
         if (position < posCheck) tempEnd = tempCheck;
         else tempStart = tempCheck;
         tempCheck = tempStart + (tempEnd - tempStart) / 2.0;
      }
      if (found) return tempCheck;
      else throw new NoPositionFoundException("Could not find position for " + point.getId());
   }


}
