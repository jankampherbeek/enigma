/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.factories;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.screens.ChartsAspects;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedPairInterface;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import javafx.stage.Stage;

import java.util.List;

/**
 * Factory for ChartsAspects.
 */
public class ChartsAspectsFactory {

   public ChartsAspects getChartsAspects(final List<AnalyzedPairInterface> aspects, final MetaDataForAnalysis meta) {
      return new ChartsAspects(new Stage(), Rosetta.getRosetta(), aspects, meta);
   }

}
