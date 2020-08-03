/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma;

import com.radixpro.enigma.ui.common.Dashboard;
import com.radixpro.enigma.ui.common.UiCommonInjector;

/**
 * Dependency Injection.
 * Based on DIY-DI (Do It Yourself Dependency Injection) by Chad Parry.
 * More info: https://blacksheep.parry.org/wp-content/uploads/2010/03/DIY-DI.pdf
 */
public class Injector {

   //   public static MainHelper injectMainHelper(AppScope scope) { return new MainHelper(UiCommonInjector.injectDashboard(scope)); }
   public static Dashboard injectDashboard(AppScope scope) {
      return UiCommonInjector.injectDashboard(scope);
   }

}
