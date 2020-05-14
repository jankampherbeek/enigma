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
public class DelinConfigurationTest {

   @Mock
   private AspectConfiguration aspectConfigurationMock;

   private DelinConfiguration delinConfiguration;

   @Before
   public void setUp() {
      delinConfiguration = new DelinConfiguration(aspectConfigurationMock);
   }

   @Test
   public void getConfigDelin() {
      assertEquals(aspectConfigurationMock, delinConfiguration.getAspectConfiguration());
   }

}