/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.be.versions.Updater;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.shared.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dao for Property. Supports only updating and reading a property.
 * All inserts are done via the class Updater.
 *
 * @see Updater
 */
public class PropertyDao extends DaoParent {

   private final AppDb appDb;

   public PropertyDao(final AppDb appDb) {
      this.appDb = checkNotNull(appDb);
   }

   /**
    * Updates teh value of a property.
    *
    * @param updateProp Property with new content and he id to search for.
    * @throws DatabaseException is thrown for any database error.
    */
   public void update(final Property updateProp) throws DatabaseException {
      checkNotNull(updateProp);
      Connection con = appDb.getConnection();
      final String updateProperties = "UPDATE properties SET value = ?  WHERE key = ? ;";
      try {
         try (PreparedStatement pStmtProperties = con.prepareStatement(updateProperties)) {
            pStmtProperties.setString(1, updateProp.getValue());
            pStmtProperties.setString(2, updateProp.getKey());
            int result = pStmtProperties.executeUpdate();
            if (result != 1) {
               con.rollback();
               throw new DatabaseException("Could not update property " + updateProp.getKey());
            }
         }
      } catch (SQLException throwables) {
         try {
            con.rollback();
         } catch (SQLException e) {
            LOG.error("SQLException when trying to rollback : " + e.getMessage());
         }
         throw new DatabaseException("SQLException when updating property " + updateProp.getKey() + ". Exception :  " + throwables.getMessage());
      } finally {
         appDb.closeConnection();
      }

   }

   /**
    * Find property for a specific key.
    *
    * @param key the key to search for.
    * @return A list with properties. The list should contain one or zero properties.
    */
   public List<Property> read(final String key) {
      checkNotNull(key);
      List<Property> properties = new ArrayList<>();
      final String queryProperties = "SELECT key, value FROM properties where key = ?;";
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmt = con.prepareStatement(queryProperties)) {
         pStmt.setString(1, key);
         try (ResultSet rsProperties = pStmt.executeQuery()) {
            while (rsProperties.next()) {
               properties.add(new Property(key, rsProperties.getString("value")));
            }
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading property for key: " + key + " . Msg: " + throwables.getMessage());
      }
      return properties;
   }

}