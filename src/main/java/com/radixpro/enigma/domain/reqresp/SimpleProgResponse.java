/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.astronpos.IPosition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Response for calculated progressive positions.
 */
public class SimpleProgResponse {

   private final List<IPosition> positions;
   private final IProgCalcRequest request;

   public SimpleProgResponse(@NotNull final List<IPosition> positions,
                             @NotNull final IProgCalcRequest request) {
      this.positions = positions;
      this.request = request;
   }

   public List<IPosition> getPositions() {
      return positions;
   }

   public IProgCalcRequest getRequest() {
      return request;
   }
}
