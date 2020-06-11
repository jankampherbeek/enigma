/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma;

import com.radixpro.enigma.shared.AppControllerFactory;
import com.radixpro.enigma.ui.shared.Dashboard;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class App extends Application {
   private static final Logger LOG = Logger.getLogger(App.class);

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      LOG.info("Started Enigma.");
      new AppControllerFactory().getAppController();
      showDashboard();
   }

   private void showDashboard() {
      new Dashboard();
   }


}
