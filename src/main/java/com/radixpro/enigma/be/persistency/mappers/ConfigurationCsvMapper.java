package com.radixpro.enigma.be.persistency.mappers;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.config.*;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationCsvMapper {

   public Configuration configFromCsv(final String[] csvLine) throws UnknownIdException {
      return handleCsvLine(csvLine);
   }

   public String[] csvFromConfig(final Configuration config) {
      return handleConfig(config);
   }

   private Configuration handleCsvLine(final String[] csvLine) throws UnknownIdException {
      long id = Long.valueOf(csvLine[0]);
      long parentId = Long.valueOf(csvLine[1]);
      String name = csvLine[2];
      String description = csvLine[3];
      HouseSystems houseSystem = HouseSystems.EMPTY.getSystemForId(Integer.valueOf(csvLine[4]));
      Ayanamshas ayanamsha = Ayanamshas.EMPTY.getAyanamshaForId(Integer.valueOf(csvLine[5]));
      EclipticProjections eclipticProjection = EclipticProjections.EMPTY.getProjectionForId(Integer.valueOf(csvLine[6]));
      ObserverPositions observerPosition = ObserverPositions.EMPTY.getObserverPositionForId(Integer.valueOf(csvLine[7]));
      List<ConfiguredCelObject> confCelObjects = createConfCelObjects(csvLine[8]);
      List<ConfiguredAspect> confAspects = createConfAspects(csvLine[9]);
      double baseOrb = Double.valueOf(csvLine[10]);
      AspectOrbStructure aspOrbStruct = AspectOrbStructure.ASPECT.getStructureForId(Integer.valueOf(csvLine[11]));
      boolean drawInOutGoing = "y".equalsIgnoreCase(csvLine[12]);

      AstronConfiguration astronConf = new AstronConfiguration(houseSystem, ayanamsha, eclipticProjection, observerPosition, confCelObjects);
      AspectConfiguration aspectConf = new AspectConfiguration(confAspects, baseOrb, aspOrbStruct, drawInOutGoing);
      DelinConfiguration delinConf = new DelinConfiguration(aspectConf);
      return new Configuration(id, parentId, name, description, astronConf, delinConf);
   }

   private String[] handleConfig(Configuration config) {
      String id = Long.toString(config.getId());
      String parentId = Long.toString(config.getParentId());
      String name = config.getName();
      String description = config.getDescription();
      String houseSystem = Integer.toString(config.getAstronConfiguration().getHouseSystem().getId());
      String ayanamsha = Integer.toString(config.getAstronConfiguration().getAyanamsha().getId());
      String eclProj = Integer.toString(config.getAstronConfiguration().getEclipticProjection().getId());
      String observerPos = Integer.toString(config.getAstronConfiguration().getObserverPosition().getId());
      String confCelObjects = createStringForConfCelObjects(config.getAstronConfiguration().getCelObjects());
      String confAspects = createStringForConfAspects(config.getDelinConfiguration().getAspectConfiguration().getAspects());
      String baseOrb = Double.toString(config.getDelinConfiguration().getAspectConfiguration().getBaseOrb());
      String aspectOrbStruct = Integer.toString(config.getDelinConfiguration().getAspectConfiguration().getOrbStructure().getId());
      String drawInOutGoing = config.getDelinConfiguration().getAspectConfiguration().isDrawInOutGoing() ? "y" : "n";

      String[] csvData = {id, parentId, name, description, houseSystem, ayanamsha, eclProj, observerPos, confCelObjects,
            confAspects, baseOrb, aspectOrbStruct, drawInOutGoing};
      return csvData;
   }

   private List<ConfiguredCelObject> createConfCelObjects(final String csvData) throws UnknownIdException {
      List<ConfiguredCelObject> confCelObjects = new ArrayList<>();
      String[] celObjData = csvData.split("\\|");
      for (String singleObjectData : celObjData) {
         String[] items = singleObjectData.split("-");
         CelestialObjects celestialObject = CelestialObjects.EMPTY.getCelObjectForId(Integer.valueOf(items[0]));
         int orbPercentage = Integer.valueOf(items[1]);
         boolean showCelBody = "y".equalsIgnoreCase(items[2]);
         String glyph = items[3];
         confCelObjects.add(new ConfiguredCelObject(celestialObject, glyph, orbPercentage, showCelBody));
      }
      return confCelObjects;
   }

   private List<ConfiguredAspect> createConfAspects(final String csvData) {
      List<ConfiguredAspect> confAspects = new ArrayList<>();
      String[] aspectData = csvData.split("\\|");
      for (String singleAspectData : aspectData) {
         String[] items = singleAspectData.split("-");
         AspectTypes aspect = AspectTypes.CONJUNCTION.getAspectForId(Integer.parseInt(items[0]));
         int orbPercentage = Integer.parseInt(items[1]);
         boolean showInDrawing = "y".equalsIgnoreCase(items[2]);
         String glyph = items[3];
         confAspects.add(new ConfiguredAspect(aspect, orbPercentage, glyph, showInDrawing));
      }
      return confAspects;
   }


   private String createStringForConfCelObjects(List<ConfiguredCelObject> allCelObjects) {
      StringBuilder sBuilder = new StringBuilder();
      int count = 0;
      int maxCount = allCelObjects.size();
      for (ConfiguredCelObject confCelObject : allCelObjects) {
         sBuilder.append(confCelObject.getCelObject().getId());
         sBuilder.append("-");
         sBuilder.append(confCelObject.getOrbPercentage());
         sBuilder.append("-");
         sBuilder.append(confCelObject.isShowInDrawing() ? "y" : "n");
         sBuilder.append("-");
         sBuilder.append(confCelObject.getGlyph());
         if (++count < maxCount) sBuilder.append("\\|");
      }
      return sBuilder.toString();
   }

   private String createStringForConfAspects(List<ConfiguredAspect> allAspects) {
      StringBuilder sBuilder = new StringBuilder();
      int count = 0;
      int maxCount = allAspects.size();
      for (ConfiguredAspect confAspect : allAspects) {
         sBuilder.append(confAspect.getAspect().getId());
         sBuilder.append("-");
         sBuilder.append(confAspect.getOrbPercentage());
         sBuilder.append("-");
         sBuilder.append(confAspect.isShowInDrawing() ? "y" : "n");
         sBuilder.append("-");
         sBuilder.append(confAspect.getGlyph());
         if (++count < maxCount) sBuilder.append("|");
      }
      return sBuilder.toString();
   }


}
