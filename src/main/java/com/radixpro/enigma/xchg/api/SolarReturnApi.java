/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.handlers.SolarReturnHandler;
import com.radixpro.enigma.be.exceptions.NoPositionFoundException;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.xchg.api.requests.SolarReturnRequest;
import com.radixpro.enigma.xchg.api.responses.SolarReturnResponse;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service for the calculation of a Solar Return Chart.
 */
public class SolarReturnApi {

   private static final Logger LOG = Logger.getLogger(SolarReturnApi.class);
   private final SolarReturnHandler handler;
   private final Rosetta rosetta;

   /**
    * Use ApiProgFactory for instantiation.
    *
    * @param handler handler to fullfil incoming requests.
    * @param rosetta handles the resource bundle translations.
    * @see com.radixpro.enigma.xchg.api.factories.ApiProgFactory
    */
   public SolarReturnApi(final SolarReturnHandler handler, final Rosetta rosetta) {
      this.handler = checkNotNull(handler);
      this.rosetta = checkNotNull(rosetta);
   }

   /**
    * Service for the calculation of a Solar Return Chart.
    *
    * @param request the request for the calculation. PRE: not null.
    * @return the response from the calculation.
    */
   public SolarReturnResponse calculateSolarReturn(final SolarReturnRequest request) {
      checkNotNull(request);
      CalculatedChart solarReturnChart;
      String resultMsg = "OK";
      try {
         solarReturnChart = handler.getSolarReturnChart(request.getLongSun(), request.getBirthDateTime(), request.getYearForReturn(),
               request.getLocation(), request.getSettings());
      } catch (NoPositionFoundException e) {
         LOG.error("Could not find position in given period. NoPositionFoundException : " + e.getMessage());
         solarReturnChart = null;
         resultMsg = rosetta.getText("error.positionsearch");
      }
      return new SolarReturnResponse(solarReturnChart, resultMsg);
   }


}
