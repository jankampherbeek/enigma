/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.testsupport;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.be.persistency.AppDb;
import com.radixpro.enigma.be.versions.Updater;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Parent for classes that perform tests using then database.
 */
public class TestSupport {

   private static Connection con;
   private static AppDb appDb;
   private static boolean initialized = false;

   private TestSupport() {
      // prevent instantiation
   }

   public static void initRosetta() {
      if (null == appDb) appDb = AppDb.initAppDb("test");
      Rosetta.defineRosetta(appDb);
      Rosetta.getRosetta().setLanguage("en");
   }

   public static AppDb useDb() {
      if (!initialized) {
         appDb = AppDb.initAppDb("test");
         con = appDb.getConnection();
         initDatabase();
         Rosetta.defineRosetta(appDb);
         initialized = true;
      }
      return appDb;
   }

   private static void initDatabase() {
      emptyDatabase();
      new Updater(appDb).updateStep20202();
   }

   private static void emptyDatabase() {
      File traceFile = new File("test" + File.separator + "db" + File.separator + "enigmadb.trace.db");
      if (traceFile.exists()) traceFile.delete();
      File dbFile = new File("test" + File.separator + "db" + File.separator + "enigmadb.mv.db");
      if (dbFile.exists()) dropTables();
   }

   private static void dropTables() {
      dropIt("configsaspects");
      dropIt("configspoints");
      dropIt("chartsevents");
      dropIt("configs");
      dropIt("events");
      dropIt("charts");
      dropIt("versions");
      dropIt("aspects");
      dropIt("asporbstrs");
      dropIt("ayanamshas");
      dropIt("housesystems");
      dropIt("eclprojs");
      dropIt("mundpoints");
      dropIt("obspos");
      dropIt("points");
      dropIt("timezones");
      dropIt("ratings");
      dropIt("charttypes");
      dropIt("properties");
   }

   private static void dropIt(final String tableName) {
      final String query = "DROP TABLE " + tableName + ";";
      try {
         Statement statement = con.createStatement();
         statement.executeUpdate(query);
         statement.close();
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }


}
