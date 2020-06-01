/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HouseSystemsTest {

   private HouseSystems houseSystem;

   @Before
   public void setUp() {
      houseSystem = HouseSystems.ALCABITIUS;
   }

   @Test
   public void getSeId() {
      String seId = "B";
      assertEquals(seId, houseSystem.getSeId());
   }

   @Test
   public void getId() {
      int id = 11;
      assertEquals(id, houseSystem.getId());
   }

   @Test
   public void getNrOfCusps() {
      assertEquals(12, houseSystem.getNrOfCusps());
   }

   @Test
   public void getRbKeyForName() {
      assertEquals("houses.alcabitius", houseSystem.getNameForRB());
   }

   @Test
   public void isCounterClockwise() {
      assertTrue(houseSystem.isCounterClockwise());
   }

   @Test
   public void isQuadrantSystem() {
      assertTrue(houseSystem.isQuadrantSystem());
   }

   @Test
   public void isCuspIsStart() {
      assertTrue(houseSystem.isCuspIsStart());
   }

   @Test
   public void total() {
      assertEquals(21, HouseSystems.values().length);
   }

   @Test
   public void getSystemForId() throws UnknownIdException {
      assertEquals(HouseSystems.PLACIDUS, houseSystem.getSystemForId(6));
   }

   @Test(expected = UnknownIdException.class)
   public void getSystemForIdNotFound() throws UnknownIdException {
      houseSystem.getSystemForId(1000);
   }

   @Test
   public void getObservableList() {
      assertEquals(20, houseSystem.getObservableList().size());
   }

   @Test
   public void getIndexMappings() {
      assertEquals(20, houseSystem.getIndexMappings().getAllIndexMappings().size());
   }
}