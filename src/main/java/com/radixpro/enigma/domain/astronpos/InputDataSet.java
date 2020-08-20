/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Input dataset to be saved in Json format.
 */
public class InputDataSet {

   public final String name;
   public final String description;
   public final String origFileName;
   public final String dateTime;
   public final List<ChartInputData> inputData;

   public InputDataSet(final String name, final String description, final String origFileName, final String dateTime, final List<ChartInputData> inputData) {
      this.name = checkNotNull(name);
      this.description = checkNotNull(description);
      this.origFileName = checkNotNull(origFileName);
      this.dateTime = checkNotNull(dateTime);
      this.inputData = checkNotNull(inputData);
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
