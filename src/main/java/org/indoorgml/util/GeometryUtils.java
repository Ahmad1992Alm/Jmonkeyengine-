package org.indoorgml.util;

import org.indoorgml.model.Polygon;
import org.indoorgml.model.Vector3d;

import java.util.List;

/**
 * Static helper methods for simple geometry transformations.
 */
public final class GeometryUtils {

    private GeometryUtils() {
    }

    /**
     * Computes the center of the bounding box of all polygons.
     */
    public static Vector3d computeBoundingCenter(List<Polygon> polygons) {
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

    /**
     * Returns the largest dimension of the bounding box covering all polygons.
     */
    public static double computeMaxDimension(List<Polygon> polygons) {
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

    /**
     * Scales all polygon vertices by the given factor.
     */
    public static void applyScale(List<Polygon> polygons, double s) {
        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                v.setX(v.getX() * s);
                v.setY(v.getY() * s);
                v.setZ(v.getZ() * s);
            }
        }
    }

    /**
     * Translates all polygon vertices by the given offset vector.
     */
    public static void applyTranslation(List<Polygon> polygons, Vector3d offset) {
        for (Polygon poly : polygons) {
            for (Vector3d v : poly.getVertices()) {
                v.setX(v.getX() + offset.getX());
                v.setY(v.getY() + offset.getY());
                v.setZ(v.getZ() + offset.getZ());
            }
        }
    }
}
