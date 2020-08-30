/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.domain.astronpos.ChartInputData;
import com.radixpro.enigma.domain.astronpos.InputDataSet;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.TimeZones;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts from Json to InputDataSet
 */
public class InputDataSetMapper {


   public InputDataSet jsonToInputDataSet(final JSONObject object) {
      return constructInputDataSet(object);
   }

   private InputDataSet constructInputDataSet(JSONObject object) {
      // FIXME handle MetaData
      String name = (String) object.get("name");
      String description = (String) object.get("description");
      String origFileName = (String) object.get("origFileName");
      String dateTime = (String) object.get("dateTime");
      JSONArray allEntries = (JSONArray) object.get("inputData");
      List<ChartInputData> allInputData = constructEntries(allEntries);
      return new InputDataSet(name, description, origFileName, dateTime, allInputData);
   }

   private List<ChartInputData> constructEntries(final JSONArray inputData) {
      // FIXME handle MetaData
      List<ChartInputData> chartInputData = new ArrayList<>();
      for (Object dataObject : inputData) {
         JSONObject jsonObject = (JSONObject) dataObject;
         int id = Integer.parseInt(jsonObject.get("id").toString());
         String name = (String) jsonObject.get("name");
         FullDateTime fullDateTime = createDateTime(jsonObject);
         Location location = createLocation(jsonObject);
         chartInputData.add(new ChartInputData(id, name, fullDateTime, location));
      }
      return chartInputData;
   }

   private FullDateTime createDateTime(JSONObject jsonObject) {
      JSONObject jsonFullDateTime = (JSONObject) jsonObject.get("dateTime");
      JSONObject jsonSimpleDateTime = (JSONObject) jsonFullDateTime.get("simpleDateTime");
      JSONObject jsonSimpleDate = (JSONObject) jsonSimpleDateTime.get("date");
      int year = Integer.parseInt(jsonSimpleDate.get("year").toString());
      int month = Integer.parseInt(jsonSimpleDate.get("month").toString());
      int day = Integer.parseInt(jsonSimpleDate.get("day").toString());
      boolean gregorian = Boolean.parseBoolean(jsonSimpleDate.get("gregorian").toString());
      final SimpleDate simpleDate = new SimpleDate(year, month, day, gregorian);
      JSONObject jsonSimpleTime = (JSONObject) jsonSimpleDateTime.get("time");
      int hour = Integer.parseInt(jsonSimpleTime.get("hour").toString());
      int minute = Integer.parseInt(jsonSimpleTime.get("minute").toString());
      int second = Integer.parseInt(jsonSimpleTime.get("second").toString());
      SimpleTime simpleTime = new SimpleTime(hour, minute, second);
      SimpleDateTime simpleDateTime = new SimpleDateTime(simpleDate, simpleTime);
      String timeZoneAbbr = jsonFullDateTime.get("timeZone").toString();
      boolean dst = Boolean.parseBoolean(jsonFullDateTime.get("dst").toString());
      double offsetForLmt = Double.parseDouble(jsonFullDateTime.get("offsetForLmt").toString());
      TimeZones timeZone = TimeZones.valueOf(timeZoneAbbr);
      return new FullDateTime(simpleDateTime, timeZone, dst, offsetForLmt);
   }

   private Location createLocation(JSONObject jsonObject) {
      JSONObject jsonLocation = (JSONObject) jsonObject.get("location");
      JSONObject jsonLongInput = (JSONObject) jsonLocation.get("longInput");
      String lonDirection = jsonLongInput.get("direction").toString();
      int dirCorrection = "Ww".contains(lonDirection) ? -1 : 1;
      double lonValue = (Double.parseDouble(jsonLongInput.get("value").toString())) * dirCorrection;
      JSONObject jsonLatInput = (JSONObject) jsonLocation.get("latInput");
      String latDirection = jsonLatInput.get("direction").toString();
      dirCorrection = "SsZz".contains(latDirection) ? -1 : 1;
      double latValue = (Double.parseDouble(jsonLatInput.get("value").toString())) * dirCorrection;
      return new Location(latValue, lonValue);
   }

}