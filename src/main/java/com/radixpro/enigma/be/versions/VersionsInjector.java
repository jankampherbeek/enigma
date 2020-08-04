/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.versions;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.xchg.api.XchgApiInjector;

public class VersionsInjector {

   public static Updater injectUpdater(AppScope scope) {
      return new Updater(scope.getAppDb());
   }

   public static AppVersion injectAppVersion(AppScope scope) {
      return new AppVersion(injectUpdater(scope), XchgApiInjector.injectVersionApi(scope));
   }
}
