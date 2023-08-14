package Engine;

import org.joml.Vector3f;

import java.util.ArrayList;

public abstract class Component implements Drawable {
    private Position pos = new Position();
    private ArrayList<Vector3f> vertices = new ArrayList<>();
    private float minX, minY, minZ;
    private float maxX, maxY, maxZ;
    private Vector3f force = new Vector3f();
    private boolean anchored = true;
    private boolean colision = true;
    private Component component;

    public Position pos() {
        return pos;
    }
    public void pos(Position pos) {
        this.pos = pos;
    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }
    public void addVertex(Vector3f vertex) {
        vertices.add(vertex);
    }

    public Vector3f getForce() {
        return force;
    }
    public void setForce(Vector3f force) {
        this.force = force;
    }

    public boolean isAnchored() {
        return anchored;
    }
    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    public boolean isColision() {
        return colision;
    }
    public void setColision(boolean colision) {
        this.colision = colision;
    }

    public boolean checkColision(Component component) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        float maxZ = Float.MIN_VALUE;

        for (Vector3f vertex : vertices) {
            if (vertex.x < minX) minX = vertex.x;
            if (vertex.y < minY) minY = vertex.y;
            if (vertex.z < minZ) minZ = vertex.z;
            if (vertex.x > maxX) maxX = vertex.x;
            if (vertex.y > maxY) maxY = vertex.y;
            if (vertex.z > maxZ) maxZ = vertex.z;
        }

        if (getMaxX() < component.getMinX() || getMinX() > component.getMaxX()) return false;
        if (getMaxY() < component.getMinY() || getMinY() > component.getMaxY()) return false;
        if (getMaxZ() < component.getMinZ() || getMinZ() > component.getMaxZ()) return false;
        return true;
    }

    public float getMinX() { return minX; }
    public float getMinY() { return minY; }
    public float getMinZ() { return minZ; }
    public float getMaxX() { return maxX; }
    public float getMaxY() { return maxY; }
    public float getMaxZ() { return maxZ; }

    public Vector3f getMin() {
        return new Vector3f(minX, minY, minZ);
    }
    public Vector3f getMax() {
        return new Vector3f(maxX, maxY, maxZ);
    }
}
