package org.indoorgml.visualizer;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import org.indoorgml.model.StatePoint;
import org.indoorgml.model.Vector3d;

import java.util.List;

/**
 * Builds small geometries representing states.
 */
public class StateGeometryBuilder {

    private static final float RADIUS = 0.2f;

    private StateGeometryBuilder() {
        // utility class
    }

    public static Node buildStates(List<StatePoint> states, AssetManager assetManager) {
        Node node = new Node("states");
        Material material = defaultMaterial(assetManager);
        for (StatePoint state : states) {
            node.attachChild(buildGeometry(state, material));
        }
        return node;
    }

    private static Geometry buildGeometry(StatePoint state, Material material) {
        Sphere sphere = new Sphere(10, 10, RADIUS);
        Geometry geom = new Geometry("state", sphere);
        geom.setMaterial(material.clone());
        ColorRGBA base = material.getParam("Color").getValue();
        geom.setUserData("baseColor", base.clone());
        Vector3d pos = state.getPosition();
        geom.setLocalTranslation(new Vector3f((float) pos.getX(), (float) pos.getY(), (float) pos.getZ()));
        geom.setUserData("stateId", state.getId());
        return geom;
    }

    private static Material defaultMaterial(AssetManager assetManager) {
        Material mat = new Material(assetManager, Materials.UNSHADED);
        mat.setColor("Color", ColorRGBA.Red);
        return mat;
    }
}
