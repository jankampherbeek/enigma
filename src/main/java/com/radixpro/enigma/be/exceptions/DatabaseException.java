/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.exceptions;

/**
 * General exception for all database errors.
 */
public class DatabaseException extends Exception {

   /**
    * Constructor takes message and passis it to the parent Exception.
    *
    * @param message Original message from the exception.
    */
   public DatabaseException(final String message) {
      super(message);
   }

}
