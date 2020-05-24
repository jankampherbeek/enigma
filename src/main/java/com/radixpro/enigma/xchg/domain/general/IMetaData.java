/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.general;

/**
 * Interface for meta data: data bout a chart, progression etc.
 */
public interface IMetaData {

   /**
    * @return Name of chartowner, event, etc.
    */
   String getName();

   /**
    * @return Nam eof configuration used.
    */
   String getConfigName();

}
