package org.indoorgml.model;

import java.util.List;

/**
 * Polygon defined by vertices and triangle indices.
 */
public class Polygon {
    private String id;
    private List<Vector3d> vertices;
    private List<Integer> indices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Vector3d> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3d> vertices) {
        this.vertices = vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }
}
