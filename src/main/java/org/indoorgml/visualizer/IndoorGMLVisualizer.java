package org.indoorgml.visualizer;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

import org.indoorgml.model.LineString;
import org.indoorgml.model.Polygon;
import org.indoorgml.model.StatePoint;

import java.util.List;

/**
 * High level helper that builds a scene graph from IndoorGML structures.
 */
public class IndoorGMLVisualizer {

    private final AssetManager assetManager;

    public IndoorGMLVisualizer(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * Creates a node containing cell spaces, transitions and state geometries.
     */
    public Node buildScene(List<Polygon> cellSpaces, List<LineString> transitions, List<StatePoint> states) {
        Node root = new Node("IndoorGML");
        if (cellSpaces != null) {
            root.attachChild(CellSpaceGeometryBuilder.buildCellSpaces(cellSpaces, assetManager));
        }
        if (transitions != null) {
            root.attachChild(TransitionGeometryBuilder.buildTransitions(transitions, assetManager));
        }
        if (states != null) {
            root.attachChild(StateGeometryBuilder.buildStates(states, assetManager));
        }
        return root;
    }
}
