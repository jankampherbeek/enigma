/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reader for Json files.
 */
public class JsonReader {

   public JSONObject readObjectFromFile(File inputData) {
      JSONParser parser = new JSONParser();
      try {
         Object object = parser.parse(new FileReader(inputData));
         return (JSONObject) object;
      } catch (ParseException pe) {
         throw new RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.getMessage());
      } catch (IOException ioe) {
         throw new RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.getMessage());
      }
   }

   public JSONArray readArrayFromFile(File inputData) {
      JSONParser parser = new JSONParser();
      try {
         Object object = parser.parse(new FileReader(inputData));
         return (JSONArray) object;
      } catch (ParseException pe) {
         throw new RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.getMessage());
      } catch (IOException ioe) {
         throw new RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.getMessage());
      }
   }

}
