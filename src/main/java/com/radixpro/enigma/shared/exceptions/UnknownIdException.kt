/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared.exceptions;

/**
 * Exception for use of invalid id's when searching for an item in an Enum.
 */
public class UnknownIdException extends Exception {

   public UnknownIdException(final String message) {
      super(message);
   }
}
