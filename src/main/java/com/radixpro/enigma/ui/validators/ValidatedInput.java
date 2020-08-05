/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Parent for validators.
 */
public abstract class ValidatedInput {

   protected final String input;
   protected boolean validated = false;

   /**
    * Constructor is used by all children to save inputted data.
    *
    * @param input The inputted data.
    */
   public ValidatedInput(final String input) {
      this.input = checkNotNull(input);
   }

//   protected abstract void validate();
   // todo check if this line should be enabled again

   public boolean isValidated() {
      return validated;
   }
}
