package org.indoorgml.model;

import java.util.List;

/**
 * Simple line string represented by an ordered list of vertices.
 */
public class LineString {
    private List<Vector3d> vertices;

    public List<Vector3d> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3d> vertices) {
        this.vertices = vertices;
    }
}
