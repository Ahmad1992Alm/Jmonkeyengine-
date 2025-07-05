package org.indoorgml.model;

import java.util.*;

/**
 * Manages CellSpaces, States and Transitions with unique IDs.
 */
public class IndoorGMLModel {
    private final Map<String, CellSpace> cellSpaces = new LinkedHashMap<>();
    private final Map<String, Transition> transitions = new LinkedHashMap<>();
    private int cellCounter = 1;
    private int stateCounter = 1;
    private int transitionCounter = 1;
    private int polygonCounter = 1;

    /**
     * Adds a new CellSpace and automatically creates a new State.
     */
    public CellSpace addCellSpace(List<Polygon> polygons) {
        String cellId = "Room" + cellCounter++;
        CellSpace cell = new CellSpace();
        cell.setId(cellId);
        for (Polygon p : polygons) {
            p.setId("P" + polygonCounter++);
        }
        cell.setPolygons(polygons);

        StatePoint state = new StatePoint();
        state.setId("S" + stateCounter++);
        state.setPosition(computeCentroid(polygons));
        cell.setState(state);
        cellSpaces.put(cellId, cell);
        return cell;
    }

    /**
     * Removes a CellSpace and all related objects.
     */
    public void removeCellSpace(String cellSpaceId) {
        CellSpace cell = cellSpaces.remove(cellSpaceId);
        if (cell == null) {
            return;
        }
        String stateId = cell.getState().getId();
        transitions.values().removeIf(t ->
                t.getStateA().getId().equals(stateId) ||
                        t.getStateB().getId().equals(stateId));
    }

    public Transition addTransition(StatePoint a, StatePoint b) {
        String id = "T" + transitionCounter++;
        Transition t = new Transition();
        t.setId(id);
        t.setStateA(a);
        t.setStateB(b);
        LineString line = new LineString();
        line.setVertices(Arrays.asList(
                new Vector3d(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ()),
                new Vector3d(b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ())
        ));
        t.setGeometry(line);
        transitions.put(id, t);
        return t;
    }

    public Collection<CellSpace> getCellSpaces() {
        return cellSpaces.values();
    }

    public Collection<Transition> getTransitions() {
        return transitions.values();
    }

    private Vector3d computeCentroid(List<Polygon> polygons) {
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
        return new Vector3d(x / count, y / count, z / count);
    }
}
