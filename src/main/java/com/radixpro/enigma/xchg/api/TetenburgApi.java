/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.TetenburgHandler;
import com.radixpro.enigma.domain.reqresp.TetenburgRequest;
import com.radixpro.enigma.domain.reqresp.TetenburgResponse;
import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Api for the calculation of critical points according to the theory of Ton Tetenburg.
 */
public class TetenburgApi {
   private static final Logger LOG = Logger.getLogger(TetenburgApi.class);
   private final TetenburgHandler handler;


   public TetenburgApi(final TetenburgHandler handler) {
      this.handler = checkNotNull(handler);
   }

   public TetenburgResponse calculateCriticalPoint(final TetenburgRequest request) {
      String resultMsg = "OK";
      double longAsc = 0.0;
      try {
         longAsc = handler.criticalPoint(request.getBirthDateTime().getJd(), request.getProgDateTime().getJd(), request.getLocation().getGeoLat(),
               request.getLongMcRadix(), request.getSolarSpeed());
      } catch (Exception e) {
         LOG.error("Exception when retrieving TetenburgResponse : " + e.getMessage());
         resultMsg = "Could not calculate critical point.";
         // TODO replace with resourcebundle entry
      }
      return new TetenburgResponse(longAsc, resultMsg);
   }

}
