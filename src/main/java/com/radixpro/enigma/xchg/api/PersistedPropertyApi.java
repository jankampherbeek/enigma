/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.persistency.PropertyDao;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.shared.exceptions.DatabaseException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PersistedPropertyApi {

   private final PropertyDao dao;

   public PersistedPropertyApi(@NotNull final PropertyDao dao) {
      this.dao = dao;
   }

   public void insert(@NotNull final Property property) {
      try {
         dao.insert(property);
      } catch (DatabaseException de) {
         new FailFastHandler().terminate(de.getMessage());
      }
   }

   public void update(@NotNull final Property property) {
      try {
         dao.update(property);
      } catch (DatabaseException de) {
         new FailFastHandler().terminate(de.getMessage());
      }
   }

   public List<Property> read(@NotNull final String key) {
      List<Property> propList = new ArrayList<>();
      try {
         propList = dao.read(key);
      } catch (Exception e) {
         new FailFastHandler().terminate(e.getMessage());
      }
      return propList;
   }


}
