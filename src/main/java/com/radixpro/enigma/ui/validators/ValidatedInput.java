/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;


import org.jetbrains.annotations.NotNull;

/**
 * Parent for validators.
 */
public abstract class ValidatedInput {  // TODO check if class can be removed

   protected final String input;
   protected final boolean validated = false;

   /**
    * Constructor is used by all children to save inputted data.
    *
    * @param input The inputted data.
    */
   public ValidatedInput(@NotNull final String input) {
      this.input = input;
   }

//   protected abstract void validate();
   // todo check if this line should be enabled again

   public boolean isValidated() {
      return validated;
   }
}
