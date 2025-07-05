package org.indoorgml.model;

/**
 * Transition connecting exactly two states.
 */
public class Transition {
    private String id;
    private StatePoint stateA;
    private StatePoint stateB;
    private LineString geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatePoint getStateA() {
        return stateA;
    }

    public void setStateA(StatePoint stateA) {
        this.stateA = stateA;
    }

    public StatePoint getStateB() {
        return stateB;
    }

    public void setStateB(StatePoint stateB) {
        this.stateB = stateB;
    }

    public LineString getGeometry() {
        return geometry;
    }

    public void setGeometry(LineString geometry) {
        this.geometry = geometry;
    }
}
