/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Response after reading a dtafile and converting it to Json.
 */
public class InputDataFileResponse {

   private final String resultMsg;
   private final List<String> errorLines;
   private final boolean success;

   public InputDataFileResponse(final String resultMsg, final List<String> errorLines, final boolean success) {
      checkArgument(null != resultMsg && !resultMsg.isEmpty());
      this.resultMsg = resultMsg;
      this.errorLines = checkNotNull(errorLines);
      this.success = success;
   }

   public String getResultMsg() {
      return resultMsg;
   }

   public List<String> getErrorLines() {
      return errorLines;
   }

   public boolean isSuccess() {
      return success;
   }
}
