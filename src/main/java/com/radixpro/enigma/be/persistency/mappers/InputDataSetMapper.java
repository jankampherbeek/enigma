/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.domain.astronpos.ChartInputData;
import com.radixpro.enigma.domain.astronpos.InputDataSet;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts from Json to InputDataSet
 */
public class InputDataSetMapper {


   public InputDataSet jsonToInputDataSet(@NotNull final JSONObject object) {
      return constructInputDataSet(object);
   }

   private InputDataSet constructInputDataSet(JSONObject object) {
      String name = (String) object.get("name");
      String description = (String) object.get("description");
      String origFileName = (String) object.get("origFileName");
      String dateTime = (String) object.get("dateTime");
      JSONArray allEntries = (JSONArray) object.get("inputData");
      List<ChartInputData> allInputData = constructEntries(allEntries);
      return new InputDataSet(name, description, origFileName, dateTime, allInputData);
   }

   private List<ChartInputData> constructEntries(final JSONArray inputData) {
      List<ChartInputData> chartInputData = new ArrayList<>();
      for (Object dataObject : inputData) {
         JSONObject jsonObject = (JSONObject) dataObject;
         int id = Integer.parseInt(jsonObject.get("id").toString());
         String name = (String) jsonObject.get("name");
         DateTimeJulian dateTimeJulian = createDateTime(jsonObject);
         Location location = createLocation(jsonObject);
         chartInputData.add(new ChartInputData(id, name, dateTimeJulian, location));
      }
      return chartInputData;
   }

   private DateTimeJulian createDateTime(JSONObject jsonObject) {
      double jdNr = (double) jsonObject.get("jdnr");
      String cal = (String) jsonObject.get("cal");
      return new DateTimeJulian(jdNr, cal);
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