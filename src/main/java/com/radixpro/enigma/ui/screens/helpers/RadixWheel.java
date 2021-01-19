/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.be.util.Range;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.FullChart;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.references.MundanePointsAstron;
import com.radixpro.enigma.ui.charts.screens.helpers.*;
import com.radixpro.enigma.ui.creators.PlotCoordinatesFactory;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.api.AspectsApi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.GLYPH_FONTNAME;
import static com.radixpro.enigma.shared.common.EnigmaDictionary.TEXT_FONTNAME;

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
   private final AspectsApi aspectsApi;
   private GraphicsContext gc;
   private ChartDrawMetrics metrics;
   private FullChart fChart;
   private double offsetAsc;
   private double corrForXY;
   private Configuration currentConfig;

   public RadixWheel(@NotNull final AspectsApi aspectsApi) {
      this.aspectsApi = aspectsApi;
   }

   public void drawWheel(@NotNull final GraphicsContext gc, @NotNull final ChartDrawMetrics metrics, @NotNull final FullChart fullChart,
                         @NotNull final Configuration currentConfig) {
      this.gc = gc;
      this.metrics = metrics;
      this.fChart = fullChart;
      this.currentConfig = currentConfig;
      defineGlobals();
      performDraw();
   }

   private void defineGlobals() {
      corrForXY = metrics.getOffsetOuterCircle() + metrics.getSizeOuterCircle() / 2;
      offsetAsc = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude() % 30;
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
      drawAspects();
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
         positions = separator.defineCoordinates(angle + 180.0);
         gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         angle += 30.0;
      }
   }

   private void drawDegreeLines() {
      prepareSmallLines();
      double angle = 30 - offsetAsc % 30;
//      int offsetIndex5Degrees = (int)(5 - Math.round( angle % 5));
      double[] positions;
      DegreeLinePlotCoordinates degreeLine;
      for (int i = 0; i <= 359; i++) {
         degreeLine = PlotCoordinatesFactory.createDegreeLinePlotCoordinates(30.0 - angle, metrics);
         positions = degreeLine.defineCoordinates(i, metrics);
         gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         angle += 1.0;
      }
   }

   private void drawCorners() {
      prepareThickLines();
      double angleMc = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude() - fChart.getCalculatedChart().getMundPoints().getMc().getLongitude();
      CornerLines cornerLines = new CornerLines(metrics);
      double[] coordinates = cornerLines.defineCoordinates(angleMc);
      gc.strokeLine(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);         // asc
      gc.strokeLine(coordinates[4], coordinates[5], coordinates[6], coordinates[7]);         // desc
      gc.strokeLine(coordinates[8], coordinates[9], coordinates[10], coordinates[11]);       // mc
      gc.strokeLine(coordinates[12], coordinates[13], coordinates[14], coordinates[15]);     // ic
   }

   private void drawHouses() {
      prepareSmallLines();
      boolean quadrantSystem = currentConfig.getAstronConfiguration().getHouseSystem().isQuadrantSystem();
      double angle;
      double[] positions;
      double asc = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude();
      List<IPosition> cusps = fChart.getCalculatedChart().getMundPoints().getCusps();
      CuspLinePlotCoordinates cuspLine;
      for (int i = 1; i <= 12; i++) {
         if (!quadrantSystem || (i != 1 && i != 4 && i != 7 && i != 10)) {
            double longitude = cusps.get(i).getLongitude();
            angle = asc - longitude;
            cuspLine = PlotCoordinatesFactory.createCuspLinePlotCoordinates(angle + 180.0, metrics);
            positions = cuspLine.defineCoordinates(angle, metrics);
            gc.strokeLine(positions[0], positions[1], positions[2], positions[3]);
         }
      }
   }

   private void drawCornerPositions() {
      preparePositonTexts();
      double angleMc = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude() - fChart.getCalculatedChart().getMundPoints().getMc().getLongitude();
      CornerPositions cornerPositions = new CornerPositions(metrics);
      double[] coordinates = cornerPositions.defineCoordinates(angleMc);
      String posText = new SexagesimalFormatter(2).formatDm(fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude() % 30.0);
      gc.fillText(posText, coordinates[0], coordinates[1]);
      gc.fillText(posText, coordinates[2], coordinates[3]);
      posText = new SexagesimalFormatter(2).formatDm(fChart.getCalculatedChart().getMundPoints().getMc().getLongitude() % 30.0);
      gc.fillText(posText, coordinates[4], coordinates[5]);
      gc.fillText(posText, coordinates[6], coordinates[7]);
   }

   private void drawCuspPositions() {
      preparePositonTexts();
      boolean quadrantSystem = currentConfig.getAstronConfiguration().getHouseSystem().isQuadrantSystem();
      double asc = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude();
      List<IPosition> cusps = fChart.getCalculatedChart().getMundPoints().getCusps();
      CuspTextPlotCoordinates cuspText;
      double[] coordinates;
      double angle;
      for (int i = 1; i <= 12; i++) {
         if (!quadrantSystem || (i != 1 && i != 4 && i != 7 && i != 10)) {
            angle = Range.INSTANCE.checkValue(asc - cusps.get(i).getLongitude(), 0.0, 360.0);
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
         int signIndex = (int) (fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude() / 30) + i;
         if (signIndex > 12) signIndex -= 12;
         gc.fillText(new GlyphForSign().getGlyph(signIndex), point.getXPos() + corrForXY - metrics.getOffSetGlyphs(), point.getYPos() + corrForXY + metrics.getOffSetGlyphs());
         angle -= 30.0;
         if (angle < 0.0) angle += 360.0;
      }
   }

   private void drawCelObjects() {
      prepareCelObjects();
      List<IPosition> bodies = fChart.getCalculatedChart().getCelPoints();
      double ascendant = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude();
      double longitude;
      double angle;
      double angle1;
      double angle2;
      double minDist = metrics.getMinAngleObjects();
      double distance;
      final List<PlotBodyInfo> plotBodyInfos = new ArrayList<>();
      for (IPosition bodyPos : bodies) {
         longitude = bodyPos.getLongitude();
         angle = Range.INSTANCE.checkValue(ascendant - longitude, 0.0, 360.0);
         plotBodyInfos.add(new PlotBodyInfo(bodyPos.getChartPoint(), angle, longitude));
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

   private void drawConnectLines(@NotNull final PlotBodyInfo plotBodyInfo) {
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

   private void drawCelObjectPosition(@NotNull final PlotBodyInfo plotBodyInfo) {
      gc.setFont(new Font(TEXT_FONTNAME, metrics.getSizeTextFont()));
      gc.setStroke(CELBODY_POSITION_COLOR);
      gc.setFill(CELBODY_POSITION_COLOR);
      double[] coordinates = new CelObjectPlotPosition(metrics).defineCoordinates(plotBodyInfo.getCorrectedAngle());
      gc.fillText(plotBodyInfo.getPosText(), coordinates[0], coordinates[1]);
   }

   private void drawAspects() {
      List<PointInfoForAspect> pointInfos = new ArrayList<>();
      DrawAspectHelper helper = new DrawAspectHelper();
      List<IPosition> celObjectList = fChart.getCalculatedChart().getCelPoints();
      List<IPosition> specMundPoints = fChart.getCalculatedChart().getMundPoints().getSpecPoints();
      List<IPosition> housesList = new ArrayList<>();
      housesList.add(specMundPoints.get(0));
      housesList.add(specMundPoints.get(1));
      final List<IAnalyzedPair> aspects = aspectsApi.analyzeAspects(celObjectList, housesList, currentConfig.getDelinConfiguration().getAspectConfiguration());
      List<IPosition> bodies = fChart.getCalculatedChart().getCelPoints();
      double ascendant = fChart.getCalculatedChart().getMundPoints().getAsc().getLongitude();
      double angleFromAsc;
      for (IPosition bodyPos : bodies) {
         double longitude = bodyPos.getLongitude();
         angleFromAsc = Range.INSTANCE.checkValue(ascendant - longitude, 0.0, 360.0);
         pointInfos.add(new PointInfoForAspect(bodyPos.getChartPoint(), angleFromAsc));
      }
      angleFromAsc = Range.INSTANCE.checkValue(ascendant - fChart.getCalculatedChart().getMundPoints().getMc().getLongitude(), 0.0, 360.0);
      pointInfos.add(new PointInfoForAspect(MundanePointsAstron.MC, angleFromAsc));
      pointInfos.add(new PointInfoForAspect(MundanePointsAstron.ASC, 0.0));
      final List<DrawableLine> drawableLines = helper.createDrawLines(aspects, pointInfos, metrics);
      gc.setGlobalAlpha(0.6);
      for (DrawableLine line : drawableLines) {
         gc.setStroke(line.getLineColor());
         gc.setFill(line.getLineColor());
         gc.setLineWidth(line.getLineWidth());
         gc.strokeLine(line.getStartPoint().getXPos() + corrForXY, line.getStartPoint().getYPos() + corrForXY,
               line.getEndPoint().getXPos() + corrForXY, line.getEndPoint().getYPos() + corrForXY);
      }
   }

}
