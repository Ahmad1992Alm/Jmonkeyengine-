package org.indoorgml.visualizer;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.Node;
import com.jme3.util.BufferUtils;

import org.indoorgml.model.LineString;
import org.indoorgml.model.Vector3d;

import java.util.List;

/**
 * Builds line geometries for transitions.
 */
public class TransitionGeometryBuilder {

    private TransitionGeometryBuilder() {
        // utility class
    }

    public static Node buildTransitions(List<LineString> lines, AssetManager assetManager) {
        Node node = new Node("transitions");
        Material material = defaultMaterial(assetManager);
        for (LineString line : lines) {
            node.attachChild(buildGeometry(line, material));
        }
        return node;
    }

    private static Geometry buildGeometry(LineString line, Material material) {
        Mesh mesh = new Mesh();

        float[] vertices = new float[line.getVertices().size() * 3];
        int i = 0;
        for (Vector3d v : line.getVertices()) {
            vertices[i++] = (float) v.getX();
            vertices[i++] = (float) v.getY();
            vertices[i++] = (float) v.getZ();
        }

        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setMode(Mesh.Mode.LineStrip);
        mesh.updateBound();

        Geometry geom = new Geometry("transition", mesh);
        geom.setMaterial(material);
        return geom;
    }

    private static Material defaultMaterial(AssetManager assetManager) {
        Material mat = new Material(assetManager, Materials.UNSHADED);
        mat.setColor("Color", ColorRGBA.Green);
        return mat;
    }
}
