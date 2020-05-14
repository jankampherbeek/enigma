/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {

   private final long id = 3L;
   private final long parentId = 1L;
   private final String name = "Some config";
   private final String description = "Description of some config.";
   @Mock
   private AstronConfiguration astronConfigurationMock;
   @Mock
   private DelinConfiguration delinConfigurationMock;
   private Configuration config;

   @Before
   public void setUp() {
      config = new Configuration(id, parentId, name, description, astronConfigurationMock, delinConfigurationMock);
   }

   @Test
   public void getId() {
      assertEquals(id, config.getId());
   }

   @Test
   public void getParentId() {
      assertEquals(parentId, config.getParentId());
   }

   @Test
   public void getConfigAstron() {
      assertEquals(astronConfigurationMock, config.getAstronConfiguration());
   }

   @Test
   public void getConfigDelin() {
      assertEquals(delinConfigurationMock, config.getDelinConfiguration());
   }

   @Test
   public void getName() {
      assertEquals(name, config.getName());
   }

   @Test
   public void getDescription() {
      assertEquals(description, config.getDescription());
   }

   @Test
   public void testToString() {
      String expected = "Configuration(id=3, parentId=1, name=Some config, description=Description of some config., astronConfiguration=astronConfigurationMock, delinConfiguration=delinConfigurationMock)";
      assertEquals(expected, config.toString());
   }
}