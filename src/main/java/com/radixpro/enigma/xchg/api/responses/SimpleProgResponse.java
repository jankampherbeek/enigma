/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Response for calculated progressive positions.
 */
public class SimpleProgResponse {

   private final List<IPosition> positions;
   private final IProgCalcRequest request;

   /**
    * Constructor defines all properties.
    *
    * @param positions calculated progresive positions. PRE: not null.
    * @param request   the original request. PRE: not null.
    */
   public SimpleProgResponse(final List<IPosition> positions, final IProgCalcRequest request) {
      this.positions = checkNotNull(positions);
      this.request = checkNotNull(request);
   }

   public List<IPosition> getPositions() {
      return positions;
   }

   public IProgCalcRequest getRequest() {
      return request;
   }
}
