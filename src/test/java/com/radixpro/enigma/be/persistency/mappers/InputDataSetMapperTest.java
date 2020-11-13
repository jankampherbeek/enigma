/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.statistics.core.InputDataSet;
import com.radixpro.enigma.statistics.persistency.InputDataSetMapper;
import com.radixpro.enigma.testsupport.TestSupport;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Before;

//@RunWith(JfxTestRunner.class)    // TODO enable JfxTestRunner
public class InputDataSetMapperTest {

   private JSONObject jsonObject;
   private String jsonString = "{\n" +
         "  \"name\" : \"aaa\",\n" +
         "  \"description\" : \"abc\",\n" +
         "  \"origFileName\" : \"C:\\\\enigma-data\\\\inputdata\\\\test1.csv\",\n" +
         "  \"dateTime\" : \"2020-08-20T10:31:38.399920400\",\n" +
         "  \"inputData\" : [ {\n" +
         "    \"id\" : 22,\n" +
         "    \"name\" : \"Jan\",\n" +
         "    \"dateTime\" : {\n" +
         "      \"simpleDateTime\" : {\n" +
         "        \"date\" : {\n" +
         "          \"year\" : 1953,\n" +
         "          \"month\" : 1,\n" +
         "          \"day\" : 29,\n" +
         "          \"gregorian\" : true\n" +
         "        },\n" +
         "        \"time\" : {\n" +
         "          \"hour\" : 8,\n" +
         "          \"minute\" : 37,\n" +
         "          \"second\" : 30\n" +
         "        }\n" +
         "      },\n" +
         "      \"timeZone\" : \"CET\",\n" +
         "      \"dst\" : false,\n" +
         "      \"offsetForLmt\" : 0.0,\n" +
         "      \"jdUt\" : 2434406.8177083335,\n" +
         "      \"formattedDateTime\" : \"1953-1-29 G / 8:37:30 / +00:00: UT/Coordinated Universal Time, GMT/Greenwich Mean Time\"\n" +
         "    },\n" +
         "    \"location\" : {\n" +
         "      \"longInput\" : {\n" +
         "        \"degrees\" : 6,\n" +
         "        \"minutes\" : 54,\n" +
         "        \"seconds\" : 0,\n" +
         "        \"direction\" : \"E\",\n" +
         "        \"value\" : 6.9\n" +
         "      },\n" +
         "      \"latInput\" : {\n" +
         "        \"degrees\" : 52,\n" +
         "        \"minutes\" : 13,\n" +
         "        \"seconds\" : 0,\n" +
         "        \"direction\" : \"N\",\n" +
         "        \"value\" : 52.21666666666667\n" +
         "      },\n" +
         "      \"formattedLocation\" : \" / 6:54:0 E / 52:13:0 N\",\n" +
         "      \"geoLat\" : 52.21666666666667,\n" +
         "      \"geoLong\" : 6.9\n" +
         "    }\n" +
         "  }, {\n" +
         "    \"id\" : 23,\n" +
         "    \"name\" : \"Piet\",\n" +
         "    \"dateTime\" : {\n" +
         "      \"simpleDateTime\" : {\n" +
         "        \"date\" : {\n" +
         "          \"year\" : 1989,\n" +
         "          \"month\" : 7,\n" +
         "          \"day\" : 1,\n" +
         "          \"gregorian\" : true\n" +
         "        },\n" +
         "        \"time\" : {\n" +
         "          \"hour\" : 12,\n" +
         "          \"minute\" : 30,\n" +
         "          \"second\" : 0\n" +
         "        }\n" +
         "      },\n" +
         "      \"timeZone\" : \"UT\",\n" +
         "      \"dst\" : true,\n" +
         "      \"offsetForLmt\" : 0.0,\n" +
         "      \"jdUt\" : 2447708.9791622963,\n" +
         "      \"formattedDateTime\" : \"1989-7-1 G / 12:30:0 DST / +00:00: UT/Coordinated Universal Time, GMT/Greenwich Mean Time\"\n" +
         "    },\n" +
         "    \"location\" : {\n" +
         "      \"longInput\" : {\n" +
         "        \"degrees\" : 5,\n" +
         "        \"minutes\" : 52,\n" +
         "        \"seconds\" : 0,\n" +
         "        \"direction\" : \"E\",\n" +
         "        \"value\" : 5.866666666666667\n" +
         "      },\n" +
         "      \"latInput\" : {\n" +
         "        \"degrees\" : 51,\n" +
         "        \"minutes\" : 48,\n" +
         "        \"seconds\" : 0,\n" +
         "        \"direction\" : \"N\",\n" +
         "        \"value\" : 51.8\n" +
         "      },\n" +
         "      \"formattedLocation\" : \" / 5:52:0 E / 51:48:0 N\",\n" +
         "      \"geoLat\" : 51.8,\n" +
         "      \"geoLong\" : 5.866666666666667\n" +
         "    }\n" +
         "  } ]\n" +
         "}";

   private InputDataSet inputDataSet;
   private InputDataSetMapper converter;

   @Before
   public void setUp() throws Exception {
      TestSupport.initRosetta();
      converter = new InputDataSetMapper();
      jsonObject = (JSONObject) JSONValue.parse(jsonString);
      inputDataSet = converter.jsonToInputDataSet(jsonObject);
   }

   // FIXME fix format for Json and enable tests.

//   @Test
//   public void jsonToInputDataSet() {
//      assertEquals("aaa", inputDataSet.getName());
//      assertEquals("abc", inputDataSet.getDescription());
//      assertEquals(2, inputDataSet.getInputData().size());
//      assertEquals(22, inputDataSet.getInputData().get(0).getId());
//      assertEquals("Jan", inputDataSet.getInputData().get(0).getName());
//   }
//
//   @Test
//   public void jsonToDateTime() {
//      DateTimeJulian dateTime = inputDataSet.getInputData().get(0).getDateTime();
//      assertEquals(2434406.8177083335, dateTime.getJd(), DELTA_8_POS);
//      assertEquals("G", dateTime.getCalendar());
//   }
//
//   @Test
//   public void jsonToLocation() {
//      Location location = inputDataSet.getInputData().get(1).getLocation();
//      assertEquals(51.8, location.getGeoLat(), DELTA_8_POS);
//      assertEquals(5.866666666666667, location.getGeoLon(), DELTA_8_POS);
//   }
}