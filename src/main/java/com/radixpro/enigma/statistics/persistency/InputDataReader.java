/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency;

import com.opencsv.CSVReader;
import com.radixpro.enigma.domain.input.ChartInputData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.DateTimeParts;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.shared.converters.Csv2LocationConverter;
import com.radixpro.enigma.statistics.core.InputDataSet;
import com.radixpro.enigma.statistics.process.InputDataException;
import com.radixpro.enigma.ui.helpers.DateTimeCreator;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Reader for csv files using CSV_STANDARD_CHART or CSV_STANDARD_EVENT
 */
public class InputDataReader {

   private final Csv2LocationConverter locationConverter;
   private List<String> errorLines;
   private boolean noErrors;

   public InputDataReader(@NotNull final Csv2LocationConverter locationConverter) {
      this.locationConverter = locationConverter;
   }

   /**
    * Read csv and create an InputDataSet from the contents of the csv.
    *
    * @param dataName    Name to identify the data. PRE: not empty, not just spaces.
    * @param description Description of the data. PRE: not empty, not just spaces.
    * @param fileName    Full path and name of the file to read. PRE: not empty, not just spaces.
    * @return Populated instance of InputDataSet.
    * @throws InputDataException if an exception occurs.
    */
   public InputDataSet readCsv(@NotNull final String dataName, @NotNull final String description, @NotNull final String fileName) throws InputDataException {
      checkArgument(!dataName.isBlank());
      checkArgument(!description.isBlank());
      checkArgument(!fileName.isBlank());
      errorLines = new ArrayList<>();
      noErrors = true;
      List<String[]> lines;
      try {
         try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            lines = reader.readAll();
         }
      } catch (IOException ie) {
         throw new InputDataException("IOException when reading form file : " + fileName);
      }
      return processLines(dataName, description, fileName, lines);
   }

   private InputDataSet processLines(final String dataName, final String description, final String fileName, final List<String[]> lines)
         throws InputDataException {
      List<ChartInputData> charts = new ArrayList<>();
      for (String[] line : lines) {
         if (!(line.length < 4 || line[0].equalsIgnoreCase("id"))) {
            charts.add(processSingleLine(line));
         }
      }
      LocalDateTime dateTimeStamp = LocalDateTime.now();
      return new InputDataSet(dataName, description, fileName, dateTimeStamp.toString(), charts);
   }

   private ChartInputData processSingleLine(final String[] line) throws InputDataException {
      ChartInputData cInputData;
      try {
         int id = Integer.parseInt(line[0].trim());
         String name = line[1].trim();
         String lonTxt = line[2].trim();
         String latTxt = line[3].trim();
         String dateTxt = line[4].trim();
         String cal = line[5].trim();
         String timeTxt = line[6].trim();
         TimeZones zone = TimeZones.timeZoneForName(line[7].trim());
         boolean dst = line[8].trim().equalsIgnoreCase("Y");
         Location location = locationConverter.convert(lonTxt, latTxt);
         DateTimeJulian dateTimeJulian = DateTimeCreator.INSTANCE.createDateTimeJulian(dateTxt, cal, timeTxt, zone, dst, 0.0);
         DateTimeParts dateTimeParts = DateTimeCreator.INSTANCE.createDateTimeParts(dateTxt, timeTxt, zone, dst);
         cInputData = new ChartInputData(id, name, dateTimeParts, dateTimeJulian, location);
      } catch (Exception e) {
         noErrors = false;
         errorLines.add(Arrays.toString(line));
         throw new InputDataException("Error when parsing line : " + Arrays.toString(line));
      }
      return cInputData;
   }


   public List<String> getErrorLines() {
      return errorLines;
   }

   public boolean isNoErrors() {
      return noErrors;
   }
}
