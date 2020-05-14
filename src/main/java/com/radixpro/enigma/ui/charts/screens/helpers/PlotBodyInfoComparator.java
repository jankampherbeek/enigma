/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import java.util.Comparator;

/**
 * Comparator for sorting PlotBodyInfo. Compares the value of the angle from the ascendant..
 */
public class PlotBodyInfoComparator implements Comparator<PlotBodyInfo> {

   @Override
   public int compare(PlotBodyInfo first, PlotBodyInfo second) {
      return (int) (first.getAngleFromAsc() - second.getAngleFromAsc());
   }

}
