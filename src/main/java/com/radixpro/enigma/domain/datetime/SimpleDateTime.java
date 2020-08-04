/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.datetime;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DTO for date and time.
 */
public class SimpleDateTime implements Serializable {
   private final SimpleDate date;
   private final SimpleTime time;

   /**
    * Constructor expects an instantiated Date and an instantiated Time .
    *
    * @param simpleDate DTO with the date.
    * @param simpleTime DTO with the time.
    */
   public SimpleDateTime(final SimpleDate simpleDate, final SimpleTime simpleTime) {
      this.date = checkNotNull(simpleDate);
      this.time = checkNotNull(simpleTime);
   }

   public SimpleDate getDate() {
      return date;
   }

   public SimpleTime getTime() {
      return time;
   }
}
