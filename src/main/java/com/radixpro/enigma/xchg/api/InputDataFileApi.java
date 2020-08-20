/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.InputDataFileHandler;
import com.radixpro.enigma.domain.reqresp.InputDataFileRequest;
import com.radixpro.enigma.domain.reqresp.InputDataFileResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Api for converting and saving input data.
 */
public class InputDataFileApi {

   private final InputDataFileHandler handler;

   public InputDataFileApi(final InputDataFileHandler handler) {
      this.handler = checkNotNull(handler);
   }

   public InputDataFileResponse addDataFile(InputDataFileRequest request) {
      return handler.handleDataFile(request);
   }


}
