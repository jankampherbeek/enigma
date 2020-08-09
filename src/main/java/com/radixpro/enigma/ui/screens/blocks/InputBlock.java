/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.references.InputStatus;

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
   public InputBlock(SessionState state) {
      this.state = state;
      rosetta = Rosetta.getRosetta();
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
