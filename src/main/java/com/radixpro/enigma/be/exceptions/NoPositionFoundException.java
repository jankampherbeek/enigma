/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.exceptions;

public class NoPositionFoundException extends Exception {

   /**
    * Constructor takes message and passes it to the parent Exception.
    *
    * @param message Original message from the exception.
    */
   public NoPositionFoundException(final String message) {
      super(message);
   }


}
