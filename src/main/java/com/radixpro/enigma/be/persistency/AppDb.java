/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.shared.AppProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDb {

   private static final Logger LOG = Logger.getLogger(AppDb.class);
   private final AppProperties props;
   private Connection con;
   private static AppDb instance;

   private AppDb() {
      this.props = new AppProperties("PROD");
      LOG.info("Instantiated AppDb.");
   }

   private AppDb(final String environment) {
      this.props = new AppProperties(environment);
      LOG.info("Instantiated AppDb.");
   }

   /**
    * Create db settings for prod, dev or test.
    *
    * @param environment
    * @return
    */
   public static AppDb initAppDb(final String environment) {
      if (null == instance) instance = new AppDb(environment);
      return instance;
   }

   /**
    * Always uses production database.
    *
    * @return single instance of AppDb.
    */
   public static AppDb getInstance() {   // TODO consider to remove null-check and creation of appDb. This should have been done by initAppDb()
      if (null == instance) instance = new AppDb();
      return instance;
   }

   public Connection getConnection() {
      if (null == con) {
         try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:" + props.getDatabasePath());
            LOG.info("SQL connection created succesfully.");
         } catch (SQLException | ClassNotFoundException e) {
            LOG.error("Exception when creating connection : " + e.getMessage());
         }
      }
      return con;
   }

   public void closeConnection() {
      try {
         if (null != con && !con.isClosed()) {

            Statement st = con.createStatement();
            st.execute("SHUTDOWN");
            con.close();
            con = null;
         }
      } catch (SQLException e) {
         LOG.error(("SQLException when closing connection : " + e.getMessage()));
      }
   }

   public AppProperties getProps() {
      return props;
   }
}
