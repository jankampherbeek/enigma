/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.handlers.CaHandlersFactory;
import com.radixpro.enigma.domain.astronpos.AllMundanePositions;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.astronpos.FullPointPosition;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.HouseSystems;
import com.radixpro.enigma.references.ObserverPositions;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * HAndler for the calculation of charts.
 */
public class CalculatedChartHandler {

   private final FullPointPositionHandler fullPointPositionHandler;
   private final MundanePositionsHandler mundanePositionsHandler;

   /**
    * Instantiate via factory.
    *
    * @param fullPointPositionHandler handler for full positions of celestial points.
    * @param mundanePositionsHandler  handler for mundane positions.
    * @see CaHandlersFactory
    */
   public CalculatedChartHandler(@NotNull final FullPointPositionHandler fullPointPositionHandler,
                                 @NotNull final MundanePositionsHandler mundanePositionsHandler) {
      this.fullPointPositionHandler = fullPointPositionHandler;
      this.mundanePositionsHandler = mundanePositionsHandler;
   }

   /**
    * Handle the calculation of a chart.
    *
    * @param settings Settigns for the calcualtion.
    * @param dateTime Date and time.
    * @param location Location.
    * @return result of the calculation.
    */
   public CalculatedChart defineChart(@NotNull final ChartCalcSettings settings, @NotNull final FullDateTime dateTime, @NotNull final Location location) {
      final double jdUt = dateTime.getJdUt();
      final ObserverPositions obsPos = settings.getObsPos();
      final EclipticProjections eclProj = settings.getEclProj();
      final Ayanamshas ayanamsha = settings.getAyanamsha();
      final HouseSystems houseSystem = settings.getHouseSystem();
      final List<IChartPoints> celPoints = settings.getPoints();
      final List<IPosition> fullPointPositions = new ArrayList<>();
      for (IChartPoints celPoint : celPoints) {
         final FullPointPosition fullPointPosition = fullPointPositionHandler.definePosition(celPoint, jdUt, obsPos, eclProj, ayanamsha, location);
         fullPointPositions.add(fullPointPosition);
      }
      final AllMundanePositions allMundanePositions = mundanePositionsHandler.definePositions(jdUt, eclProj, ayanamsha, houseSystem, location);
      return new CalculatedChart(fullPointPositions, allMundanePositions);
   }

}
