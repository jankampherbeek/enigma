/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EclipticProjectionsTest {

   private EclipticProjections projection;

   @Before
   public void setUp() {
      projection = EclipticProjections.SIDEREAL;
   }

   @Test
   public void getId() {
      assertEquals(2, projection.getId());
   }

   @Test
   public void getNameForRB() {
      assertEquals("eclipticprojections.sidereal", projection.getNameForRB());
   }

   @Test
   public void getProjectionForId() throws UnknownIdException {
      assertEquals(EclipticProjections.TROPICAL, projection.getProjectionForId(1));
   }

   @Test(expected = UnknownIdException.class)
   public void getProjectionForIdNotFound() throws UnknownIdException {
      projection.getProjectionForId(1000);
   }

   @Test
   public void getObservableList() {
      assertEquals(2, projection.getObservableList().size());
   }


   @Test
   public void getIndexMappings() {
      IndexMappingsList imList = projection.getIndexMappings();
      assertEquals(2, imList.getAllIndexMappings().size());
   }
}