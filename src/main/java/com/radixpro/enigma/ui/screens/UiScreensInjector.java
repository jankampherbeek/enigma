/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.shared.common.SharedCommonInjector;
import com.radixpro.enigma.ui.charts.ChartsSessionState;
import com.radixpro.enigma.xchg.api.XchgApiInjector;
import javafx.stage.Stage;


public class UiScreensInjector {

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(new Stage(), SharedCommonInjector.injectRosetta(),
            ChartsSessionState.getInstance(), XchgApiInjector.injectCalculatedChartApi(scope), UiScreensInjector.injectChartsTetenburg(scope));
      // TODO DI ChartsSessionState via AppScope
   }

   public static ChartsTetenburg injectChartsTetenburg(AppScope scope) {
      return new ChartsTetenburg(ChartsSessionState.getInstance(), new Stage(), SharedCommonInjector.injectRosetta(), XchgApiInjector.injectTetenburgApi(scope));
      // TODO DI ChartsSessionState via AppScope
   }

}
