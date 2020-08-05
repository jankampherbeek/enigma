/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared;

public class UiDictionary {

   public static final String FONT_STYLE_DATA = "-fx-font-family: \"Courier\";";
   public static final String FONT_STYLE_GLYPH = " -fx-font-family: \"EnigmaAstrology\";  -fx-font-size: 14;";
   public static final String INPUT_DEFAULT_STYLE = "-fx-background-radius:5; ";
   public static final String INPUT_ERROR_STYLE = "-fx-background-radius:5;  -fx-background-color:yellow;";
   public static final String STYLESHEET = "css/enigma.css";

   public static final String DATE_SEPARATOR = "/";
   public static final int HOUR_MIN = 0;
   public static final int HOUR_MAX = 23;
   public static final int LAT_DEGREE_MIN = -89;
   public static final int LAT_DEGREE_MAX = 89;
   public static final int LONG_DEGREE_MIN = -180;
   public static final int LONG_DEGREE_MAX = 180;
   public static final int MINUTE_MIN = 0;
   public static final int MINUTE_MAX = 59;
   public static final int MINUTES_PER_HOUR = 60;
   public static final int SECOND_MIN = 0;
   public static final int SECOND_MAX = 59;
   public static final int SECONDS_PER_HOUR = 3600;
   public static final String SEXAG_SEPARATOR = ":";

   public static final double BUTTONBAR_HEIGHT = 50.0;
   public static final double GAP = 6.0;
   public static final double INPUT_DATETIME_HEIGHT = 200.0;
   public static final double INPUT_DATA_WIDTH = 588.0;
   public static final double INPUT_HALF_DATA_WIDTH = 288.0;
   public static final double INPUT_HEIGHT = 25.0;
   public static final double INPUT_LOCATION_HEIGHT = 130.0;
   public static final double INPUT_MINOR_DATA_WIDTH = 236.0;
   public static final double INPUT_MICRO_DATA_WIDTH = 40;
   public static final double INPUT_WIDTH = 600.0;
   public static final double SUBTITLE_HEIGHT = 30.0;
   public static final double TITLE_HEIGHT = 45.0;


   private UiDictionary() {
      // prevent instantiation
   }

}
