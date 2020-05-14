/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HouseSystemsTest {

   private HouseSystems houseSystem;

   @Before
   public void setUp() {
      houseSystem = HouseSystems.ALCABITIUS;
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getSeId() {
      String seId = "B";
      assertEquals(seId, houseSystem.getSeId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getId() {
      int id = 11;
      assertEquals(id, houseSystem.getId());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getNrOfCusps() {
      assertEquals(12, houseSystem.getNrOfCusps());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getRbKeyForName() {
      assertEquals("houses.alcabitius", houseSystem.getNameForRB());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void isCounterClockwise() {
      assertTrue(houseSystem.isCounterClockwise());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void isQuadrantSystem() {
      assertTrue(houseSystem.isQuadrantSystem());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void isCuspIsStart() {
      assertTrue(houseSystem.isCuspIsStart());
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void total() {
      assertEquals(21, HouseSystems.values().length);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getSystemForId() throws UnknownIdException {
      assertEquals(HouseSystems.PLACIDUS, houseSystem.getSystemForId(6));
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db(expected = UnknownIdException.class)
   public void getSystemForIdNotFound() throws UnknownIdException {
      houseSystem.getSystemForId(1000);
   }

   //   @Test   TODO release 2020.2: disabled test, requires init for db
   public void getObservableList() {
      assertEquals(20, houseSystem.getObservableList().size());
   }

}