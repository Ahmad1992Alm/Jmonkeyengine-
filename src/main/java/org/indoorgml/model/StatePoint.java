package org.indoorgml.model;

/**
 * Simple representation of a state using a position.
 */
public class StatePoint {
    private String id;
    private Vector3d position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }
}
