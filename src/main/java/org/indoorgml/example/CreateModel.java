package org.indoorgml.example;

import org.indoorgml.model.CellSpace;
import org.indoorgml.model.IndoorGMLModel;
import org.indoorgml.model.Polygon;
import org.indoorgml.model.StatePoint;
import org.indoorgml.model.Vector3d;
import org.indoorgml.util.GeometryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility that builds a small example {@link IndoorGMLModel} containing
 * a few cell spaces, their states and connecting transitions. The polygon
 * data uses large coordinates which are scaled down and centered around
 * the origin so the resulting scene fits nicely into the view.
 */
public final class CreateModel {

    private CreateModel() {
    }

    /**
     * Creates a sample model with multiple cell spaces.
     */
    public static IndoorGMLModel createModel() {
        IndoorGMLModel model = new IndoorGMLModel();

        List<CellSpace> cells = Arrays.asList(
                createCellSpace1(),
                createCellSpace2(),
                createCellSpace3()
        );

        // collect polygons for transformation
        List<Polygon> allPolygons = new ArrayList<>();
        for (CellSpace cs : cells) {
            allPolygons.addAll(cs.getPolygons());
        }

        // scale coordinates to a manageable size and move them around the origin
        float scale = (float) (10.0 / GeometryUtils.computeMaxDimension(allPolygons));
        GeometryUtils.applyScale(allPolygons, scale);
        Vector3d center = GeometryUtils.computeBoundingCenter(allPolygons);
        GeometryUtils.applyTranslation(allPolygons,
                new Vector3d(-center.getX(), -center.getY(), -center.getZ()));

        // add cells to the model (states are created automatically)
        for (CellSpace cs : cells) {
            model.addCellSpace(cs.getPolygons());
        }

        // connect all states with transitions in a simple ring
        List<StatePoint> states = new ArrayList<>(model.getStates());
        for (int i = 0; i < states.size(); i++) {
            StatePoint a = states.get(i);
            StatePoint b = states.get((i + 1) % states.size());
            model.addTransition(a, b);
        }

        return model;
    }

    private static CellSpace createCellSpace1() {
        CellSpace c = new CellSpace();
        List<Polygon> list = new ArrayList<>();
        list.add(poly(new Vector3d(62762.3590635083, 49638.2654653775, 2500.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0)));
        list.add(poly(new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 2500.0)));
        list.add(poly(new Vector3d(62775.3752335849, 48947.8075238529, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 2500.0)));
        list.add(poly(new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 2500.0)));
        list.add(poly(new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(62762.3590635083, 49638.2654653775, 2500.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 2500.0)));
        list.add(poly(new Vector3d(62762.3590635083, 49638.2654653775, 0.0),
                new Vector3d(63515.4374949107, 49639.4237948367, 0.0),
                new Vector3d(63522.3102633086, 48948.4819317082, 0.0),
                new Vector3d(62775.3752335849, 48947.8075238529, 0.0)));
        c.setPolygons(list);
        return c;
    }

    private static CellSpace createCellSpace2() {
        CellSpace c = new CellSpace();
        List<Polygon> list = new ArrayList<>();
        list.add(poly(new Vector3d(63459.7496755729, 48913.971239468, 2500.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0)));
        list.add(poly(new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0),
                new Vector3d(63459.7496755729, 48913.971239468, 2500.0)));
        list.add(poly(new Vector3d(63458.4604999209, 48948.4242817383, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 2500.0)));
        list.add(poly(new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0),
                new Vector3d(62813.8517414851, 48947.842264303, 2500.0)));
        list.add(poly(new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 2500.0),
                new Vector3d(62815.1711547398, 48914.513130238, 2500.0)));
        list.add(poly(new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(62813.8517414851, 48947.842264303, 0.0),
                new Vector3d(63458.4604999209, 48948.4242817383, 0.0)));
        c.setPolygons(list);
        return c;
    }

    private static CellSpace createCellSpace3() {
        CellSpace c = new CellSpace();
        List<Polygon> list = new ArrayList<>();
        list.add(poly(new Vector3d(62815.1711547398, 48914.513130238, 0.0),
                new Vector3d(63065.2696380588, 48914.3028749233, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 0.0),
                new Vector3d(63459.7496755729, 48913.971239468, 15.0),
                new Vector3d(63065.2696380588, 48914.3028749233, 15.0),
                new Vector3d(62815.1711547398, 48914.513130238, 15.0)));
        c.setPolygons(list);
        return c;
    }

    private static Polygon poly(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4) {
        Polygon poly = new Polygon();
        poly.setVertices(Arrays.asList(v1, v2, v3, v4));
        poly.setIndices(Arrays.asList(0, 1, 2, 0, 2, 3));
        return poly;
    }

    private static Polygon poly(Vector3d v1, Vector3d v2, Vector3d v3,
                                Vector3d v4, Vector3d v5, Vector3d v6) {
        Polygon poly = new Polygon();
        poly.setVertices(Arrays.asList(v1, v2, v3, v4, v5, v6));
        poly.setIndices(Arrays.asList(0, 1, 5, 5, 4, 1, 1, 4, 2, 2, 4, 3));
        return poly;
    }
}

