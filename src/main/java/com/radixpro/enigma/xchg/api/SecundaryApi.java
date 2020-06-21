/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.handlers.ProgAspectHandler;
import com.radixpro.enigma.be.calc.handlers.EphProgCalcHandler;
import com.radixpro.enigma.be.calc.handlers.SecundaryDateHandler;
import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.api.requests.ProgAnalyzeRequest;
import com.radixpro.enigma.xchg.api.requests.SecundaryCalcRequest;
import com.radixpro.enigma.xchg.api.responses.EphProgAspectResponse;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.domain.FullDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

public class SecundaryApi {

   private final EphProgCalcHandler calcHandler;
   private final SecundaryDateHandler secundaryDateHandler;
   private final ProgAspectHandler aspectHandler;

   /**
    * Create via factory.
    *
    * @param calcHandler    Handler for calculations. PRE: not null.
    * @param secDateHandler Handler for calculating the secundary date. PRE: not null.
    * @param aspectHandler  Handler to find the aspects. PRE: not null.
    * @see com.radixpro.enigma.xchg.api.factories.ApiProgFactory
    */
   public SecundaryApi(final EphProgCalcHandler calcHandler, final SecundaryDateHandler secDateHandler, final ProgAspectHandler aspectHandler) {  // TODO create factory
      this.calcHandler = checkNotNull(calcHandler);
      this.secundaryDateHandler = checkNotNull(secDateHandler);
      this.aspectHandler = checkNotNull(aspectHandler);
   }

   /**
    * Calculate positions for secondary progressions.
    *
    * @param request Request with relevant data.
    * @return The calculated positions.
    */
   public SimpleProgResponse calculateSecundary(final SecundaryCalcRequest request) {
      FullDateTime eventDateTime = request.getDateTime();
      FullDateTime birthDateTime = request.getBirthDateTime();
      FullDateTime secDateTime = secundaryDateHandler.calcSecundaryDate(birthDateTime, eventDateTime);
      IProgCalcRequest secRequest = new SecundaryCalcRequest(secDateTime, birthDateTime, request.getLocation(), request.getSettings());
      return calcHandler.retrievePositions(secRequest);
   }

   /**
    * Define aspects for secondary progressions.
    *
    * @param request Reques twith relevant data.
    * @return Calcualted aspects.
    */
   public EphProgAspectResponse defineAspects(final ProgAnalyzeRequest request) {
      return aspectHandler.analyzeAspects(request);
   }
}
