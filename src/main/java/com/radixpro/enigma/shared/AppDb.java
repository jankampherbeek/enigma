/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDb {

   private static final Logger LOG = Logger.getLogger(AppDb.class);
   private final AppProperties props;
   private Connection con;

   public AppDb(final AppProperties props) {
      this.props = props;
      LOG.info("Instantiated AppDb.");
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

}
