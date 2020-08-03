/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma;

import com.radixpro.enigma.be.versions.Updater;
import com.radixpro.enigma.shared.AppDb;
import com.radixpro.enigma.shared.AppVersion;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * MainHelper, required for DIY-DI
 */
public class MainHelper extends Application {

   private static final Logger LOG = Logger.getLogger(App.class);
   private String env;
   private AppScope scope;

   public MainHelper() {
   }

   public void run(String[] args) {
      env = (args.length > 0) ? args[0] : "prod";
//      applicationScope.setEnv(env);
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      scope = new AppScope();
      LOG.info("Started Enigma.");
      new AppVersion(new Updater(AppDb.initAppDb(env)));    // TODO DI do not pass env but use LocationScope
      showDashboard();
   }

   private void showDashboard() {
      Injector.injectDashboard(scope).showDashboard();
   }
}
