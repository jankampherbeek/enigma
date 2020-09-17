/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Input dataset to be saved in Json format.
 */
public class InputDataSet {

   public final String name;
   public final String description;
   public final String origFileName;
   public final String dateTime;
   public final List<ChartInputData> inputData;

   public InputDataSet(@NotNull final String name,
                       @NotNull final String description,
                       @NotNull final String origFileName,
                       @NotNull final String dateTime,
                       @NotNull final List<ChartInputData> inputData) {
      this.name = name;
      this.description = description;
      this.origFileName = origFileName;
      this.dateTime = dateTime;
      this.inputData = inputData;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public String getOrigFileName() {
      return origFileName;
   }

   public String getDateTime() {
      return dateTime;
   }

   public List<ChartInputData> getInputData() {
      return inputData;
   }
}
