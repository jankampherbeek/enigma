/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.input;

import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.Ratings;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Metadata for a chart, name, description etc.
 */
public class ChartMetaData implements Serializable {

   private final String name;
   private final String description;
   private final ChartTypes chartType;
   private final Ratings rating;
   private String inputData;

   public ChartMetaData(@NotNull final String name, @NotNull final String description, @NotNull final ChartTypes chartType, @NotNull final Ratings rating,
                        @NotNull String inputData) {
      this.name = name;
      this.description = description;
      this.chartType = chartType;
      this.rating = rating;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public ChartTypes getChartType() {
      return chartType;
   }

   public Ratings getRating() {
      return rating;
   }

   public String getInputData() {
      return inputData;
   }
}
