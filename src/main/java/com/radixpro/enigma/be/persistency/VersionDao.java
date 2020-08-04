/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.shared.exceptions.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.*;

public class VersionDao {

   private static final Logger LOG = Logger.getLogger(VersionDao.class);
   private final AppDb appDb;

   public VersionDao() {
      this.appDb = AppDb.getInstance();
   }

   public String readLatest() {
      Connection con = appDb.getConnection();
      final String query = "SELECT versionTxt FROM VERSIONS WHERE id = SELECT MAX(id) FROM VERSIONS;";
      String versionTxt = "";
      try {
         final Statement stmt = con.createStatement();
         ResultSet rSet = stmt.executeQuery(query);
         while (rSet.next()) {
            versionTxt = rSet.getString("versiontxt");
         }
      } catch (SQLException throwables) {
         versionTxt = "0.0";
         LOG.error("Error when reading version, assuming database is not yet installed and returning version 0.0.");
      } finally {
         appDb.closeConnection();
      }

      return versionTxt;
   }

   public void insert(String newVersion) throws DatabaseException {
      Connection con = appDb.getConnection();
      final String query = "INSERT INTO versions (id, versiontxt) VALUES(versionsseq.NEXTVAL, ?)";
      try {
         PreparedStatement pStmt = con.prepareStatement(query);
         pStmt.setString(1, newVersion);
         int rowsChanged = pStmt.executeUpdate();
         if (rowsChanged != 1) throw new DatabaseException("Inserting version did not succeed. Using query : " + query + " . Changed rows : " + rowsChanged);
         con.commit();
      } catch (SQLException e) {
         throw new DatabaseException("SQLException when inserting version. Using query : " + query + " . Exeption : " + e.getMessage());
      } finally {
         appDb.closeConnection();
      }
   }

}
