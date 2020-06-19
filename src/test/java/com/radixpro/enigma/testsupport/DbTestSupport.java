/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.testsupport;

import com.radixpro.enigma.be.versions.Updater;
import com.radixpro.enigma.shared.AppDb;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Parent for classes that perform tests using then database.
 */
public class DbTestSupport {

   private final Connection con;
   private final AppDb appDb;

   public DbTestSupport() {
      this.appDb = AppDb.initAppDb("test");
      this.con = appDb.getConnection();
      initDatabase();
   }

   private void initDatabase() {
      emptyDatabase();
      new Updater(appDb).updateStep20202();
   }

   private void emptyDatabase() {
      File traceFile = new File("test" + File.separator + "db" + File.separator + "enigmadb.trace.db");
      if (traceFile.exists()) traceFile.delete();
      File dbFile = new File("test" + File.separator + "db" + File.separator + "enigmadb.mv.db");
      if (dbFile.exists()) dropTables();
   }

   private void dropTables() {
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

   private void dropIt(final String tableName) {
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
