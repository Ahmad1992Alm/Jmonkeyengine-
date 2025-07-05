package org.indoorgml.model;

import java.util.List;

/**
 * Representation of a CellSpace consisting of one or more polygons and a corresponding state.
 */
public class CellSpace {
    private String id;
    private List<Polygon> polygons;
    private StatePoint state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public StatePoint getState() {
        return state;
    }

    public void setState(StatePoint state) {
        this.state = state;
    }
}
