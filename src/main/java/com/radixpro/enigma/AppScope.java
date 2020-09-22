/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma;

import com.radixpro.enigma.be.persistency.AppDb;

/**
 * Application Scope for DIY-DI
 */
public class AppScope {

   private String env;
   private Rosetta rosetta;
   private SessionState sessionState;
   private AppDb appDb;

   public AppScope() {

   }

   public String getEnv() {
      return env;
   }

   public void setEnv(String env) {
      this.env = env;
   }

   public SessionState getSessionState() {
      return sessionState;
   }

   public void setSessionState(SessionState sessionState) {
      this.sessionState = sessionState;
   }

   public Rosetta getRosetta() {
      return rosetta;
   }

   public void setRosetta(Rosetta rosetta) {
      this.rosetta = rosetta;
   }
//
//   public AppDb getAppDb() {
//      return appDb;
//   }
//
//   public void setAppDb(AppDb appDb) {
//      this.appDb = appDb;
//   }
}
