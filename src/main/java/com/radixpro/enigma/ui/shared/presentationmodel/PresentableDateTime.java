/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.shared.common.Rosetta;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presents date and time as Strings.
 */
public class PresentableDateTime {

   private final String date;
   private final String time;

   public PresentableDateTime(final FullDateTime dateTime) {
      checkNotNull(dateTime);
      date = constructDateText(dateTime.getSimpleDateTime().getDate());
      time = constructTimeText(dateTime);
   }

   private String constructDateText(final SimpleDate date) {
      int year = date.getYear();
      int month = date.getMonth();
      int day = date.getDay();
      String cal = date.isGregorian() ? "G" : "J";
      return String.format("%04d/%02d/%02d %s", year, month, day, cal);
   }

   private String constructTimeText(final FullDateTime fullDateTime) {
      int hour = fullDateTime.getSimpleDateTime().getTime().getHour();
      int minute = fullDateTime.getSimpleDateTime().getTime().getMinute();
      int second = fullDateTime.getSimpleDateTime().getTime().getSecond();
      String zoneTxt = Rosetta.getRosetta().getText(fullDateTime.getTimeZone().getNameForRB());
      String dstKey = fullDateTime.isDst() ? "ui.shared.dst" : "ui.shared.nodst";
      String dstTxt = Rosetta.getRosetta().getText(dstKey);
      return String.format("%02d:%02d:%02d %s %s", hour, minute, second, dstTxt, zoneTxt);
   }

   public String getDate() {
      return date;
   }

   public String getTime() {
      return time;
   }
}
