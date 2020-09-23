/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.EphProgCalcHandler;
import com.radixpro.enigma.be.handlers.ProgAspectHandler;
import com.radixpro.enigma.be.handlers.SecundaryDateHandler;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.reqresp.*;
import org.jetbrains.annotations.NotNull;

public class SecundaryApi {

   private final EphProgCalcHandler calcHandler;
   private final SecundaryDateHandler secundaryDateHandler;
   private final ProgAspectHandler aspectHandler;


   public SecundaryApi(@NotNull final EphProgCalcHandler calcHandler, @NotNull final SecundaryDateHandler secDateHandler,
                       @NotNull final ProgAspectHandler aspectHandler) {
      this.secundaryDateHandler = secDateHandler;
      this.aspectHandler = aspectHandler;
      this.calcHandler = calcHandler;
   }

   /**
    * Calculate positions for secondary progressions.
    *
    * @param request Request with relevant data.
    * @return The calculated positions.
    */
   public SimpleProgResponse calculateSecundary(final SecundaryCalcRequest request) {
      // FIXME check what these first four lines are doing
      DateTimeJulian eventDateTime = request.getDateTime();
      DateTimeJulian birthDateTime = request.getBirthDateTime();
      DateTimeJulian secDateTime = secundaryDateHandler.calcSecundaryDate(birthDateTime, eventDateTime);
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
