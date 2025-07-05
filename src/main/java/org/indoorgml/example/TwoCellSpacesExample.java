package org.indoorgml.example;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
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
 * Example that visualizes two 3D CellSpaces constructed from multiple
 * polygons and connects their centroids with a transition.
 */
public class TwoCellSpacesExample extends SimpleApplication {

    private ChaseCamera chaseCam;

    public static void main(String[] args) {
        TwoCellSpacesExample app = new TwoCellSpacesExample();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        List<Polygon> cellSpace1 = createCellSpace1();
        List<Polygon> cellSpace2 = createCellSpace2();

        // Combine polygons for visualization
        List<Polygon> allPolygons = new ArrayList<>();
        allPolygons.addAll(cellSpace1);
        allPolygons.addAll(cellSpace2);

        List<StatePoint> states = Arrays.asList(
                computeCentroid(cellSpace1),
                computeCentroid(cellSpace2)
        );

        List<LineString> transitions = createTransition(states.get(0), states.get(1));

        // Scale large coordinates down and translate them so the geometry is
        // centered around the origin before creating the scene graph.
        float scale = (float) (10.0 / computeMaxDimension(allPolygons));
        applyScale(allPolygons, transitions, states, scale);

        Vector3d center = computeBoundingCenter(allPolygons);
        applyTranslation(allPolygons, transitions, states,
                -center.getX(), -center.getY(), -center.getZ());

        IndoorGMLVisualizer visualizer = new IndoorGMLVisualizer(assetManager);
        Node scene = visualizer.buildScene(allPolygons, transitions, states);
        rootNode.attachChild(scene);

        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, scene, inputManager);
        chaseCam.setDefaultDistance(6f);
        chaseCam.setDragToRotate(true);
        chaseCam.setMinDistance(2f);
        chaseCam.setMaxDistance(20f);
    }

    private List<LineString> createTransition(StatePoint a, StatePoint b) {
        LineString line = new LineString();
        line.setVertices(Arrays.asList(
                new Vector3d(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ()),
                new Vector3d(b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ())
        ));
        return Arrays.asList(line);
    }

    private StatePoint computeCentroid(List<Polygon> polygons) {
        double x = 0;
        double y = 0;
        double z = 0;
        int count = 0;
        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                x += v.getX();
                y += v.getY();
                z += v.getZ();
                count++;
            }
        }
        StatePoint state = new StatePoint();
        state.setPosition(new Vector3d(x / count, y / count, z / count));
        return state;
    }

    private double computeMaxDimension(List<Polygon> polygons) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                minX = Math.min(minX, v.getX());
                minY = Math.min(minY, v.getY());
                minZ = Math.min(minZ, v.getZ());
                maxX = Math.max(maxX, v.getX());
                maxY = Math.max(maxY, v.getY());
                maxZ = Math.max(maxZ, v.getZ());
            }
        }

        double dx = maxX - minX;
        double dy = maxY - minY;
        double dz = maxZ - minZ;
        return Math.max(dx, Math.max(dy, dz));
    }

    private Vector3d computeBoundingCenter(List<Polygon> polygons) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                minX = Math.min(minX, v.getX());
                minY = Math.min(minY, v.getY());
                minZ = Math.min(minZ, v.getZ());
                maxX = Math.max(maxX, v.getX());
                maxY = Math.max(maxY, v.getY());
                maxZ = Math.max(maxZ, v.getZ());
            }
        }

        return new Vector3d(
                (minX + maxX) / 2.0,
                (minY + maxY) / 2.0,
                (minZ + maxZ) / 2.0);
    }

    private void applyScale(List<Polygon> polygons, List<LineString> lines,
                            List<StatePoint> states, double s) {
        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                v.setX(v.getX() * s);
                v.setY(v.getY() * s);
                v.setZ(v.getZ() * s);
            }
        }
        for (LineString line : lines) {
            for (Vector3d v : line.getVertices()) {
                v.setX(v.getX() * s);
                v.setY(v.getY() * s);
                v.setZ(v.getZ() * s);
            }
        }
        for (StatePoint state : states) {
            Vector3d pos = state.getPosition();
            pos.setX(pos.getX() * s);
            pos.setY(pos.getY() * s);
            pos.setZ(pos.getZ() * s);
        }
    }

    private void applyTranslation(List<Polygon> polygons, List<LineString> lines,
                                  List<StatePoint> states,
                                  double tx, double ty, double tz) {
        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                v.setX(v.getX() + tx);
                v.setY(v.getY() + ty);
                v.setZ(v.getZ() + tz);
            }
        }
        for (LineString line : lines) {
            for (Vector3d v : line.getVertices()) {
                v.setX(v.getX() + tx);
                v.setY(v.getY() + ty);
                v.setZ(v.getZ() + tz);
            }
        }
        for (StatePoint state : states) {
            Vector3d pos = state.getPosition();
            pos.setX(pos.getX() + tx);
            pos.setY(pos.getY() + ty);
            pos.setZ(pos.getZ() + tz);
        }
    }

    private Polygon poly(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4) {
        Polygon poly = new Polygon();
        poly.setVertices(Arrays.asList(v1, v2, v3, v4));
        poly.setIndices(Arrays.asList(0, 1, 2, 0, 2, 3));
        return poly;
    }

    private List<Polygon> createCellSpace1() {
        List<Polygon> list = new ArrayList<>();
        list.add(poly(
                new Vector3d(62762.3590635083, 49638.2654653775, 2500.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0)
        ));
        list.add(poly(
                new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 2500.0)
        ));
        list.add(poly(
                new Vector3d(62775.3752335849, 48947.8075238529, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0)
        ));
        list.add(poly(
                new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0)
        ));
        list.add(poly(
                new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 2500.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0)
        ));
        list.add(poly(
                new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 0.0)
        ));
        return list;
    }

    private List<Polygon> createCellSpace2() {
        List<Polygon> list = new ArrayList<>();
        list.add(poly(
                new Vector3d(63459.7496755729, 48913.971239468, 2500.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0)
        ));
        list.add(poly(
                new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0),
                new Vector3d(63459.7496755729, 48913.971239468, 2500.0)
        ));
        list.add(poly(
                new Vector3d(63458.4604999209, 48948.4242817383, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0)
        ));
        list.add(poly(
                new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0)
        ));
        list.add(poly(
                new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 2500.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0)
        ));
        list.add(poly(
                new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 0.0)
        ));
        return list;
    }
}
