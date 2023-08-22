package Engine;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public abstract class Component {
    private Vector3f pos = new Vector3f(0, 0, -10);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    public boolean checkColision(Component component) {
        ArrayList<Vector3f> transformedVertices = transformVertices(getVertices(), pos, rotate, scale);
        ArrayList<Vector3f> componentTransformedVertices = transformVertices(component.getVertices(), component.getPos(), component.getRotate(), component.getScale());

        Vector3f max = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        Vector3f min = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);

        for(Vector3f vertex : transformedVertices) {
            if(vertex.x > max.x) max.x = vertex.x;
            if(vertex.y > max.y) max.y = vertex.y;
            if(vertex.z > max.z) max.z = vertex.z;

            if(vertex.x < min.x) min.x = vertex.x;
            if(vertex.y < min.y) min.y = vertex.y;
            if(vertex.z < min.z) min.z = vertex.z;
        }

        Vector3f componentMax = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        Vector3f componentMin = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);

        for(Vector3f vertex : componentTransformedVertices) {
            if(vertex.x > componentMax.x) componentMax.x = vertex.x;
            if(vertex.y > componentMax.y) componentMax.y = vertex.y;
            if(vertex.z > componentMax.z) componentMax.z = vertex.z;

            if(vertex.x < componentMin.x) componentMin.x = vertex.x;
            if(vertex.y < componentMin.y) componentMin.y = vertex.y;
            if(vertex.z < componentMin.z) componentMin.z = vertex.z;
        }

        if (max.x <= componentMin.x || min.x >= componentMax.x) return false;
        if (max.y <= componentMin.y || min.y >= componentMax.y) return false;
        if (max.z <= componentMin.z || min.z >= componentMax.z) return false;
        return true;
    }

    public Vector3f getPos() {
        return pos;
    }
    public Vector3f getRotate() {
        return rotate;
    }
    public Vector3f getScale() {
        return scale;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void setRotation(Vector3f rotation) {
        this.rotate = rotation;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public abstract void draw();
    public abstract ArrayList<Vector3f> getVertices();

    private synchronized static ArrayList<Vector3f> transformVertices(ArrayList<Vector3f> vertices, Vector3f position, Vector3f rotation, Vector3f scale) {
        ArrayList<Vector3f> transformedVertices = new ArrayList<>();
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(position)
            .rotateX((float) Math.toRadians(rotation.x))
            .rotateY((float) Math.toRadians(rotation.y))
            .rotateZ((float) Math.toRadians(rotation.z))
            .scale(scale);

        for (Vector3f vertex : vertices) {
            Vector3f transformedVertex = new Vector3f(vertex);
            transformedVertex.mulPosition(transformationMatrix);
            transformedVertices.add(transformedVertex);
        }

        return transformedVertices;
    }
}
