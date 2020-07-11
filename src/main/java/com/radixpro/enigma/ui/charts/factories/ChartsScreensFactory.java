/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.factories;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.screens.ChartsAspects;
import com.radixpro.enigma.ui.charts.screens.ChartsMidpoints;
import com.radixpro.enigma.ui.charts.screens.ChartsTetenburg;
import com.radixpro.enigma.xchg.domain.FullChartDepr;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import javafx.stage.Stage;

import java.util.List;

/**
 * Factory for screens for Charts.
 */
public class ChartsScreensFactory {

   public ChartsAspects createChartsAspects(final List<IAnalyzedPair> aspects, final MetaDataForAnalysis meta) {
      return new ChartsAspects(new Stage(), Rosetta.getRosetta(), aspects, meta);
   }

   public ChartsMidpoints createChartsMidpoints(final List<IAnalyzedPair> midpoints, final MetaDataForAnalysis meta) {
      return new ChartsMidpoints(new Stage(), Rosetta.getRosetta(), midpoints, meta);
   }

   public ChartsTetenburg getChartsTetenburg(final MetaDataForAnalysis meta, final FullChartDepr fullChartDepr) {
      return new ChartsTetenburg(new Stage(), Rosetta.getRosetta(), meta, fullChartDepr);
   }

   ;
}
