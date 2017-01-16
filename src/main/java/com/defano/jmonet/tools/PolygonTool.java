package com.defano.jmonet.tools;

import com.defano.jmonet.model.PaintToolType;
import com.defano.jmonet.tools.base.AbstractPolylineTool;

import java.awt.*;

public class PolygonTool extends AbstractPolylineTool {

    public PolygonTool() {
        super(PaintToolType.POLYGON);
    }

    @Override
    protected void drawPolyline(Graphics2D g, Stroke stroke, Paint paint, int[] xPoints, int[] yPoints) {
        g.setPaint(paint);
        g.setStroke(stroke);
        g.drawPolyline(xPoints, yPoints, xPoints.length);
    }

    @Override
    protected void drawPolygon(Graphics2D g, Stroke stroke, Paint strokePaint, int[] xPoints, int[] yPoints) {
        g.setStroke(stroke);
        g.setPaint(strokePaint);
        g.drawPolygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    protected void fillPolygon(Graphics2D g, Paint fillPaint, int[] xPoints, int[] yPoints) {
        g.setPaint(getFillPaint());
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}