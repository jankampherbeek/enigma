/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.screenblocks;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.shared.common.SessionState;
import com.radixpro.enigma.ui.shared.InputStatus;

/**
 * Parent for input blocks.
 */
public abstract class InputBlock {

   protected static final String INPUT_STYLE = "inputDefault";
   protected Rosetta rosetta;
   protected SessionState state;
   protected InputStatus inputStatus = InputStatus.INCOMPLETE;

   /**
    * Constructor handles creation of block.
    */
   public InputBlock() {
      state = SessionState.getInstance();
      rosetta = Rosetta.getRosetta();
      initialize();
   }

   /**
    * Status of the input (READY, INCOMPLETE, or CANCELLED).
    *
    * @return the ctual status.
    */
   public InputStatus getInputStatus() {
      return inputStatus;
   }

   protected abstract void initialize();

}
