/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Metrics for drawing a chart. Supports resizing.
 * This object is mutable as the value for canvasDimension can be set.
 */
public class ChartIDrawMetrics implements IDrawMetrics {

   private static final double BASE_DIMENSION = 700;
   private double canvasDimension;
   private double sizeOuterCircle;        // size of circle outside of ecliptic signs
   private double sizeSignsCircle;        // size of circle between houses and signs
   private double offsetHousesCircle;     // distance on x- and y-axis for drawing houses circle
   private double sizeHousesCircle;       // size of circle between aspect-space and houses
   private double sizeGlyphFont;          // size for glyphs, measured in points, both for signs and celestial bodies
   private double sizeTextFont;           // size for text, measured in points
   private double offSetGlyphs;           // correction to make sure the x-y-coordinates are at the center of the glyph
   private double diameterCelBodiesMedium;// radius for celestial Bodies (default)
   private double minAngleObjects;        // the minimum angle between celestial objects (to prevent overlap)
   private double distanceConnectLines;   // the distance between the plotposition for the glyph and the connect line
   private double diameterPosTextsLeft;   // distance for position texts in the left part of the chart
   private double diameterPosTextsRight;  // distance for position texts in the right part of the chart
   private double diameterPosTextsTop;    // distance for position texts in the top part of the chart
   private double diameterPosTextsBottom; // distance for position texts in hte bottom part of the chart
   private double diameterCuspTextsLeft;  // distance for position texts in the left part of the chart
   private double diameterCuspTextsRight; // distance for position texts in the right part of the chart
   private double diameterCuspTextsTop;   // distance for position texts in the top part of the chart
   private double diameterCuspTextsBottom;// distance for position texts in hte bottom part of the chart

   ///////
   private double offsetOuterCircle;         // distance on x- and y-axis for drawing outer circle
   private double offsetSignsCircle;         // distance on x- and y-axis for drawing signs circle
   private double diameterHousesCircle;      // diameter of circle between aspect-space and houses
   private double diameterSignsCircle;       // diameter of circle between houses and signs
   private double diameterOuterCircle;       // diameter of circle outside of the signs
   private double diameterDegrees5Circle;    // diameter degree-lines for degrees that are a multiple of 5
   private double diameterDegreesCircle;     // diameter degree-lines
   private double diameterSignGlyphsCircle;  // diameter of circle for placing sygn glyphs
   private double widthThickLines;           // width for thick lines (asc, mc, ic, desc).
   private double widthMediumLines;          // width for medium lines (separators between signs)
   private double widthThinLines;            // width for thin lines (cusps, degrees)
   private double corrForXY;                 // correction factor to add to x and y coordinates

   /**
    * Constructor initially sets the base-dimension to 700, this is the width and height of the canvas.
    */
   public ChartIDrawMetrics() {
      setCanvasDimension(BASE_DIMENSION);
   }

   private void defineValues() {
      offsetOuterCircle = canvasDimension * 0.1;
      sizeOuterCircle = canvasDimension * 0.8;
      sizeSignsCircle = canvasDimension * 0.7;
      sizeHousesCircle = canvasDimension * 0.4;
      offsetHousesCircle = offsetOuterCircle + ((sizeOuterCircle - sizeHousesCircle) / 2);
      sizeGlyphFont = canvasDimension * 0.035;
      sizeTextFont = canvasDimension * 0.013;
      widthThickLines = canvasDimension * 0.005;
      widthMediumLines = canvasDimension * 0.0025;
      widthThinLines = canvasDimension * 0.0018;
      offSetGlyphs = sizeGlyphFont / 3.0;

      diameterPosTextsLeft = canvasDimension * 0.3;
      diameterPosTextsRight = canvasDimension * 0.275;
      diameterPosTextsTop = canvasDimension * 0.275;
      diameterPosTextsBottom = canvasDimension * 0.285;

      diameterCuspTextsLeft = canvasDimension * 0.34;
      diameterCuspTextsRight = canvasDimension * 0.31;
      diameterCuspTextsTop = canvasDimension * 0.33;
      diameterCuspTextsBottom = canvasDimension * 0.33;
      diameterCelBodiesMedium = canvasDimension * 0.25;
      minAngleObjects = sizeGlyphFont / 4.2;  // value is in degrees
      distanceConnectLines = canvasDimension * 0.02;

      /////
      diameterOuterCircle = canvasDimension * 0.4;
      diameterSignsCircle = canvasDimension * 0.35;
      diameterHousesCircle = canvasDimension * 0.2;
      diameterDegrees5Circle = diameterSignsCircle * 0.98;
      diameterDegreesCircle = diameterSignsCircle * 0.99;
      diameterSignGlyphsCircle = canvasDimension * 0.375;


      offsetSignsCircle = offsetOuterCircle + diameterOuterCircle - diameterSignsCircle;

      corrForXY = getOffsetOuterCircle() + getSizeOuterCircle() / 2;
   }

   public double getCanvasDimension() {
      return this.canvasDimension;
   }

   /**
    * Change base-dimension. This method should be called after resizing by the user.
    *
    * @param newDimension new size of the canvasm it should the minimum of the height and width.
    */
   @Override
   public void setCanvasDimension(final double newDimension) {
      this.canvasDimension = newDimension;
      defineValues();
   }

   public double getSizeOuterCircle() {
      return this.sizeOuterCircle;
   }

   public double getSizeSignsCircle() {
      return this.sizeSignsCircle;
   }

   public double getOffsetHousesCircle() {
      return this.offsetHousesCircle;
   }

   public double getSizeHousesCircle() {
      return this.sizeHousesCircle;
   }

   public double getSizeGlyphFont() {
      return this.sizeGlyphFont;
   }

   public double getSizeTextFont() {
      return this.sizeTextFont;
   }

   public double getOffSetGlyphs() {
      return this.offSetGlyphs;
   }

   public double getDiameterCelBodiesMedium() {
      return this.diameterCelBodiesMedium;
   }

   public double getMinAngleObjects() {
      return this.minAngleObjects;
   }

   public double getDistanceConnectLines() {
      return this.distanceConnectLines;
   }

   public double getDiameterPosTextsLeft() {
      return this.diameterPosTextsLeft;
   }

   public double getDiameterPosTextsRight() {
      return this.diameterPosTextsRight;
   }

   public double getDiameterPosTextsTop() {
      return this.diameterPosTextsTop;
   }

   public double getDiameterPosTextsBottom() {
      return this.diameterPosTextsBottom;
   }

   public double getDiameterCuspTextsLeft() {
      return this.diameterCuspTextsLeft;
   }

   public double getDiameterCuspTextsRight() {
      return this.diameterCuspTextsRight;
   }

   public double getDiameterCuspTextsTop() {
      return this.diameterCuspTextsTop;
   }

   public double getDiameterCuspTextsBottom() {
      return this.diameterCuspTextsBottom;
   }

   public double getOffsetOuterCircle() {
      return this.offsetOuterCircle;
   }

   public double getOffsetSignsCircle() {
      return this.offsetSignsCircle;
   }

   public double getDiameterHousesCircle() {
      return this.diameterHousesCircle;
   }

   public double getDiameterSignsCircle() {
      return this.diameterSignsCircle;
   }

   public double getDiameterOuterCircle() {
      return this.diameterOuterCircle;
   }

   public double getDiameterDegrees5Circle() {
      return this.diameterDegrees5Circle;
   }

   public double getDiameterDegreesCircle() {
      return this.diameterDegreesCircle;
   }

   public double getDiameterSignGlyphsCircle() {
      return this.diameterSignGlyphsCircle;
   }

   public double getWidthThickLines() {
      return this.widthThickLines;
   }

   public double getWidthMediumLines() {
      return this.widthMediumLines;
   }

   public double getWidthThinLines() {
      return this.widthThinLines;
   }

   public double getCorrForXY() {
      return this.corrForXY;
   }
}
