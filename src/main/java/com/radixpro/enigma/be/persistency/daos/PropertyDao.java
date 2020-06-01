/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.persistency.daos;

import com.opencsv.CSVReader;
import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.shared.Property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dao for Property.
 */
public class PropertyDao extends DaoParent {

   private static final String PROP_FILE = DB_LOCATION + "properties.csv";
   private static final String NEW_PROP_FILE = DB_LOCATION + "new_properties.csv";
   private static final String OLD_PROP_FILE = DB_LOCATION + "old_properties.csv";

   /**
    * Add new Property.
    *
    * @param insertProp The property to add.
    * @throws DatabaseException is thrown for any database error.
    */
   public void insert(final Property insertProp) throws DatabaseException {
      List<Property> allProps = readAll();
      allProps.add(insertProp);
      List<String[]> allLines = new ArrayList<>();
      for (Property prop : allProps) {
         String[] propLine = {String.valueOf(prop.getId()), prop.getKey(), prop.getValue()};  // HIERO verwacht array met strings.
         allLines.add(propLine);
      }
      writeData(allLines, PROP_FILE);
   }

   /**
    * Finds property with the same id and updates it.
    *
    * @param updateProp Property with new content and he id to search for.
    * @throws DatabaseException is thrown for any database error.
    */
   public void update(final Property updateProp) throws DatabaseException {
      List<Property> allProps = readAll();
      List<String[]> updatedProps = new ArrayList<>();
      long updatePropId = updateProp.getId();
      for (Property prop : allProps) {
         Property effectiveProp = (prop.getId() == updatePropId ? updateProp : prop);
         String[] propLine = {String.valueOf(effectiveProp.getId()), effectiveProp.getKey(), effectiveProp.getValue()};
         updatedProps.add(propLine);
      }
      writeData(updatedProps, NEW_PROP_FILE);
      updateFiles(OLD_PROP_FILE, PROP_FILE, NEW_PROP_FILE);
   }

   /**
    * Delete property, checks for the id and deletes item(s) with that id.
    *
    * @param delProp The property to delete.
    * @throws DatabaseException is thrown for any database error.
    */
   public void delete(final Property delProp) throws DatabaseException {
      List<Property> allProps = readAll();
      List<String[]> remainingProps = new ArrayList<>();
      for (Property prop : allProps) {
         if (prop.getId() != delProp.getId()) {
            String[] remainingProp = {String.valueOf(prop.getId()), prop.getKey(), prop.getValue()};
            remainingProps.add(remainingProp);
         }
      }
      writeData(remainingProps, NEW_PROP_FILE);
      updateFiles(OLD_PROP_FILE, PROP_FILE, NEW_PROP_FILE);
   }

   /**
    * Find property for a specific key.
    *
    * @param key the key to search for.
    * @return A list with properties. The list should contain one or zero properties.
    * @throws DatabaseException is thrown for any database error.
    */
   public List<Property> read(final String key) throws DatabaseException {
      checkNotNull(key);
      List<Property> allProps = readAll();
      List<Property> foundProps = new ArrayList<>();
      for (Property prop : allProps) {
         if (key.equalsIgnoreCase(prop.getKey())) foundProps.add(prop);
      }
      return foundProps;
   }

   /**
    * Reads all properties.
    *
    * @return List of Property.
    * @throws DatabaseException is thrown for any database error.
    */
   public List<Property> readAll() throws DatabaseException {
      List<String[]> allLines;
      List<Property> propList = new ArrayList<>();
      try (CSVReader reader = createReader(PROP_FILE)) {
         allLines = reader.readAll();
      } catch (IOException e) {
         throw new DatabaseException("IOException when reading all properties : " + e.getMessage());
      }
      for (String[] line : allLines) {  // respectively id, key, value
         propList.add(new Property(Long.parseLong(line[0]), line[1], line[2]));
      }
      return propList;
   }

   /**
    * Define max id as currently used for a Property.
    *
    * @return the max id that was found.
    * @throws DatabaseException is thrown for any database error.
    */
   public long getMaxId() throws DatabaseException {
      List<Property> props = readAll();
      long maxId = 0;
      for (Property prop : props) {
         if (prop.getId() > maxId) maxId = prop.getId();
      }
      return maxId;
   }

}