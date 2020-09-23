/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.be.handlers.SolarReturnHandler;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.reqresp.SolarReturnRequest;
import com.radixpro.enigma.domain.reqresp.SolarReturnResponse;
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Service for the calculation of a Solar Return Chart.
 */
public class SolarReturnApi {

   private static final Logger LOG = Logger.getLogger(SolarReturnApi.class);
   private final SolarReturnHandler handler;
   private final Rosetta rosetta;


   public SolarReturnApi(@NotNull final SolarReturnHandler handler) {
      this.handler = handler;
      this.rosetta = Rosetta.getRosetta();
   }

   /**
    * Service for the calculation of a Solar Return Chart.
    *
    * @param request the request for the calculation. PRE: not null.
    * @return the response from the calculation.
    */
   public SolarReturnResponse calculateSolarReturn(@NotNull final SolarReturnRequest request) {
      CalculatedChart solarReturnChart;
      String resultMsg = "OK";
      try {
         solarReturnChart = handler.getSolarReturnChart(request.getLongSun(), request.getBirthDateTime(), request.getAgeForReturn(),
               request.getLocation(), request.getSettings());
      } catch (NoPositionFoundException e) {
         LOG.error("Could not find position in given period. NoPositionFoundException : " + e.getMessage());
         solarReturnChart = null;
         resultMsg = rosetta.getText("error.positionsearch");
      }
      return new SolarReturnResponse(solarReturnChart, resultMsg);
   }


}
