package org.integratedmodelling.klab.components.geospace.extents;

import java.util.Iterator;

import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;

public class Grid implements IGrid {

    public Grid() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Iterator<Cell> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double snapX(double xCoordinate, int direction) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double snapY(double yCoordinate, int direction) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getYCells() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getXCells() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCellCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getCellArea(boolean forceSquareMeters) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOffset(int x, int y) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isActive(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getOffsetFromWorldCoordinates(double lon, double lat) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int[] getXYOffsets(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double[] getCoordinates(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Locator getLocator(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getMinX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMaxX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMinY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMaxY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getCellWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getCellHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

}
