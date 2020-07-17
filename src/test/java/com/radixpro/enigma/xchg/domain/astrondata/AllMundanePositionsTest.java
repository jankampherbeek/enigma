/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AllMundanePositionsTest {

   @Mock
   private IPosition cusp1Mock;
   @Mock
   private IPosition cusp2Mock;
   @Mock
   private IPosition specPos1Mock;
   @Mock
   private IPosition specPos2Mock;
   private AllMundanePositions allMundanePositions;

   @Before
   public void setUp() throws Exception {
      List<IPosition> cuspList = new ArrayList<>();
      cuspList.add(cusp1Mock);
      cuspList.add(cusp2Mock);
      List<IPosition> specPosList = new ArrayList<>();
      specPosList.add(specPos1Mock);
      specPosList.add(specPos2Mock);
      allMundanePositions = new AllMundanePositions(cuspList, specPosList);
   }

   @Test
   public void getCusps() {
      assertEquals(2, allMundanePositions.getCusps().size());
      assertEquals(cusp1Mock, allMundanePositions.getCusps().get(0));
   }

   @Test
   public void getSpecPoints() {
      assertEquals(2, allMundanePositions.getSpecPoints().size());
      assertEquals(specPos2Mock, allMundanePositions.getSpecPoints().get(1));
   }
}