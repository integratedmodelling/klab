package org.integratedmodelling.klab.components.runtime.actors.extensions;

import groovy.lang.GroovyObjectSupport;

/**
 * Grid specification bean for k.Actors. Filled in as the need arises.
 * 
 * @author Ferd
 *
 */
public class Grid extends GroovyObjectSupport {

    private int linearCells;
    private int xCells;
    private int yCells;

    public Grid(Integer linearCells) {
        this.linearCells = linearCells;
    }

    public Grid(Integer xCells, Integer yCells) {
        this.xCells = xCells;
        this.yCells = yCells;
    }

    public int getLinearCells() {
        return linearCells;
    }

    public int getXCells() {
        return xCells;
    }

    public int getYCells() {
        return yCells;
    }

}
