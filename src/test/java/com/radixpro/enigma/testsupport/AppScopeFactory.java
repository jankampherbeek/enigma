/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.testsupport;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.be.persistency.AppDb;

public class AppScopeFactory {

   private static AppScope appScope;

   private AppScopeFactory() {
      // prevent instantiation
   }

   public static AppScope getAppScope() {
      if (null == appScope) {
         createAppScope();
      }
      return appScope;
   }

   private static void createAppScope() {
      AppDb appDb = TestSupport.useDb();
      appScope = new AppScope();
      appScope.setRosetta(Rosetta.defineRosetta());     // TODO laten vervallen
   }
}
