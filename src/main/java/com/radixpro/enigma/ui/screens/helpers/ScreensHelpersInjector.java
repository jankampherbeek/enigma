/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.AppScope;

public class ScreensHelpersInjector {

   private ScreensHelpersInjector() {
      // prevent instantiation
   }

   public static ChartDataHelper injectChartDataHelper(AppScope scope) {
      return new ChartDataHelper(scope.getSessionState());
   }

}
