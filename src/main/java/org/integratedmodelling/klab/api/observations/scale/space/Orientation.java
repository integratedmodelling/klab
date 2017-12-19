package org.integratedmodelling.klab.api.observations.scale.space;

/**
 * Geographic orientations for a Moore neighborhood 2D model, constants 
 * chosen to represent the actual geographic heading.
 * 
 * @author ferdinando.villa
 *
 */
public enum Orientation {

    N(0),
    NE(45),
    E(90),
    SE(135),
    S(180),
    SW(225),
    W(270),
    NW(315);

    int heading;
    
    Orientation(int h) { heading = h; }
    
}
