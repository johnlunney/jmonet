package com.defano.jmonet.tools.base;

import com.defano.jmonet.model.PaintToolType;
import com.defano.jmonet.tools.util.Geometry;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Mouse and keyboard handler for tools defining a line drawn between two points. When the shift key is held down, the
 * defined line is constrained to the nearest 15 degree angle.
 */
public abstract class AbstractLineTool extends PaintTool {

    private Point initialPoint;

    public AbstractLineTool(PaintToolType type) {
        super(type);
        setToolCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected abstract void drawLine(Graphics2D g, Stroke stroke, Paint paint, int x1, int y1, int x2, int y2);

    /** {@inheritDoc} */
    @Override
    public void mouseMoved(MouseEvent e, Point imageLocation) {
        setToolCursor(getToolCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(MouseEvent e, Point imageLocation) {
        initialPoint = imageLocation;
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(MouseEvent e, Point imageLocation) {
        getCanvas().clearScratch();

        Point currentLoc = imageLocation;

        if (e.isShiftDown()) {
            currentLoc = Geometry.line(initialPoint, currentLoc, getConstrainedAngle());
        }

        Graphics2D g2d = (Graphics2D) getCanvas().getScratchImage().getGraphics();
        drawLine(g2d, getStroke(), getStrokePaint(), initialPoint.x, initialPoint.y, currentLoc.x, currentLoc.y);
        g2d.dispose();

        getCanvas().invalidateCanvas();
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased(MouseEvent e, Point imageLocation) {
        getCanvas().commit();
    }
}
