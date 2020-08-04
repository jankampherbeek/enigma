/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.persistency.VersionDao;
import com.radixpro.enigma.shared.exceptions.DatabaseException;
import org.apache.log4j.Logger;

public class VersionApi {

   private static final Logger LOG = Logger.getLogger(VersionApi.class);
   private final VersionDao dao;

   public VersionApi(final VersionDao dao) {
      this.dao = dao;
   }

   public String latestVersion() {
      return dao.readLatest();
   }

   public void defineNewVersion(final String newVersion) {
      try {
         dao.insert(newVersion);
      } catch (DatabaseException e) {
         LOG.error(e.getMessage());
      }
   }

}
