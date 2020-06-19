/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.exceptions.DatabaseException;
import com.radixpro.enigma.be.persistency.daos.PropertyDao;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.Property;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersistedPropertyApi {

   private final PropertyDao dao;

   public PersistedPropertyApi() {
      dao = new PropertyDao();
   }


   public void update(final Property property) {
      checkNotNull(property);
      try {
         dao.update(property);
      } catch (DatabaseException de) {
         new FailFastHandler().terminate(de.getMessage());
      }
   }

   public List<Property> read(final String key) {
      checkNotNull(key);
      List<Property> propList = new ArrayList<>();
      try {
         propList = dao.read(key);
      } catch (Exception e) {
         new FailFastHandler().terminate(e.getMessage());
      }
      return propList;
   }


}
