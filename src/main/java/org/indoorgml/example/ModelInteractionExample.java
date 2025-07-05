package org.indoorgml.example;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import org.indoorgml.model.CellSpace;
import org.indoorgml.model.StatePoint;
import org.indoorgml.model.Transition;
import org.indoorgml.model.IndoorGMLModel;
import org.indoorgml.example.CreateModel;
import org.indoorgml.visualizer.CellSpaceGeometryBuilder;
import org.indoorgml.visualizer.StateGeometryBuilder;
import org.indoorgml.visualizer.TransitionGeometryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Example that uses IndoorGMLModel to create CellSpaces with
 * automatically generated States and Transitions. Clicking on the
 * geometries prints their IDs to the console.
 */
public class ModelInteractionExample extends SimpleApplication {

    private IndoorGMLModel model;
    private ChaseCamera chaseCam;
    private Node scene;
    private final java.util.List<Geometry> selected = new java.util.ArrayList<>();

    public static void main(String[] args) {
        ModelInteractionExample app = new ModelInteractionExample();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        model = CreateModel.createModel();
        scene = createScene();
        rootNode.attachChild(scene);

        inputManager.addMapping("select", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "select");
        inputManager.addMapping("delete", new KeyTrigger(KeyInput.KEY_DELETE));
        inputManager.addListener(actionListener, "delete");

        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, scene, inputManager);
        chaseCam.setDefaultDistance(6f);
        chaseCam.setDragToRotate(true);
    }

    private Node createScene() {
        Node node = new Node("scene");
        ColorRGBA[] colors = new ColorRGBA[] {
                ColorRGBA.Blue,
                ColorRGBA.Orange,
                ColorRGBA.Cyan,
                ColorRGBA.Magenta,
                ColorRGBA.Brown
        };
        int idx = 0;
        for (CellSpace cs : model.getCellSpaces()) {
            ColorRGBA color = colors[idx % colors.length];
            node.attachChild(CellSpaceGeometryBuilder.buildCellSpacesFromCells(
                    List.of(cs), assetManager, color, 0.5f));
            idx++;
        }
        node.attachChild(StateGeometryBuilder.buildStates(new ArrayList<>(model.getStates()), assetManager));
        node.attachChild(TransitionGeometryBuilder.buildTransitionsFromTransitions(
                new ArrayList<>(model.getTransitions()), assetManager));
        return node;
    }

    private void selectAtCursor() {
        clearSelection();
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f origin = cam.getWorldCoordinates(click2d, 0f);
        Vector3f direction = cam.getWorldCoordinates(click2d, 1f).subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            Geometry g = results.getClosestCollision().getGeometry();
            String cellId = g.getUserData("cellId");
            String polygonId = g.getUserData("polygonId");
            String stateId = g.getUserData("stateId");
            String transitionId = g.getUserData("transitionId");
            if (cellId != null) {
                highlightWithId("cellId", cellId);
                System.out.println("Clicked CellSpace " + cellId + " (" + polygonId + ")");
            } else if (stateId != null) {
                highlightWithId("stateId", stateId);
                System.out.println("Clicked State " + stateId);
            } else if (transitionId != null) {
                highlightWithId("transitionId", transitionId);
                System.out.println("Clicked Transition " + transitionId);
            }
        }
    }

    private void deleteSelection() {
        if (selected.isEmpty()) {
            return;
        }
        Geometry g = selected.get(0);
        String cellId = g.getUserData("cellId");
        String stateId = g.getUserData("stateId");
        String transitionId = g.getUserData("transitionId");
        if (cellId != null) {
            model.removeCellSpace(cellId);
        } else if (stateId != null) {
            model.removeState(stateId);
        } else if (transitionId != null) {
            model.removeTransition(transitionId);
        }
        rebuildScene();
    }

    private void highlightWithId(String key, String id) {
        java.util.ArrayDeque<com.jme3.scene.Spatial> stack = new java.util.ArrayDeque<>();
        stack.add(scene);
        while (!stack.isEmpty()) {
            com.jme3.scene.Spatial s = stack.poll();
            if (s instanceof Geometry geom) {
                String val = geom.getUserData(key);
                if (id.equals(val)) {
                    ColorRGBA base = geom.getUserData("baseColor");
                    ColorRGBA highlight = ColorRGBA.Yellow.clone();
                    if (base != null) {
                        highlight.a = base.a;
                    }
                    geom.getMaterial().setColor("Color", highlight);
                    selected.add(geom);
                }
            }
            if (s instanceof Node node) {
                stack.addAll(node.getChildren());
            }
        }
    }

    private void clearSelection() {
        for (Geometry geom : selected) {
            ColorRGBA base = geom.getUserData("baseColor");
            if (base != null) {
                geom.getMaterial().setColor("Color", base);
            }
        }
        selected.clear();
    }

    private void rebuildScene() {
        rootNode.detachChild(scene);
        scene = createScene();
        rootNode.attachChild(scene);
        chaseCam.setSpatial(scene);
        clearSelection();
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                return;
            }
            if ("select".equals(name)) {
                selectAtCursor();
            } else if ("delete".equals(name)) {
                deleteSelection();
            }
        }
    };
}

