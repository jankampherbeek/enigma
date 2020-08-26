/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.persistency.DataFileDao;
import com.radixpro.enigma.domain.stats.DataFileDescription;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * Handler to access datafiles
 */
public class DataFileHandler {

   private final DataFileDao dao;
   private final PersistedPropertyApi propApi;

   public DataFileHandler(@NotNull final DataFileDao dao, @NotNull final PersistedPropertyApi propApi) {
      this.dao = dao;
      this.propApi = propApi;
   }

   public List<DataFileDescription> readDataFileDesciptions() {
      final String projDir = propApi.read("projdir").get(0).getValue();
      File projDirFile = new File(projDir + File.separator + "data" + File.separator);
      return dao.readDataFileList(projDirFile);
   }

}
