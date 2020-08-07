/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.daos.helpers;

import com.google.common.base.Preconditions;
import com.radixpro.enigma.be.persistency.ConfigurationDao;
import com.radixpro.enigma.domain.config.*;
import com.radixpro.enigma.references.*;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Helper for ConfigurationDao. Mutable class.
 * Constructs a Configuration from resultsets.
 *
 * @see ConfigurationDao
 */
public class ConfigurationDaoConstructionHelper {

   private static final Logger LOG = Logger.getLogger(ConfigurationDaoConstructionHelper.class);
   private final ResultSet configResultSet;
   private ResultSet aspectsResultSet;
   private ResultSet pointsResultSet;
   private int id;
   private int parentid;
   private String name;
   private String description;
   private int idHouses;
   private int idAyanamshas;
   private int idEclProjs;
   private int idObsPos;
   private int idAspOrbStrs;
   private double baseOrb;                     // TODO rename globally to aspectBaseOrb
   private boolean drawInOutGoing;
   private List<ConfiguredAspect> aspects;
   private List<ConfiguredCelObject> celPoints;

   /**
    * This is a mutable class, the constructor only defines the resultset for a configuration.
    *
    * @param configResultSet PRE: not null.
    */
   public ConfigurationDaoConstructionHelper(final ResultSet configResultSet) {
      this.configResultSet = checkNotNull(configResultSet);
   }

   /**
    * Sets the resultset for aspects.
    *
    * @param aspectsResultSet PRE: not null.
    */
   public void setAspectsResultSet(final ResultSet aspectsResultSet) {
      this.aspectsResultSet = checkNotNull(aspectsResultSet);
   }

   /**
    * Sets the resultset for points.
    *
    * @param pointsResultSet PRE: not null.
    */
   public void setPointsResultSet(final ResultSet pointsResultSet) {
      this.pointsResultSet = checkNotNull(pointsResultSet);
   }

   /**
    * Return the constructed configuration.
    * PRE: the resultsets for aspects and for points have been set.
    *
    * @return the configuration.
    */
   public Configuration getConfiguration() {
      Preconditions.checkState((null != aspectsResultSet) && (null != pointsResultSet));
      return constructConfig();
   }

   private Configuration constructConfig() {
      Configuration config = null;
      aspects = new ArrayList<>();
      celPoints = new ArrayList<>();
      try {
         id = configResultSet.getInt("id");
         parentid = configResultSet.getInt("parentid");
         name = configResultSet.getString("name");
         description = configResultSet.getString("description");
         idHouses = configResultSet.getInt("idhouses");
         idAyanamshas = configResultSet.getInt("idayanamshas");
         idEclProjs = configResultSet.getInt("ideclprojs");
         idObsPos = configResultSet.getInt("idobspos");
         idAspOrbStrs = configResultSet.getInt("idasporbstrs");
         baseOrb = configResultSet.getDouble("baseorb");
         drawInOutGoing = configResultSet.getBoolean("drawinoutgoing");
         while (pointsResultSet.next()) {
            int pointId = pointsResultSet.getInt("idpoint");
            String glyph = pointsResultSet.getString("glyph");
            int orbPerc = pointsResultSet.getInt("orbperc");
            boolean showInDrawing = pointsResultSet.getBoolean("showdrawing");
            celPoints.add(createConfCelObject(pointId, glyph, orbPerc, showInDrawing));
         }
         while (aspectsResultSet.next()) {
            int aspectId = aspectsResultSet.getInt("idaspect");
            String glyph = aspectsResultSet.getString("glyph");
            int orbPerc = aspectsResultSet.getInt("orbperc");
            boolean showInDrawing = aspectsResultSet.getBoolean("showdrawing");
            aspects.add(new ConfiguredAspect(AspectTypes.getAspectForId(aspectId), orbPerc, glyph, showInDrawing));
         }
         config = createConfig();
      } catch (SQLException throwables) {
         LOG.error("SQLException when constructing config for persistency.");
      }
      return config;
   }

   private ConfiguredCelObject createConfCelObject(final int pointId, final String glyph, final int orbPerc, final boolean showInDrawing) {
      ConfiguredCelObject ccObject = null;
      try {
         ccObject = new ConfiguredCelObject(CelestialObjects.getCelObjectForId(pointId), glyph, orbPerc, showInDrawing);
      } catch (UnknownIdException e) {
         LOG.error("Could not read CelestialObject for id :" + pointId);
      }
      return ccObject;
   }

   private Configuration createConfig() {
      Configuration config = null;
      try {
         AstronConfiguration astronConfiguration = new AstronConfiguration(HouseSystems.getSystemForId(idHouses), Ayanamshas.getAyanamshaForId(idAyanamshas),
               EclipticProjections.getProjectionForId(idEclProjs), ObserverPositions.getObserverPositionForId(idObsPos), celPoints);
         DelinConfiguration delinConfiguration = new DelinConfiguration(new AspectConfiguration(aspects, baseOrb,
               AspectOrbStructures.getStructureForId(idAspOrbStrs), drawInOutGoing));
         config = new Configuration(id, parentid, name, description, astronConfiguration, delinConfiguration);
      } catch (UnknownIdException e) {
         LOG.error("Could not read object. Original message : " + e.getMessage());
      }
      return config;
   }

}
