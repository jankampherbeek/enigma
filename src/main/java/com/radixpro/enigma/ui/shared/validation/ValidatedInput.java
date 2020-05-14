/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Parent for validators.
 */
public abstract class ValidatedInput {

   protected static final String DATE_SEPARATOR = "/";
   protected static final int HOUR_MIN = 0;
   protected static final int HOUR_MAX = 23;
   protected static final int LAT_DEGREE_MIN = -89;
   protected static final int LAT_DEGREE_MAX = 89;
   protected static final int LONG_DEGREE_MIN = -180;
   protected static final int LONG_DEGREE_MAX = 180;
   protected static final int MINUTE_MIN = 0;
   protected static final int MINUTE_MAX = 59;
   protected static final int MINUTES_PER_HOUR = 60;
   protected static final int SECOND_MIN = 0;
   protected static final int SECOND_MAX = 59;
   protected static final int SECONDS_PER_HOUR = 3600;
   protected static final String SEXAG_SEPARATOR = ":";
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

   protected abstract void validate();

   public boolean isValidated() {
      return validated;
   }
}
