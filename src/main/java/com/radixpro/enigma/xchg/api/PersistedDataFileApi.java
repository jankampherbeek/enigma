/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.DataFileHandler;
import com.radixpro.enigma.domain.stats.DataFileDescription;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * API for datafiles.
 */
public class PersistedDataFileApi {

   private final DataFileHandler handler;

   public PersistedDataFileApi(@NotNull final DataFileHandler handler) {
      this.handler = handler;
   }

   public List<DataFileDescription> readDataFileDescriptions() {
      return handler.readDataFileDesciptions();
   }

}
