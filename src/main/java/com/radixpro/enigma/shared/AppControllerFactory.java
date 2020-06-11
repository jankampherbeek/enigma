/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import com.radixpro.enigma.be.versions.Updater;

public class AppControllerFactory {

   public AppController getAppController() {
      final AppProperties appProperties = new AppProperties();
      final AppDb appDb = new AppDb(appProperties);
      final Updater updater = new Updater(appDb);
      final AppVersion appVersion = new AppVersion(appDb, updater);
      return AppController.getAppController(appProperties, appDb, appVersion);
   }
}
