package org.integratedmodelling.klab.rest;

public class ObservationAssetStatistics {

    public enum Type {
        ResolvedObservable, Model, Resource, Export
    }

    private Type type;

    /**
     * Model URN, resource URN, or observable (either computed or exported).
     */
    private String name;

    /**
     * Time is included in the total time for the parent asset or observation. Do not sum these up
     * among assets of different type.
     */
    private double computationTime;

    /**
     * number of "passes" for this asset use within the computation it belongs to.
     */
    private int computations;

    /**
     * Number of scheduled executions if an actuator (type==Model), zero otherwise. Even in
     * actuators zero is possible as the initialization step is not counted.
     */
    private int schedules;

    /**
     * Only filled in when relevant, currently only the bytes downloaded for exports.
     */
    private long size;

}
