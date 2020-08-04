/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.ui.shared.screenblocks.DateTimeInput;
import com.radixpro.enigma.ui.shared.screenblocks.LocationInput;
import com.radixpro.enigma.ui.shared.screenblocks.ProgMetaInput;

/**
 * Factory for screens for Charts.
 */
public class ChartsScreensFactory {

//   public static ChartsAspects createChartsAspects(final List<IAnalyzedPair> aspects, final MetaDataForAnalysis meta) {
//      return new ChartsAspects(new Stage(), Rosetta.getRosetta(), aspects, meta);
//   }

//   public static ChartsMidpoints createChartsMidpoints(final List<IAnalyzedPair> midpoints, final MetaDataForAnalysis meta) {
//      return new ChartsMidpoints((), Rosetta.getRosetta(), midpoints, meta);
//   }

//   public static ChartsTetenburg getChartsTetenburg(final MetaDataForAnalysis meta, final FullChart fullChart) {
//      return new ChartsTetenburg(new Stage(), Rosetta.getRosetta(), meta, fullChart);
//   }

   public static ChartsTransitsInput getChartsTransitsInput() {
      return new ChartsTransitsInput(new ProgMetaInput(), new LocationInput(), new DateTimeInput());
   }


}
