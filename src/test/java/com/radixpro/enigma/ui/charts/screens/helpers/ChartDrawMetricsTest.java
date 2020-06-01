/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class ChartDrawMetricsTest {

   private ChartDrawMetrics metrics;

   @Before
   public void setUp() {
      metrics = new ChartDrawMetrics();
   }

   @Test
   public void getCanvasDimension() {
      assertEquals(700.0, metrics.getCanvasDimension(), DELTA_8_POS);
   }

   @Test
   public void setCanvasDimension() {
      metrics.setCanvasDimension(444.0);
      assertEquals(444.0, metrics.getCanvasDimension(), DELTA_8_POS);
   }

   @Test
   public void getSizeOuterCircle() {
      assertEquals(560.0, metrics.getSizeOuterCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(280.0, metrics.getSizeOuterCircle(), DELTA_8_POS);
   }

   @Test
   public void getSizeSignsCircle() {
      assertEquals(490.0, metrics.getSizeSignsCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(245.0, metrics.getSizeSignsCircle(), DELTA_8_POS);
   }

   @Test
   public void getOffsetHousesCircle() {
      assertEquals(210.0, metrics.getOffsetHousesCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(105.0, metrics.getOffsetHousesCircle(), DELTA_8_POS);
   }

   @Test
   public void getSizeHousesCircle() {
      assertEquals(280.0, metrics.getSizeHousesCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(140.0, metrics.getSizeHousesCircle(), DELTA_8_POS);
   }

   @Test
   public void getSizeGlyphFont() {
      assertEquals(24.5, metrics.getSizeGlyphFont(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(12.25, metrics.getSizeGlyphFont(), DELTA_8_POS);
   }

   @Test
   public void getSizeTextFont() {
      assertEquals(9.1, metrics.getSizeTextFont(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(4.55, metrics.getSizeTextFont(), DELTA_8_POS);
   }

   @Test
   public void getOffSetGlyphs() {
      assertEquals(8.166666666666666667, metrics.getOffSetGlyphs(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(4.08333333333333333334, metrics.getOffSetGlyphs(), DELTA_8_POS);
   }

   @Test
   public void getDiameterCelBodiesMedium() {
      assertEquals(175.0, metrics.getDiameterCelBodiesMedium(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(87.5, metrics.getDiameterCelBodiesMedium(), DELTA_8_POS);
   }

   @Test
   public void getMinAngleObjects() {
      assertEquals(5.833333333333333333, metrics.getMinAngleObjects(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(2.9166666666666667, metrics.getMinAngleObjects(), DELTA_8_POS);
   }

   @Test
   public void getDistanceConnectLines() {
      assertEquals(14.0, metrics.getDistanceConnectLines(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(7.0, metrics.getDistanceConnectLines(), DELTA_8_POS);
   }

   @Test
   public void getDiameterPosTextsLeft() {
      assertEquals(210.0, metrics.getDiameterPosTextsLeft(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(105.0, metrics.getDiameterPosTextsLeft(), DELTA_8_POS);
   }

   @Test
   public void getDiameterPosTextsRight() {
      assertEquals(192.5, metrics.getDiameterPosTextsRight(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(96.25, metrics.getDiameterPosTextsRight(), DELTA_8_POS);
   }

   @Test
   public void getDiameterPosTextsTop() {
      assertEquals(192.5, metrics.getDiameterPosTextsTop(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(96.25, metrics.getDiameterPosTextsTop(), DELTA_8_POS);
   }

   @Test
   public void getDiameterPosTextsBottom() {
      assertEquals(199.5, metrics.getDiameterPosTextsBottom(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(99.75, metrics.getDiameterPosTextsBottom(), DELTA_8_POS);
   }

   @Test
   public void getDiameterCuspTextsLeft() {
      assertEquals(238.0, metrics.getDiameterCuspTextsLeft(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(119.0, metrics.getDiameterCuspTextsLeft(), DELTA_8_POS);
   }

   @Test
   public void getDiameterCuspTextsRight() {
      assertEquals(217, metrics.getDiameterCuspTextsRight(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(108.5, metrics.getDiameterCuspTextsRight(), DELTA_8_POS);
   }

   @Test
   public void getDiameterCuspTextsTop() {
      assertEquals(231.0, metrics.getDiameterCuspTextsTop(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(115.5, metrics.getDiameterCuspTextsTop(), DELTA_8_POS);
   }

   @Test
   public void getDiameterCuspTextsBottom() {
      assertEquals(231, metrics.getDiameterCuspTextsBottom(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(115.5, metrics.getDiameterCuspTextsBottom(), DELTA_8_POS);
   }

   @Test
   public void getOffsetOuterCircle() {
      assertEquals(70.0, metrics.getOffsetOuterCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(35.0, metrics.getOffsetOuterCircle(), DELTA_8_POS);
   }

   @Test
   public void getOffsetSignsCircle() {
      assertEquals(105.0, metrics.getOffsetSignsCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(52.5, metrics.getOffsetSignsCircle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterHousesCircle() {
      assertEquals(140.0, metrics.getDiameterHousesCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(70.0, metrics.getDiameterHousesCircle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterSignsCircle() {
      assertEquals(245.0, metrics.getDiameterSignsCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(122.5, metrics.getDiameterSignsCircle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterOuterCircle() {
      assertEquals(280.0, metrics.getDiameterOuterCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(140.0, metrics.getDiameterOuterCircle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterDegrees5Circle() {
      assertEquals(240.1, metrics.getDiameterDegrees5Circle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(120.05, metrics.getDiameterDegrees5Circle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterDegreesCircle() {
      assertEquals(242.55, metrics.getDiameterDegreesCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(121.275, metrics.getDiameterDegreesCircle(), DELTA_8_POS);
   }

   @Test
   public void getDiameterSignGlyphsCircle() {
      assertEquals(262.5, metrics.getDiameterSignGlyphsCircle(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(131.25, metrics.getDiameterSignGlyphsCircle(), DELTA_8_POS);
   }

   @Test
   public void getWidthThickLines() {
      assertEquals(3.5, metrics.getWidthThickLines(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(1.75, metrics.getWidthThickLines(), DELTA_8_POS);
   }

   @Test
   public void getWidthMediumLines() {
      assertEquals(1.75, metrics.getWidthMediumLines(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(0.875, metrics.getWidthMediumLines(), DELTA_8_POS);
   }

   @Test
   public void getWidthThinLines() {
      assertEquals(1.26, metrics.getWidthThinLines(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(0.63, metrics.getWidthThinLines(), DELTA_8_POS);
   }

   @Test
   public void getCorrForXY() {
      assertEquals(350.0, metrics.getCorrForXY(), DELTA_8_POS);
      metrics.setCanvasDimension(350.0);
      assertEquals(175.0, metrics.getCorrForXY(), DELTA_8_POS);
   }

}