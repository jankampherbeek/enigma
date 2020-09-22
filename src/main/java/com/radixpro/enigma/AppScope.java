/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma;

/**
 * Application Scope for DIY-DI
 */
public class AppScope {

   private String env;
   private Rosetta rosetta;

   public AppScope() {

   }

   public String getEnv() {
      return env;
   }

   public void setEnv(String env) {
      this.env = env;
   }

   public Rosetta getRosetta() {
      return rosetta;
   }

   public void setRosetta(Rosetta rosetta) {
      this.rosetta = rosetta;
   }

}
