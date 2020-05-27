/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.xchg.domain.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedAspect;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The aspectlines to draw.
 */
public class DrawAspectHelper {

   public List<DrawableLine> createDrawLines(final List<IAnalyzedPair> aspects, final List<PointInfoForAspect> points,
                                             final ChartDrawMetrics metrics) {
      double lineWidth;
      List<DrawableLine> lines = new ArrayList<>();
      for (IAnalyzedPair pair : aspects) {
         AnalyzedAspect aspect = (AnalyzedAspect) pair;
         PointInfoForAspect firstPointInfo = retrievePointInfo(aspect.getFirst(), points);
         PointInfoForAspect secondPointInfo = retrievePointInfo(aspect.getSecond(), points);
         Point firstPoint = calculatePoint(firstPointInfo.getAngleFromAsc(), metrics);
         Point secondPoint = calculatePoint(secondPointInfo.getAngleFromAsc(), metrics);
         lineWidth = defineLineWidth(metrics.getWidthThickLines(), aspect.getPercOrb());
         Color lineColor = defineColor(aspect);
         lines.add(new DrawableLine(firstPoint, secondPoint, lineWidth, lineColor));
      }
      return lines;
   }

   private PointInfoForAspect retrievePointInfo(AnalyzablePoint analyzablePoint, List<PointInfoForAspect> points) {
      for (PointInfoForAspect pointInfo : points) {
         if (analyzablePoint.getChartPoint() == pointInfo.getPoint()) return pointInfo;
      }

      throw new RuntimeException("Mismatch when combining aspectinfo for analyzablePoint: " + analyzablePoint.getChartPoint().getRbKey());   // TODO create specific exception
   }

   private Point calculatePoint(double angleFromAsc, ChartDrawMetrics metrics) {
      double hypothenusa = metrics.getSizeHousesCircle() / 2;
      return new RectTriangle(hypothenusa, angleFromAsc + 180.0).getPointAtEndOfHyp();
   }

   private double defineLineWidth(final double maxLineWidth, final double percOrb) {
      return Math.max(0.5, maxLineWidth * (100.0 - percOrb) / 100.0);
   }

   private Color defineColor(final AnalyzedAspect aspect) {
      AspectTypes type = aspect.getAspectType();
      if (type == AspectTypes.OPPOSITION || type == AspectTypes.SQUARE) return Color.RED;
      if (type == AspectTypes.TRIANGLE || type == AspectTypes.SEXTILE) return Color.GREEN;
      if (type == AspectTypes.CONJUNCTION) return Color.ORANGE;
      return Color.BROWN;
   }


}
