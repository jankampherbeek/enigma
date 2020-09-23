/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.common;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.screens.UiScreensInjector;

public class UiCommonInjector {

   private UiCommonInjector() {
      // prevent instantiation
   }


   public static Dashboard injectDashboard(AppScope scope) {
      return new Dashboard(scope.getRosetta(), UiScreensInjector.injectChartsStart(scope), UiScreensInjector.injectStatsStart());
   }


}
