package org.indoorgml.example;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import org.indoorgml.model.LineString;
import org.indoorgml.model.Polygon;
import org.indoorgml.model.StatePoint;
import org.indoorgml.model.Vector3d;
import org.indoorgml.visualizer.IndoorGMLVisualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Example application that positions several cell spaces at different
 * heights and connects their states with transitions.
 */
public class ElevatedCellSpacesExample extends SimpleApplication {

    public static void main(String[] args) {
        ElevatedCellSpacesExample app = new ElevatedCellSpacesExample();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        List<Polygon> cellSpaces = createCellSpaces();
        List<StatePoint> states = createStates(cellSpaces);
        List<LineString> transitions = createTransitions(states);

        IndoorGMLVisualizer visualizer = new IndoorGMLVisualizer(assetManager);
        Node scene = visualizer.buildScene(cellSpaces, transitions, states);
        rootNode.attachChild(scene);

        flyCam.setMoveSpeed(5f);
    }

    private List<Polygon> createCellSpaces() {
        List<Polygon> list = new ArrayList<>();
        list.add(square(0, 0, 0));
        list.add(square(3, 0, 1));
        list.add(square(0, 3, 2));
        return list;
    }

    private Polygon square(float x, float y, float z) {
        Polygon poly = new Polygon();
        poly.setVertices(Arrays.asList(
                new Vector3d(x, y, z),
                new Vector3d(x + 2, y, z),
                new Vector3d(x + 2, y + 2, z),
                new Vector3d(x, y + 2, z)
        ));
        poly.setIndices(Arrays.asList(0, 1, 2, 0, 2, 3));
        return poly;
    }

    private List<StatePoint> createStates(List<Polygon> cells) {
        List<StatePoint> states = new ArrayList<>();
        for (Polygon p : cells) {
            states.add(computeCentroid(p));
        }
        return states;
    }

    private StatePoint computeCentroid(Polygon poly) {
        double x = 0;
        double y = 0;
        double z = 0;
        List<Vector3d> vertices = poly.getVertices();
        for (Vector3d v : vertices) {
            x += v.getX();
            y += v.getY();
            z += v.getZ();
        }
        int n = vertices.size();
        StatePoint state = new StatePoint();
        state.setPosition(new Vector3d(x / n, y / n, z / n));
        return state;
    }

    private List<LineString> createTransitions(List<StatePoint> states) {
        List<LineString> lines = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            Vector3d a = states.get(i).getPosition();
            Vector3d b = states.get((i + 1) % states.size()).getPosition();
            LineString line = new LineString();
            line.setVertices(Arrays.asList(
                    new Vector3d(a.getX(), a.getY(), a.getZ()),
                    new Vector3d(b.getX(), b.getY(), b.getZ())
            ));
            lines.add(line);
        }
        return lines;
    }
}
