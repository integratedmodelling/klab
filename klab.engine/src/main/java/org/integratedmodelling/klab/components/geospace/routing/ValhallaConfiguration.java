package org.integratedmodelling.klab.components.geospace.routing;

import java.util.Arrays;

import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;

public class ValhallaConfiguration {
    public static enum TransportType {
        Auto("auto"),
        Pedestrian("pedestrian"),
        Bicycle("bicycle"),
        Bus("bus"),
        Truck("truck"),
        Taxi("taxi"),
        MotorScooter("motor_scooter"),
        Multimodal("multimodel"),
        ;

        private String type;

        TransportType(String type) {
            this.type = type;
        }

        final public String getType() {
            return this.type;
        }

        public final static TransportType fromValue(String value) {
            return Arrays.stream(TransportType.values()).filter(val -> val.getType().equals(value)).findAny()
                    .orElseThrow(() -> new KlabIllegalArgumentException("Value " +  value + " unknown for TransportType"));
        }
    }

    public static enum GeometryCollapser {
        Centroid("centroid"),
        ;

        private String type;

        GeometryCollapser(String type) {
            this.type = type;
        }

        final public String getType() {
            return this.type;
        }

        public final static GeometryCollapser fromValue(String value) {
            return Arrays.stream(GeometryCollapser.values()).filter(val -> val.getType().equals(value)).findAny()
                    .orElseThrow(() -> new KlabIllegalArgumentException("Value " +  value + " unknown for GeometryCollapser"));
        }
    }

    public static enum IsochroneType {
        Time("time"),
        Distance("distance"),
        ;

        private String type;

        IsochroneType(String type) {
            this.type = type;
        }

        final public String getType() {
            return this.type;
        }

        public final static IsochroneType fromValue(String value) {
            return Arrays.stream(IsochroneType.values()).filter(val -> val.getType().equals(value)).findAny()
                    .orElseThrow(() -> new KlabIllegalArgumentException("Value " +  value + " unknown for IsochroneType"));
        }
    }

}
