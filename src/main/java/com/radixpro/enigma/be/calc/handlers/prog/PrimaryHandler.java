/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.prog;

import static com.google.common.base.Preconditions.checkNotNull;

public class PrimaryHandler {

   private final PrimaryPositionsHandler primaryPositionsHandler;
   private final TimeKeyHandler timeKeyHandler;

   /**
    * Instantiate via factory.
    *
    * @param primaryPositionsHandler Handler for the astronomical calculations. PRE: not null.
    * @param timeKeyHandler          Handler for time key related calculations. PRE: nut null.
    * @see HandlerProgFactory
    */
   public PrimaryHandler(final PrimaryPositionsHandler primaryPositionsHandler, final TimeKeyHandler timeKeyHandler) {
      this.primaryPositionsHandler = checkNotNull(primaryPositionsHandler);
      this.timeKeyHandler = checkNotNull(timeKeyHandler);
   }

   // TODO perform calculations


}
