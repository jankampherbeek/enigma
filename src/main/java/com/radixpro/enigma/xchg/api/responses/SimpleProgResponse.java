/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Response for calculated progressive positions.
 * TODO replace SimplePosVo with iPosition
 */
public class SimpleProgResponse {

   private final List<SimplePosVo> positions;
   private final IProgCalcRequest request;

   /**
    * Constructor defines all properties.
    *
    * @param positions calculated progresive positions. PRE: not null.
    * @param request   the original request. PRE: not null.
    */
   public SimpleProgResponse(final List<SimplePosVo> positions, final IProgCalcRequest request) {
      this.positions = checkNotNull(positions);
      this.request = checkNotNull(request);
   }

   public List<SimplePosVo> getPositions() {
      return positions;
   }

   public IProgCalcRequest getRequest() {
      return request;
   }
}
