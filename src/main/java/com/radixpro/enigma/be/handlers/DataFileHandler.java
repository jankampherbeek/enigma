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
 * Handler to access datafiles using the internal Json format.
 */
public class DataFileHandler {

   private final DataFileDao dao;
   private static final String PROJDIR_KEY = "projdir";
   private static final String DATA_FOLDER = "data";
   private final String projDir;

   public DataFileHandler(@NotNull final DataFileDao dao,
                          @NotNull final PersistedPropertyApi propApi) {
      this.dao = dao;
      projDir = propApi.read(PROJDIR_KEY).get(0).getValue();
   }

   public List<DataFileDescription> readDataFileDesciptions() {
      File projDirFile = new File(projDir + File.separator + DATA_FOLDER + File.separator);
      return dao.readDataFileList(projDirFile);
   }


}
