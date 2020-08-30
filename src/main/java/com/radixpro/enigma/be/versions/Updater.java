/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.versions;

import com.radixpro.enigma.be.persistency.AppDb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Updater {

   private static final Logger LOG = Logger.getLogger(Updater.class);
   private final AppDb appDb;
   private Connection con;
   public Updater(final AppDb appDb) {
      this.appDb = appDb;
   }

   public void updateStep20202() {
      LOG.info("Starting with updateStep20202.");
      con = appDb.getConnection();
      performUpdate("CREATE TABLE IF NOT EXISTS charttypes (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS ratings (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS timezones (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS points (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS obspos (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS mundpoints (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS eclprojs (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS housesystems (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS ayanamshas (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS asporbstrs (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS aspects (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL);");
      performUpdate("CREATE TABLE IF NOT EXISTS VERSIONS (id INT PRIMARY KEY, versiontxt VARCHAR(12) NOT NULL);");
      performUpdate("CREATE SEQUENCE IF NOT EXISTS versionsSeq START WITH 100");
      performUpdate("CREATE TABLE IF NOT EXISTS properties (key VARCHAR(50) NOT NULL, value VARCHAR(100) NOT NULL);");
//      performUpdate("CREATE TABLE IF NOT EXISTS charts (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL, description VARCHAR(256)" +
//            ", source VARCHAR(256), idcharttype INT NOT NULL, idrating INT NOT NULL, calDate VARCHAR(11) NOT NULL, time VARCHAR(8) NOT NULL" +
//            ", calendar VARCHAR(1) NOT NULL, dst BOOLEAN NOT NULL, idtz INT NOT NULL, offsetlmt DOUBLE, locname VARCHAR(255), geolong VARCHAR(11) NOT NULL" +
//            ", geolat VARCHAR(11) NOT NULL, FOREIGN KEY (idcharttype) REFERENCES charttypes(id), FOREIGN KEY (idrating) REFERENCES ratings(id)" +
//            ", FOREIGN KEY (idtz) REFERENCES timezones(id));");
      performUpdate("CREATE TABLE IF NOT EXISTS charts (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL, description VARCHAR(256)" +
            ", idcharttype INT NOT NULL, idrating INT NOT NULL, caldate VARCHAR(11) NOT NULL, cal VARCHAR(1) NOT NULL, time DOUBLE NOT NULL" +
            ", geolat DOUBLE NOT NULL, geolon DOUBLE NOT NULL, datainput VARCHAR(255), FOREIGN KEY (idcharttype) REFERENCES charttypes(id)" +
            ", FOREIGN KEY (idrating) REFERENCES ratings(id));");
      performUpdate("CREATE UNIQUE INDEX ON charts(name ASC)");
      performUpdate("CREATE SEQUENCE IF NOT EXISTS chartsSeq START WITH 100");
//      performUpdate("CREATE TABLE IF NOT EXISTS events (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL, description VARCHAR(256)" +
//            ", datetime VARCHAR(16) NOT NULL, idtz INT, dst BOOLEAN, offsetlmt DOUBLE, locname VARCHAR(255), geolong VARCHAR(11), geolat VARCHAR(11)" +
//            ", FOREIGN KEY (idtz) REFERENCES timezones(id));");
      performUpdate("CREATE TABLE IF NOT EXISTS events (id INT PRIMARY KEY, name VARCHAR(90) NOT NULL, description VARCHAR(256)" +
            ", caldate VARCHAR(11) NOT NULL, cal VARCHAR(1) NOT NULL, time DOUBLE NOT NULL, geolat DOUBLE, geolon DOUBLE, datainput varchar(255));");
      performUpdate("CREATE UNIQUE INDEX ON events(name ASC)");
      performUpdate("CREATE TABLE IF NOT EXISTS configs (id INT PRIMARY KEY, parentid INT, name VARCHAR(90) NOT NULL, description VARCHAR(256)" +
            ", idhouses INT NOT NULL, idayanamshas INT NOT NULL, ideclprojs INT NOT NULL, idobspos INT NOT NULL, idasporbstrs INT NOT NULL" +
            ", baseorb DOUBLE NOT NULL, drawinoutgoing BOOLEAN NOT NULL, FOREIGN KEY (parentid) REFERENCES configs(id)" +
            ", FOREIGN KEY (idhouses) REFERENCES housesystems(id), FOREIGN KEY (idayanamshas) REFERENCES ayanamshas(id)" +
            ", FOREIGN KEY (ideclprojs) REFERENCES eclprojs(id), FOREIGN KEY (idobspos) REFERENCES obspos(id)" +
            ", FOREIGN KEY (idasporbstrs) REFERENCES asporbstrs(id));");
      performUpdate("CREATE SEQUENCE IF NOT EXISTS configsSeq START WITH 1000");
      performUpdate("CREATE TABLE IF NOT EXISTS chartsevents(idchart INT, idevent INT , PRIMARY KEY(idchart, idevent)" +
            ", FOREIGN KEY (idchart) REFERENCES charts(id), FOREIGN KEY (idevent) REFERENCES events(id));");
      performUpdate("CREATE TABLE IF NOT EXISTS configspoints (idconfig INT, idpoint INT, glyph VARCHAR(1) NOT NULL, orbperc INT NOT NULL " +
            ", showdrawing BOOLEAN NOT NULL, PRIMARY KEY(idconfig, idpoint), FOREIGN KEY (idconfig) REFERENCES configs(id)" +
            ", FOREIGN KEY (idpoint) REFERENCES points(id));");
      performUpdate("CREATE TABLE IF NOT EXISTS configsaspects (idconfig INT, idaspect INT, glyph VARCHAR(1) NOT NULL, orbperc INT NOT NULL " +
            ", showdrawing BOOLEAN NOT NULL, FOREIGN KEY (idconfig) REFERENCES configs(id), FOREIGN KEY (idaspect) REFERENCES aspects(id));");
      // DML charttypes
      performUpdate("INSERT INTO charttypes(id, name) VALUES (0, 'UNKNOWN');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (1, 'FEMALE');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (2, 'MALE');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (3, 'NATAL');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (4, 'EVENT');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (5, 'HORARY');");
      performUpdate("INSERT INTO charttypes(id, name) VALUES (6, 'ELECTION');");
      // DML ratings
      performUpdate("INSERT INTO ratings(id, name) VALUES (0, 'ZZ');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (1, 'AA');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (2, 'A');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (3, 'B');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (4, 'C');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (5, 'DD');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (6, 'X');");
      performUpdate("INSERT INTO ratings(id, name) VALUES (7, 'XX');");
      // DML timezones
      performUpdate("INSERT INTO timezones(id, name) VALUES (0, 'LMT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (1, 'UT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (2, 'CET');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (3, 'EET');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (4, 'EAT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (5, 'IRST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (6, 'AMT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (7, 'AFT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (8, 'PKT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (9, 'IST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (10, 'IOT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (11, 'MMT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (12, 'ICT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (13, 'WST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (14, 'JST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (15, 'ACST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (16, 'AEST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (17, 'LHST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (18, 'MCT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (19, 'NZST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (20, 'SST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (21, 'HAST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (22, 'MART');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (23, 'AKST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (24, 'PST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (25, 'MST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (26, 'CST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (27, 'EST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (28, 'AST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (29, 'NST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (30, 'BRT');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (31, 'GST');");
      performUpdate("INSERT INTO timezones(id, name) VALUES (32, 'AZOT');");
      // DML points
      performUpdate("INSERT INTO points(id, name) VALUES (0, 'EMPTY');");
      performUpdate("INSERT INTO points(id, name) VALUES (1, 'SUN');");
      performUpdate("INSERT INTO points(id, name) VALUES (2, 'MOON');");
      performUpdate("INSERT INTO points(id, name) VALUES (3, 'MERCURY');");
      performUpdate("INSERT INTO points(id, name) VALUES (4, 'VENUS');");
      performUpdate("INSERT INTO points(id, name) VALUES (5, 'EARTH');");
      performUpdate("INSERT INTO points(id, name) VALUES (6, 'MARS');");
      performUpdate("INSERT INTO points(id, name) VALUES (7, 'JUPITER');");
      performUpdate("INSERT INTO points(id, name) VALUES (8, 'SATURN');");
      performUpdate("INSERT INTO points(id, name) VALUES (9, 'URANUS');");
      performUpdate("INSERT INTO points(id, name) VALUES (10, 'NEPTUNE');");
      performUpdate("INSERT INTO points(id, name) VALUES (11, 'PLUTO');");
      performUpdate("INSERT INTO points(id, name) VALUES (12, 'CHEIRON');");
      performUpdate("INSERT INTO points(id, name) VALUES (13, 'MEAN_NODE');");
      performUpdate("INSERT INTO points(id, name) VALUES (14, 'TRUE_NODE');");
      performUpdate("INSERT INTO points(id, name) VALUES (15, 'PHOLUS');");
      performUpdate("INSERT INTO points(id, name) VALUES (16, 'CERES');");
      performUpdate("INSERT INTO points(id, name) VALUES (17, 'PALLAS');");
      performUpdate("INSERT INTO points(id, name) VALUES (18, 'JUNO');");
      performUpdate("INSERT INTO points(id, name) VALUES (19, 'VESTA');");
      performUpdate("INSERT INTO points(id, name) VALUES (20, 'NESSUS');");
      performUpdate("INSERT INTO points(id, name) VALUES (21, 'HUYA');");
      performUpdate("INSERT INTO points(id, name) VALUES (22, 'MAKEMAKE');");
      performUpdate("INSERT INTO points(id, name) VALUES (23, 'HAUMEA');");
      performUpdate("INSERT INTO points(id, name) VALUES (24, 'ERIS');");
      performUpdate("INSERT INTO points(id, name) VALUES (25, 'IXION');");
      performUpdate("INSERT INTO points(id, name) VALUES (26, 'ORCUS');");
      performUpdate("INSERT INTO points(id, name) VALUES (27, 'QUAOAR');");
      performUpdate("INSERT INTO points(id, name) VALUES (28, 'SEDNA');");
      performUpdate("INSERT INTO points(id, name) VALUES (29, 'VARUNA');");
      // DML obspos
      performUpdate("INSERT INTO obspos(id, name) VALUES (0, 'EMPTY');");
      performUpdate("INSERT INTO obspos(id, name) VALUES (1, 'GEOCENTRIC');");
      performUpdate("INSERT INTO obspos(id, name) VALUES (2, 'TOPOCENTRIC');");
      // DML mundpoints
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (1, 'MC');");
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (2, 'ASC');");
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (3, 'VERTEX');");
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (4, 'ANIT_VERTEX');");
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (5, 'EAST_POINT');");
      performUpdate("INSERT INTO mundpoints(id, name) VALUES (6, 'CUSP');");
      // DML eclprojs
      performUpdate("INSERT INTO eclprojs(id, name) VALUES (0, 'EMPTY');");
      performUpdate("INSERT INTO eclprojs(id, name) VALUES (1, 'TROPICAL');");
      performUpdate("INSERT INTO eclprojs(id, name) VALUES (2, 'SIDEREAL');");
      // DML housesystems
      performUpdate("INSERT INTO housesystems(id, name) VALUES (0, 'EMPTY');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (1, 'NO_HOUSES');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (2, 'WHOLESIGN');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (3, 'EQUAL');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (4, 'EQUAL_MC');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (5, 'VEHLOW');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (6, 'PLACIDUS');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (7, 'KOCH');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (8, 'PORPHYRI');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (9, 'REGIOMONTANUS');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (10, 'CAMPANUS');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (11, 'ALCABITIUS');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (12, 'TOPOCENTRIC');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (13, 'KRUSINSKI');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (14, 'APC');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (15, 'MORIN');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (16, 'AXIAL');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (17, 'HORIZON');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (18, 'CARTER');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (19, 'EQUAL_ARIES');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (20, 'GAUQUELIN');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (21, 'SUNSHINE');");
      performUpdate("INSERT INTO housesystems(id, name) VALUES (22, 'SUNSHINE_TREINDL');");
      // DML ayanamshas
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (-2, 'EMPTY');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (-1, 'NONE');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (0, 'FAGAN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (1, 'LAHIRI');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (2, 'DELUCE');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (3, 'RAMAN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (4, 'USHA_SHASHI');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (5, 'KRISHNAMURTI');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (6, 'DJWHAL_KHUL');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (7, 'YUKTESHWAR');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (8, 'BHASIN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (9, 'KUGLER_1');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (10, 'KUGLER_2');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (11, 'KUGLER_3');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (12, 'HUBER');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (13, 'ETA_PISCIUM');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (14, 'ALDEBARAN_15TAU');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (15, 'HIPPARCHUS');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (16, 'SASSANIAN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (17, 'GALACT_CTR_0SAG');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (18, 'J2000');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (19, 'J1900');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (20, 'B1950');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (21, 'SURYASIDDHANTA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (22, 'SURYASIDDHANTA_MEAN_SUN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (23, 'ARYABHATA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (24, 'ARYABHATA_MEAN_SUN');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (25, 'SS_REVATI');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (26, 'SS_CITRA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (27, 'TRUE_CITRA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (28, 'TRUE_REVATI');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (29, 'TRUE_PUSHYA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (30, 'GALACTIC_CTR_BRAND');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (31, 'GALACTIC_EQ_IAU1958');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (32, 'GALACITC_EQ');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (33, 'GALACTIC_EQ_MID_MULA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (34, 'SKYDRAM');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (35, 'TRUE_MULA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (36, 'DHRUVA');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (37, 'ARYABHATA_522');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (38, 'BRITTON');");
      performUpdate("INSERT INTO ayanamshas(id, name) VALUES (39, 'GALACTIC_CTR_0CAP');");
      // DML asporbstrs
      performUpdate("INSERT INTO asporbstrs(id, name) VALUES (1, 'ASPECT');");
      performUpdate("INSERT INTO asporbstrs(id, name) VALUES (2, 'CELBODY');");
      performUpdate("INSERT INTO asporbstrs(id, name) VALUES (3, 'COMBINED');");
      performUpdate("INSERT INTO asporbstrs(id, name) VALUES (4, 'CATEGORY');");    // todo check what category is about
      // DML aspects
      performUpdate("INSERT INTO aspects(id, name) VALUES (1, 'CONJUNCTION');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (2, 'OPPOSITION');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (3, 'TRIANGLE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (4, 'SQUARE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (5, 'SEXTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (6, 'SEMISEXTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (7, 'INCONJUNCT');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (8, 'SEMISQUARE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (9, 'SESQUIQUADRATE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (10, 'QUINTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (11, 'BIQUINTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (12, 'SEPTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (13, 'PARALLEL');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (14, 'CONTRAPARALLEL');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (15, 'VIGINTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (16, 'SEMIQUINTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (17, 'TRIDECILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (18, 'BISEPTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (19, 'TRISEPTILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (20, 'NOVILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (21, 'BINOVILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (22, 'QUADRANOVILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (23, 'UNDECILE');");
      performUpdate("INSERT INTO aspects(id, name) VALUES (24, 'CENTILE');");
      // DML versions
      performUpdate("INSERT INTO versions(id, versiontxt) VALUES (1, '2020.2');");
      // DML properties
      performUpdate("INSERT INTO properties (key, value) values ('lang','en');");
      performUpdate("INSERT INTO properties (key, value) values ('config','1');");
      // DML configs, only standard configs
      performUpdate("INSERT INTO configs(id, parentid, name, description, idhouses, idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb" +
            ", drawinoutgoing) values(1, null, 'Western standard', 'Standard for western astrology', 6, -1, 1, 1, 1, 8.0, true)");
      performUpdate("INSERT INTO configs(id, parentid, name, description, idhouses, idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb" +
            ", drawinoutgoing) values(2, null, 'Astronomical Correct', 'Based on astronomical correct/visual positions', 9, -1, 1, 2, 1, 8.0, true)");
      performUpdate("INSERT INTO configs(id, parentid, name, description, idhouses, idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb" +
            ", drawinoutgoing) values(3, null, 'Hellenistic', 'Hellenistic', 2, -1, 1, 1, 1, 8.0, true)");
      performUpdate("INSERT INTO configs(id, parentid, name, description, idhouses, idayanamshas, ideclprojs, idobspos, idasporbstrs, baseorb" +
            ", drawinoutgoing) values(4, null, 'Vedic', 'Vedic', 2, 1, 2, 1, 1, 8.0, true)");
      // DML configspoints
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,1,'a',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,2,'b',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,3,'c',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,4,'d',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,6,'f',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,7,'g',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,8,'h',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,9,'i',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,10,'j',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,11,'k',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,12,'w',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(1,13,'{',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,1,'a',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,2,'b',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,3,'c',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,4,'d',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,6,'f',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,7,'g',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,8,'h',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,9,'i',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,10,'j',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,11,'k',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,12,'w',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(2,14,'{',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,1,'a',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,2,'b',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,3,'c',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,4,'d',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,6,'f',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,7,'g',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,8,'h',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(3,13,'{',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,1,'a',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,2,'b',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,3,'c',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,4,'d',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,6,'f',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,7,'g',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,8,'h',100, true);");
      performUpdate("INSERT INTO configspoints(idconfig, idpoint, glyph, orbperc, showdrawing) values(4,13,'{',100, true);");
      // DML aspectspoints
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,1,'B',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,2,'C',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,3,'D',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,4,'E',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,5,'F',60, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,7,'H',25, true);");  // TODO INCONJUNCT IN 2020.1 HAS AN INCORRECT GLYPHIS INCORRECT
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,13,'O',15, false);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(1,14,'P',15, false);");

      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,1,'B',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,2,'C',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,3,'D',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,4,'E',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,5,'F',60, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,13,'O',15, false);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(2,14,'P',15, false);");

      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,1,'B',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,2,'C',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,3,'D',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,4,'E',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,5,'F',60, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,13,'O',15, false);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(3,14,'P',15, false);");

      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,1,'B',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,2,'C',100, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,3,'D',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,4,'E',85, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,5,'F',60, true);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,13,'O',15, false);");
      performUpdate("INSERT INTO configsaspects(idconfig, idaspect, glyph, orbperc, showdrawing) values(4,14,'P',15, false);");

      try {
         con.commit();
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      } finally {
         appDb.closeConnection();
      }
   }


   //use for SQL commands CREATE, DROP, INSERT and UPDATE
   private synchronized void performUpdate(final String expression) {
      Statement statement = null;
      try {
         statement = con.createStatement();
         int i = 0;
         i = statement.executeUpdate(expression);
         if (i == -1) LOG.error("database error : " + expression);

         statement.close();
      } catch (SQLException throwables) {
         System.out.println("------------------" + expression);
         throwables.printStackTrace();
      }
   }

}
