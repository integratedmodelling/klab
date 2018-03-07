package org.integratedmodelling.klab.api.observations.scale.space;

public enum Direction {
    LEFT(1),
    RIGHT(2),
    TOP(3),
    BOTTOM(4),
    UP(5),
    DOWN(6);
    
    public int code;
    Direction(int n) {
        this.code = n;
    }
}
