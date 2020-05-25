/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.be.astron.assist.HousePosition;
import com.radixpro.enigma.be.astron.main.CelObjectPosition;
import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.ui.shared.factories.PlotCoordinatesFactory;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.api.CalculatedFullChart;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Image of a radix wheel.
 */
public class RadixWheel {

   private static final Color FRAME_COLOR = Color.MIDNIGHTBLUE;
   private static final Color HOUSE_POSITION_COLOR = Color.DARKRED;
   private static final Color CELBODY_POSITION_COLOR = Color.DARKGREEN;
   private static final Color GLYPH_COLOR = Color.BLACK;
   private static final Color SIGN_GLYPH_COLOR = Color.DARKBLUE;
   private static final double FRAME_GLOBAL_ALPHA = 0.9d;
   private static final double TRANSPARENT_GLOBAL_ALPHA = 0.3d;
   private static final String GLYPH_FONTNAME = "EnigmaAstrology";
   private static final String TEXT_FONTNAME = "Arial";
   private final GraphicsContext gc;
   private final ChartIDrawMetrics metrics;
   private final CalculatedFullChart cfChart;
   private double offsetAsc;
   private double corrForXY;

   /**
    * Constructing this class automatically draws a radix wheel, works like a SVG image.
    *
    * @param gc                  The GraphicsContext
    * @param metrics             Dynamic metrics, will be resized if required.
    * @param calculatedFullChart Data for the calcualted chart.
    */
   public RadixWheel(final GraphicsContext gc, final ChartIDrawMetrics metrics,
                     final CalculatedFullChart calculatedFullChart) {
      this.gc = checkNotNull(gc);
      this.metrics = checkNotNull(metrics);
      this.cfChart = checkNotNull(calculatedFullChart);
      defineGlobals();
      performDraw();
   }

   private void defineGlobals() {
      corrForXY = metrics.getOffsetOuterCircle() + metrics.getSizeOuterCircle() / 2;
      offsetAsc = cfChart.getHouseValues().getAscendant().getLongitude() % 30;
   }

   private void prepareCircles() {
      gc.setLineWidth(metrics.getWidthMediumLines());
      gc.setStroke(FRAME_COLOR);
      gc.setGlobalAlpha(FRAME_GLOBAL_ALPHA);
   }

   private void prepareSmallLines() {
      gc.setLineWidth(metrics.getWidthThinLines());
      gc.setStroke(FRAME_COLOR);
      gc.setGlobalAlpha(TRANSPARENT_GLOBAL_ALPHA);
   }

   private void prepareMediumLines() {
      gc.setLineWidth(metrics.getWidthMediumLines());
      gc.setStroke(FRAME_COLOR);
      gc.setGlobalAlpha(TRANSPARENT_GLOBAL_ALPHA);
   }

   private void prepareThickLines() {
      gc.setLineWidth(metrics.getWidthThickLines());
      gc.setStroke(FRAME_COLOR);
      gc.setGlobalAlpha(TRANSPARENT_GLOBAL_ALPHA);
   }

   private void prepareConnectLines() {
      gc.setStroke(Color.GREEN);
      gc.setFill(Color.GREEN);
      gc.setLineWidth(1d);
      gc.setGlobalAlpha(0.3);
   }

   private void preparePositonTexts() {
      gc.setLineWidth(metrics.getWidthThinLines());
      gc.setStroke(HOUSE_POSITION_COLOR);
      gc.setFill(HOUSE_POSITION_COLOR);
      gc.setFont(new Font(TEXT_FONTNAME, metrics.getSizeTextFont()));
      gc.setGlobalAlpha(FRAME_GLOBAL_ALPHA);
   }

   private void prepareSignGlyphs() {
      gc.setStroke(SIGN_GLYPH_COLOR);
      gc.setFill(SIGN_GLYPH_COLOR);
      gc.setFont(new Font(GLYPH_FONTNAME, metrics.getSizeGlyphFont()));
      gc.setGlobalAlpha(TRANSPARENT_GLOBAL_ALPHA);
   }

   private void prepareCelObjects() {
      gc.setStroke(GLYPH_COLOR);
      gc.setFill(GLYPH_COLOR);
      gc.setLineWidth(metrics.getWidthThinLines());
      gc.setGlobalAlpha(FRAME_GLOBAL_ALPHA);
      gc.setFont(new Font(GLYPH_FONTNAME, metrics.getSizeGlyphFont()));
   }

   private void performDraw() {
      drawCircles();
      drawSignSeparators();
      drawDegreeLines();
      drawCorners();
      drawHouses();
      drawCornerPositions();
      drawCuspPositions();
      drawSignGlyphs();
      drawCelObjects();
   }

   private void drawCircles() {
      prepareCircles();
      drawSpecificCircle(metrics.getOffsetOuterCircle(), metrics.getSizeOuterCircle());
      drawSpecificCircle(metrics.getOffsetSignsCircle(), metrics.getSizeSignsCircle());
      drawSpecificCircle(metrics.getOffsetHousesCircle(), metrics.getSizeHousesCircle());
   }

   private void drawSpecificCircle(final double offset, final double size) {
      gc.strokeOval(offset, offset, size, size);
   }

   private void drawSignSeparators() {
      prepareMediumLines();
      final SignSeparator separator = new SignSeparator(metrics);
      double angle = offsetAsc;
      double[] positions;
      for (int i = 1; i <= 12; i++) {
         positions = separator.defineCoordinates(angle);
         gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         angle += 30.0;
      }
   }

   private void drawDegreeLines() {
      prepareSmallLines();
      double angle = 30 - offsetAsc % 30;
      double[] positions;
      DegreeLinePlotCoordinates degreeLine;
      for (int i = 0; i <= 359; i++) {
         degreeLine = PlotCoordinatesFactory.createDegreeLinePlotCoordinates(angle, metrics);
         positions = degreeLine.defineCoordinates(i, metrics);
         gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         angle += 1.0;
      }
   }

   private void drawCorners() {
      prepareThickLines();
      double angleMc = cfChart.getHouseValues().getAscendant().getLongitude()
            - cfChart.getHouseValues().getMc().getLongitude();
      CornerLines cornerLines = new CornerLines(metrics);
      double[] coordinates = cornerLines.defineCoordinates(angleMc);
      gc.strokeLine(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);         // asc
      gc.strokeLine(coordinates[4], coordinates[5], coordinates[6], coordinates[7]);         // desc
      gc.strokeLine(coordinates[8], coordinates[9], coordinates[10], coordinates[11]);       // mc
      gc.strokeLine(coordinates[12], coordinates[13], coordinates[14], coordinates[15]);     // ic
   }

   private void drawHouses() {
      prepareSmallLines();
      boolean quadrantSystem = cfChart.getSettings().getHouseSystem().isQuadrantSystem();
      double angle;
      double[] positions;
      double asc = cfChart.getHouseValues().getAscendant().getLongitude();
      List<HousePosition> cusps = cfChart.getHouseValues().getCusps();
      CuspLinePlotCoordinates cuspLine;
      for (int i = 1; i <= 12; i++) {
         if (!quadrantSystem || (i != 1 && i != 4 && i != 7 && i != 10)) {
            double longitude = cusps.get(i).getLongitude();
            angle = asc - longitude;
            cuspLine = PlotCoordinatesFactory.createCuspLinePlotCoordinates(angle, metrics);
            positions = cuspLine.defineCoordinates(angle, metrics);
            gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         }
      }
   }

   private void drawCornerPositions() {
      preparePositonTexts();
      double angleMc = cfChart.getHouseValues().getAscendant().getLongitude() - cfChart.getHouseValues().getMc().getLongitude();
      CornerPositions cornerPositions = new CornerPositions(metrics);
      double[] coordinates = cornerPositions.defineCoordinates(angleMc);
      String posText = new SexagesimalFormatter(2).formatDm(cfChart.getHouseValues().getAscendant().getLongitude() % 30.0);
      gc.fillText(posText, coordinates[0], coordinates[1]);
      gc.fillText(posText, coordinates[2], coordinates[3]);
      posText = new SexagesimalFormatter(2).formatDm(cfChart.getHouseValues().getMc().getLongitude() % 30.0);
      gc.fillText(posText, coordinates[4], coordinates[5]);
      gc.fillText(posText, coordinates[6], coordinates[7]);
   }

   private void drawCuspPositions() {
      preparePositonTexts();
      boolean quadrantSystem = cfChart.getSettings().getHouseSystem().isQuadrantSystem();
      double asc = cfChart.getHouseValues().getAscendant().getLongitude();
      List<HousePosition> cusps = cfChart.getHouseValues().getCusps();
      CuspTextPlotCoordinates cuspText;
      double[] coordinates;
      double angle;
      for (int i = 1; i <= 12; i++) {
         if (!quadrantSystem || (i != 1 && i != 4 && i != 7 && i != 10)) {
            angle = new Range(0.0, 360.0).checkValue(asc - cusps.get(i).getLongitude());
            cuspText = PlotCoordinatesFactory.createCuspTextPlotCoordinates(angle + 180.0, metrics);
            coordinates = cuspText.defineCoordinates(angle, metrics);
            String posText = new SexagesimalFormatter(2).formatDm(cusps.get(i).getLongitude() % 30.0);
            gc.fillText(posText, coordinates[0], coordinates[1]);
         }
      }
   }

   private void drawSignGlyphs() {
      prepareSignGlyphs();
      double angle = 165.0 + offsetAsc % 30;      // 180 degrees (correct quadrant) minus 15 (glyph in center of sign).
      for (int i = 1; i <= 12; i++) {
         Point point = new RectTriangle(metrics.getDiameterSignGlyphsCircle(), angle).getPointAtEndOfHyp();
         int signIndex = (int) (cfChart.getHouseValues().getAscendant().getLongitude() / 30) + i;
         if (signIndex > 12) signIndex -= 12;
         gc.fillText(new GlyphForSign().getGlyph(signIndex), point.getXPos() + corrForXY - metrics.getOffSetGlyphs(), point.getYPos() + corrForXY + metrics.getOffSetGlyphs());
         angle -= 30.0;
         if (angle < 0.0) angle += 360.0;
      }
   }

   private void drawCelObjects() {
      prepareCelObjects();
      List<CelObjectPosition> bodies = cfChart.getBodies();
      double ascendant = cfChart.getHouseValues().getAscendant().getLongitude();
      double longitude;
      double angle;
      double angle1;
      double angle2;
      double minDist = metrics.getMinAngleObjects();
      double distance;
      final List<PlotBodyInfo> plotBodyInfos = new ArrayList<>();
      for (CelObjectPosition bodyPos : bodies) {
         longitude = bodyPos.getEclipticalPosition().getMainPosition();
         angle = new Range(0.0, 360.0).checkValue(ascendant - longitude);
         plotBodyInfos.add(new PlotBodyInfo(bodyPos.getCelestialBody(), angle, longitude));
      }
      plotBodyInfos.sort(new PlotBodyInfoComparator());
      int maxIndex = plotBodyInfos.size() - 1;
      for (int i = 0; i < plotBodyInfos.size(); i++) {
         if (i > 0) {
            angle1 = plotBodyInfos.get(i).getCorrectedAngle();
            angle2 = plotBodyInfos.get(i - 1).getCorrectedAngle();
            distance = angle1 - angle2;
         } else {
            angle1 = plotBodyInfos.get(i).getCorrectedAngle();
            angle2 = plotBodyInfos.get(maxIndex).getCorrectedAngle();
            distance = angle1 - angle2 + 360.0;
         }
         if (distance < minDist) {
            plotBodyInfos.get(i).setCorrectedAngle(angle2 + minDist);
         }
      }
      CelObject celObject = new CelObject(metrics);
      for (PlotBodyInfo bodyInfo : plotBodyInfos) {
         gc.setFont(new Font(GLYPH_FONTNAME, metrics.getSizeGlyphFont()));  // TODO Relese 2020.2: add to prepare function
         int bodyIndex = bodyInfo.getCelObject().getId();
         double[] coordinates = celObject.defineCoordinates(bodyInfo.getCorrectedAngle());
         gc.setStroke(GLYPH_COLOR);
         gc.setFill(GLYPH_COLOR);
         gc.setGlobalAlpha(FRAME_GLOBAL_ALPHA);
         gc.fillText(new GlyphForCelObject().getGlyph(bodyIndex), coordinates[0], coordinates[1]);
         drawConnectLines(bodyInfo);
         drawCelObjectPosition(bodyInfo);
      }
   }

   private void drawConnectLines(final PlotBodyInfo plotBodyInfo) {
      checkNotNull(plotBodyInfo);
      prepareConnectLines();
      double hypothenusa1 = metrics.getDiameterCelBodiesMedium() - metrics.getDistanceConnectLines();
      double hypothenusa2 = metrics.getSizeHousesCircle() / 2;
      Point startPoint = new RectTriangle(hypothenusa1, plotBodyInfo.getCorrectedAngle() + 180.0).getPointAtEndOfHyp();
      Point endPoint = new RectTriangle(hypothenusa2, plotBodyInfo.getAngleFromAsc() + 180.0).getPointAtEndOfHyp();
      gc.strokeLine(startPoint.getXPos() + corrForXY, startPoint.getYPos() + corrForXY,
            endPoint.getXPos() + corrForXY, endPoint.getYPos() + corrForXY);
      gc.setStroke(GLYPH_COLOR);
      gc.setFill(GLYPH_COLOR);
      gc.setLineWidth(1d);
      gc.setGlobalAlpha(0.6);
   }

   private void drawCelObjectPosition(final PlotBodyInfo plotBodyInfo) {
      checkNotNull(plotBodyInfo);
      gc.setFont(new Font(TEXT_FONTNAME, metrics.getSizeTextFont()));
      gc.setStroke(CELBODY_POSITION_COLOR);
      gc.setFill(CELBODY_POSITION_COLOR);
      double[] coordinates = new CelObjectPlotPosition(metrics).defineCoordinates(plotBodyInfo.getCorrectedAngle());
      gc.fillText(plotBodyInfo.getPosText(), coordinates[0], coordinates[1]);
   }

}
