/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.astrondata;

import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.astrondata.AllMundanePositions;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * HAndler for the calculation of charts.
 */
public class CalculatedChartHandler {

   private final FullPointPositionHandler fullPointPositionHandler;
   private final MundanePositionsHandler mundanePositionsHandler;

   /**
    * Instantiate via factory.
    *
    * @param fullPointPositionHandler handler for full positions of celestial points. PRE: not null.
    * @param mundanePositionsHandler  handler for mundane positions. PRE: not null.
    * @see AstronDataHandlersFactory
    */
   public CalculatedChartHandler(final FullPointPositionHandler fullPointPositionHandler, final MundanePositionsHandler mundanePositionsHandler) {
      this.fullPointPositionHandler = checkNotNull(fullPointPositionHandler);
      this.mundanePositionsHandler = checkNotNull(mundanePositionsHandler);
   }

   /**
    * Handle the calculation of a chart.
    *
    * @param settings Settigns for the calcualtion. PRE: not null.
    * @param dateTime Date and time. PRE: not null.
    * @param location Location. PRE: not null.
    * @return result of the calculation.
    */
   public CalculatedChart defineChart(final ChartCalcSettings settings, final FullDateTime dateTime, final Location location) {
      checkNotNull(settings);
      checkNotNull(dateTime);
      checkNotNull(location);
      final double jdUt = dateTime.getJdUt();
      final ObserverPositions obsPos = settings.getObsPos();
      final EclipticProjections eclProj = settings.getEclProj();
      final Ayanamshas ayanamsha = settings.getAyanamsha();
      final HouseSystems houseSystem = settings.getHouseSystem();
      final List<CelestialObjects> celPoints = settings.getPoints();
      final List<IPosition> fullPointPositions = new ArrayList<>();
      for (CelestialObjects celPoint : celPoints) {
         fullPointPositions.add(fullPointPositionHandler.definePosition(celPoint, jdUt, obsPos, eclProj, ayanamsha, location));
      }
      final AllMundanePositions allMundanePositions = mundanePositionsHandler.definePositions(jdUt, eclProj, ayanamsha, houseSystem, location);
      return new CalculatedChart(fullPointPositions, allMundanePositions);
   }

}
