/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.persistency.daos;

import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.be.persistency.daos.helpers.ConfigurationDaoConstructionHelper;
import com.radixpro.enigma.shared.AppDb;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import com.radixpro.enigma.xchg.domain.config.ConfiguredAspect;
import com.radixpro.enigma.xchg.domain.config.ConfiguredCelObject;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationDao extends DaoParent {

   private static final Logger LOG = Logger.getLogger(ConfigurationDao.class);
   private static final String INS_ASPECTS = "INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(?,?,?,?,?);";
   private static final String INS_POINTS = "INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(?,?,?,?,?);";
   private static final String SEL_CONFIGS =
         "SELECT id, parentid, name, description, idhouses,idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb, drawinoutgoing";
   private static final String SEL_ASPECTS = "SELECT idaspect, glyph, orbperc, showdrawing FROM configsaspects WHERE idconfig = ?";
   private static final String SEL_POINTS = "SELECT idpoint, glyph, orbperc, showdrawing FROM configspoints WHERE idconfig = ?";
   private static final String DEL_ASPECTS = "DELETE configsaspects WHERE idconfig = ?;";
   private static final String DEL_POINTS = "DELETE configspoints WHERE idconfig = ?;";
   private static final String ZERO_ROWS = "ZERO_ROWS";

   public int insert(final Configuration insertConfig) throws DatabaseException {
      int configId = -1;
      checkNotNull(insertConfig);
      final AppDb appDb = AppDb.getInstance();
      final String insertConfigs = "INSERT INTO configs (id, parentid, name, description, idhouses, idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb" +
            ", drawinoutgoing) values(configsseq.NEXTVAL, ?,?,?,?,?,?,?,?,?,?); ";
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmtConfigs = con.prepareStatement(insertConfigs)) {
         pStmtConfigs.setInt(1, insertConfig.getParentId());
         pStmtConfigs.setString(2, insertConfig.getName());
         pStmtConfigs.setString(3, insertConfig.getDescription());
         pStmtConfigs.setInt(4, insertConfig.getAstronConfiguration().getHouseSystem().getId());
         pStmtConfigs.setInt(5, insertConfig.getAstronConfiguration().getAyanamsha().getId());
         pStmtConfigs.setInt(6, insertConfig.getAstronConfiguration().getEclipticProjection().getId());
         pStmtConfigs.setInt(7, insertConfig.getAstronConfiguration().getObserverPosition().getId());
         pStmtConfigs.setInt(8, insertConfig.getDelinConfiguration().getAspectConfiguration().getOrbStructure().getId());
         pStmtConfigs.setDouble(9, insertConfig.getDelinConfiguration().getAspectConfiguration().getBaseOrb());
         pStmtConfigs.setBoolean(10, insertConfig.getDelinConfiguration().getAspectConfiguration().isDrawInOutGoing());
         int result = pStmtConfigs.executeUpdate();
         if (result != 1) throw new DatabaseException("Could not insert config " + insertConfig.getName() + ZERO_ROWS);


         final String queryCurrVal = "select configsseq.CURRVAL;";
         Statement stmtCurrval = con.createStatement();
         ResultSet rsCurrVal = stmtCurrval.executeQuery(queryCurrVal);
         while (rsCurrVal.next()) {
            configId = rsCurrVal.getInt(1);
         }


         try (PreparedStatement pStmtAspects = con.prepareStatement(INS_ASPECTS)) {
            List<ConfiguredAspect> aspects = insertConfig.getDelinConfiguration().getAspectConfiguration().getAspects();
            for (ConfiguredAspect aspect : aspects) {
               result = insertAspects(configId, pStmtAspects, aspect);
               if (result != 1) {
                  con.rollback();
                  throw new DatabaseException("Could not insert aspect " + aspect.getAspect().getId() + " for config " + insertConfig.getName() + ZERO_ROWS);
               }
            }
         }
         try (PreparedStatement pStmtPoints = con.prepareStatement(INS_POINTS)) {
            List<ConfiguredCelObject> points = insertConfig.getAstronConfiguration().getCelObjects();
            for (ConfiguredCelObject point : points) {
               result = insertPoints(configId, pStmtPoints, point);
               if (result != 1) {
                  con.rollback();
                  throw new DatabaseException("Could not insert celestial point " + point.getCelObject().getId() + " for config " +
                        insertConfig.getName() + ZERO_ROWS);
               }
            }
         }
         con.commit();
      } catch (SQLException throwables) {
         try {
            con.rollback();
         } catch (SQLException e) {
            LOG.error("SQLException when trying to rollback : " + e.getMessage());
         }
         throw new DatabaseException("SQLException when inserting config " + insertConfig.getName() + ". Exception :  " + throwables.getMessage());
      } finally {
         appDb.closeConnection();
      }
      return configId;
   }


   public void update(final Configuration updateConfig) throws DatabaseException {
      checkNotNull(updateConfig);
      final AppDb appDb = AppDb.getInstance();
      Connection con = appDb.getConnection();
      final String updateConfigs = "UPDATE configs set name = ?, description = ?, idhouses = ?, idayanamshas = ?, ideclprojs = ?, idobspos = ? " +
            ", idasporbstrs = ?, baseorb = ?, drawinoutgoing = ? WHERE id = ?;";
      try (PreparedStatement pStmtConfigs = con.prepareStatement(updateConfigs)) {
         pStmtConfigs.setString(1, updateConfig.getName());
         pStmtConfigs.setString(2, updateConfig.getDescription());
         pStmtConfigs.setInt(3, updateConfig.getAstronConfiguration().getHouseSystem().getId());
         pStmtConfigs.setInt(4, updateConfig.getAstronConfiguration().getAyanamsha().getId());
         pStmtConfigs.setInt(5, updateConfig.getAstronConfiguration().getEclipticProjection().getId());
         pStmtConfigs.setInt(6, updateConfig.getAstronConfiguration().getObserverPosition().getId());
         pStmtConfigs.setInt(7, updateConfig.getDelinConfiguration().getAspectConfiguration().getOrbStructure().getId());
         pStmtConfigs.setDouble(8, updateConfig.getDelinConfiguration().getAspectConfiguration().getBaseOrb());
         pStmtConfigs.setBoolean(9, updateConfig.getDelinConfiguration().getAspectConfiguration().isDrawInOutGoing());
         pStmtConfigs.setInt(10, updateConfig.getId());
         int result = pStmtConfigs.executeUpdate();
         if (result != 1) {
            con.rollback();
            throw new DatabaseException("Could not update config " + updateConfig.getName() + ZERO_ROWS);
         }
         handleDelete(con, DEL_ASPECTS, updateConfig.getId());
         handleDelete(con, DEL_POINTS, updateConfig.getId());
         try (PreparedStatement pStmtAspects = con.prepareStatement(INS_ASPECTS)) {
            List<ConfiguredAspect> aspects = updateConfig.getDelinConfiguration().getAspectConfiguration().getAspects();
            for (ConfiguredAspect aspect : aspects) {
               result = insertAspects(updateConfig.getId(), pStmtAspects, aspect);
               if (result != 1) {
                  con.rollback();
                  throw new DatabaseException("Could not insert aspect " + aspect.getAspect().getId() + " while updating config " + updateConfig.getId() +
                        ZERO_ROWS);
               }
            }
         }
         try (PreparedStatement pStmtPoints = con.prepareStatement(INS_POINTS)) {
            List<ConfiguredCelObject> points = updateConfig.getAstronConfiguration().getCelObjects();
            for (ConfiguredCelObject point : points) {
               result = insertPoints(updateConfig.getId(), pStmtPoints, point);
               if (result != 1) {
                  con.rollback();
                  throw new DatabaseException("Could not insert celestial point " + point.getCelObject().getId() + " while updating config " +
                        updateConfig.getId() + ZERO_ROWS);
               }
            }
         }
         con.commit();
      } catch (SQLException throwables) {
         try {
            con.rollback();
         } catch (SQLException e) {
            LOG.error("SQLException when trying to rollback : " + e.getMessage());
         }
         throw new DatabaseException("SQLException when updating config " + updateConfig.getId() + ". Exception :  " + throwables.getMessage());
      } finally {
         appDb.closeConnection();
      }
   }

   public void delete(final int id) {
      final String queryDelete = "DELETE configs where id = ?;";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      try {
         handleDelete(con, DEL_ASPECTS, id);
         handleDelete(con, DEL_POINTS, id);
         handleDelete(con, queryDelete, id);
      } catch (SQLException throwables) {
         LOG.error("SQLException while deleting config : " + id + " . Msg : " + throwables.getMessage());
      }
   }

   /**
    * Retreive configuration for specific id.
    *
    * @param id the di for the configuration to retrieve.
    * @return A list with one or zero configurations.
    */
   public List<Configuration> read(final int id) {
      List<Configuration> resultConfigs = new ArrayList<>();
      ConfigurationDaoConstructionHelper configurationDaoConstructionHelper;
      final String queryConfigs = SEL_CONFIGS + " FROM configs WHERE id = ?;";
      final AppDb appDb = AppDb.getInstance();
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmtConfigs = con.prepareStatement(queryConfigs)) {
         pStmtConfigs.setInt(1, id);
         ResultSet rsConfigs = pStmtConfigs.executeQuery();
         while (rsConfigs.next()) {
            configurationDaoConstructionHelper = new ConfigurationDaoConstructionHelper(rsConfigs);
            configurationDaoConstructionHelper.setPointsResultSet(handleSearchQuery(con, SEL_POINTS, id));
            configurationDaoConstructionHelper.setAspectsResultSet(handleSearchQuery(con, SEL_ASPECTS, id));
            resultConfigs.add(configurationDaoConstructionHelper.getConfiguration());
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException when reading config for id: " + id + " . Msg: " + throwables.getMessage());
      }
      return resultConfigs;
   }

   /**
    * Search a config using (a part of) the name. Search is case sensitive.
    *
    * @param searchName the search argument.
    * @return a list with zero or more configurations.
    * @throws DatabaseException is thrown for any database error.
    */
   public List<Configuration> search(final String searchName) throws DatabaseException {
      List<Configuration> resultConfigs = new ArrayList<>();
      final AppDb appDb = AppDb.getInstance();
      final String queryConfigs = SEL_CONFIGS + " FROM configs WHERE name ILIKE '%' || ? || '%';";
      final Connection con = appDb.getConnection();
      try (PreparedStatement pStmtConfigs = con.prepareStatement(queryConfigs)) {
         pStmtConfigs.setString(1, searchName);
         ResultSet rsConfigs = pStmtConfigs.executeQuery();
         while (rsConfigs.next()) {
            int id = rsConfigs.getInt("id");
            ConfigurationDaoConstructionHelper configurationDaoConstructionHelper = new ConfigurationDaoConstructionHelper(rsConfigs);
            configurationDaoConstructionHelper.setPointsResultSet(handleSearchQuery(con, SEL_POINTS, id));
            configurationDaoConstructionHelper.setAspectsResultSet(handleSearchQuery(con, SEL_ASPECTS, id));
            resultConfigs.add(configurationDaoConstructionHelper.getConfiguration());
         }
      } catch (SQLException throwables) {
         LOG.error("SQLException while searching for config with searchName : " + searchName + " . Msg : " + throwables.getMessage());
      }
      return resultConfigs;
   }

   /**
    * Retrieve all configurations.
    *
    * @return the configurations.
    * @throws DatabaseException is thrown for any database error.
    */
   public List<Configuration> readAll() {
      final AppDb appDb = AppDb.getInstance();
      final String queryConfigs = SEL_CONFIGS + " FROM configs;";
      final Connection con = appDb.getConnection();
      List<Configuration> configs = new ArrayList<>();
      try {
         try (Statement stmtConfigs = con.createStatement()) {
            ResultSet rsConfigs = stmtConfigs.executeQuery(queryConfigs);
            while (rsConfigs.next()) {
               int id = rsConfigs.getInt("id");
               ConfigurationDaoConstructionHelper configurationDaoConstructionHelper = new ConfigurationDaoConstructionHelper(rsConfigs);
               configurationDaoConstructionHelper.setPointsResultSet(handleSearchQuery(con, SEL_POINTS, id));
               configurationDaoConstructionHelper.setAspectsResultSet(handleSearchQuery(con, SEL_ASPECTS, id));
               configs.add(configurationDaoConstructionHelper.getConfiguration());
            }
         }
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      return configs;
   }

   private ResultSet handleSearchQuery(final Connection con, final String query, final int id) throws SQLException {
      PreparedStatement pStmt = con.prepareStatement(query);
      pStmt.setInt(1, id);
      return pStmt.executeQuery();
   }

   private void handleDelete(final Connection con, final String query, final int id) throws SQLException {
      try (PreparedStatement pStmt = con.prepareStatement(query)) {
         pStmt.setInt(1, id);
         pStmt.executeUpdate();
      }
   }

   private int insertAspects(final int idConfig, final PreparedStatement pStmt, final ConfiguredAspect aspect) throws SQLException {
      pStmt.setInt(1, idConfig);
      pStmt.setInt(2, aspect.getAspect().getId());
      pStmt.setString(3, aspect.getGlyph());
      pStmt.setInt(4, aspect.getOrbPercentage());
      pStmt.setBoolean(5, aspect.isShowInDrawing());
      return pStmt.executeUpdate();
   }

   private int insertPoints(final int idConfig, final PreparedStatement pStmt, final ConfiguredCelObject point) throws SQLException {
      pStmt.setInt(1, idConfig);
      pStmt.setInt(2, point.getCelObject().getId());
      pStmt.setString(3, point.getGlyph());
      pStmt.setInt(4, point.getOrbPercentage());
      pStmt.setBoolean(5, point.isShowInDrawing());
      return pStmt.executeUpdate();
   }

}
