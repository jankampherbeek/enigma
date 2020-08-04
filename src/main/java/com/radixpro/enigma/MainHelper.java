/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma;

import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.shared.common.SessionState;
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
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      scope = new AppScope();
      scope.setSessionState(SessionState.getInstance());
      scope.setRosetta(Rosetta.getRosetta());
      scope.setAppDb(AppDb.initAppDb(env));
      scope.setEnv(env);
      LOG.info("Started Enigma.");
      Injector.injectAppVersion(scope);
      Injector.injectDashboard(scope).showDashboard();
   }

}
