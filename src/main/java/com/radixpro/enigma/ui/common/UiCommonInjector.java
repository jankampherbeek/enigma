/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.common;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.shared.common.SharedCommonInjector;
import com.radixpro.enigma.ui.screens.UiScreensInjector;

public class UiCommonInjector {

   public static Dashboard injectDashboard(AppScope scope) {
      return new Dashboard(SharedCommonInjector.injectRosetta(), UiScreensInjector.injectChartsStart(scope));
   }


}
