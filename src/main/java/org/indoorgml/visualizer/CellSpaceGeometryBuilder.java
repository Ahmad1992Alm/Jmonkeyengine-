package org.indoorgml.visualizer;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.render.RenderQueue;
import com.jme3.render.RenderState;
import com.jme3.util.BufferUtils;

import org.indoorgml.model.CellSpace;
import org.indoorgml.model.Polygon;
import org.indoorgml.model.Vector3d;

import java.util.List;

/**
 * Builds JMonkeyEngine geometries for CellSpace polygons.
 */
public class CellSpaceGeometryBuilder {

    private CellSpaceGeometryBuilder() {
        // utility class
    }

    /**
     * Creates a node containing geometries for all cell spaces using a default color.
     */
    public static Node buildCellSpaces(List<Polygon> polygons, AssetManager assetManager) {
        return buildCellSpaces(polygons, assetManager, ColorRGBA.LightGray, 1f);
    }

    /**
     * Creates a node containing geometries for all cell spaces using the given color
     * and alpha value.
     */
    public static Node buildCellSpaces(List<Polygon> polygons, AssetManager assetManager,
                                       ColorRGBA color, float alpha) {
        Node node = new Node("cellSpaces");
        Material material = material(assetManager, color, alpha);
        boolean transparent = alpha < 1f;
        for (Polygon poly : polygons) {
            node.attachChild(buildGeometry(poly, material, transparent));
        }
        return node;
    }

    /**
     * Convenience method to build geometries directly from CellSpace objects.
     */
    public static Node buildCellSpacesFromCells(List<CellSpace> cells, AssetManager assetManager,
                                               ColorRGBA color, float alpha) {
        Node node = new Node("cellSpaces");
        Material material = material(assetManager, color, alpha);
        boolean transparent = alpha < 1f;
        for (CellSpace cs : cells) {
            for (Polygon poly : cs.getPolygons()) {
                node.attachChild(buildGeometry(poly, material, transparent));
            }
        }
        return node;
    }

    public static Node buildCellSpacesFromCells(List<CellSpace> cells, AssetManager assetManager) {
        return buildCellSpacesFromCells(cells, assetManager, ColorRGBA.LightGray, 1f);
    }

    private static Geometry buildGeometry(Polygon polygon, Material material, boolean transparent) {
        Mesh mesh = new Mesh();

        float[] vertices = new float[polygon.getVertices().size() * 3];
        int i = 0;
        for (Vector3d v : polygon.getVertices()) {
            vertices[i++] = (float) v.getX();
            vertices[i++] = (float) v.getY();
            vertices[i++] = (float) v.getZ();
        }

        int[] indices = polygon.getIndices().stream().mapToInt(Integer::intValue).toArray();

        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices));
        mesh.setMode(Mesh.Mode.Triangles);
        mesh.updateBound();

        Geometry geom = new Geometry("cellSpace", mesh);
        geom.setMaterial(material);
        if (transparent) {
            geom.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        return geom;
    }

    private static Material material(AssetManager assetManager, ColorRGBA color, float alpha) {
        Material mat = new Material(assetManager, Materials.UNSHADED);
        ColorRGBA c = color.clone();
        c.a = alpha;
        mat.setColor("Color", c);
        if (alpha < 1f) {
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        }
        return mat;
    }
}
