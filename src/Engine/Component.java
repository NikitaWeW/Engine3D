package Engine;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public abstract class Component {
    private Vector3f pos = new Vector3f(0, 0, 0);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    public static boolean checkAABBColision(Vector3f min, Vector3f max, Vector3f componentMin, Vector3f componentMax) {
        if (min.x <= componentMax.x &&
            max.x >= componentMin.x &&
            min.y <= componentMax.y &&
            max.y >= componentMin.y &&
            min.z <= componentMax.z &&
            max.z >= componentMin.z) 
                return true;
        return false;
    }
    public static Vector3f[] findAABB(Vector3f[] vertices) {
        Vector3f max = new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Vector3f min = new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

        for(Vector3f vertex : vertices) {
            if(vertex.x > max.x) max.x = vertex.x;
            if(vertex.y > max.y) max.y = vertex.y;
            if(vertex.z > max.z) max.z = vertex.z;

            if(vertex.x < min.x) min.x = vertex.x;
            if(vertex.y < min.y) min.y = vertex.y;
            if(vertex.z < min.z) min.z = vertex.z;
        }

        return new Vector3f[] {min, max};
    }
    
    public static Vector3f triTriIntersection(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q, Vector3f R) {

        //Axis Aligned Bounding Box
        if(checkAABBColision(findAABB(new Vector3f[] {A, B, C})[0], findAABB(new Vector3f[] {A, B, C})[1], 
            findAABB(new Vector3f[] {P, Q, R})[0], findAABB(new Vector3f[] {P, Q, R})[1]))
            return null; //no intersection
        
        Vector3f intersectionpoint1 = new Vector3f();
        Vector3f intersectionpoint2 = new Vector3f();
        //https://miro.com/app/board/uXjVNSGgVSo=/
        //what is going on
        return null;
    }

    
    public boolean checkColision(Component component) {
        return false;
    } 
    
    public abstract void render();
    public abstract ArrayList<Vector3f> getTriangles();

    public ArrayList<Vector3f> getVertices() {
        return getTriangles();
    }
    public ArrayList<Vector3f> transform(ArrayList<Vector3f> vertices) {
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(pos)
            .rotateXYZ(rotate.x, rotate.y, rotate.z)
            .scale(scale);

        ArrayList<Vector3f> transformedVertices = new ArrayList<>();
        for (Vector3f vertex : vertices)
            transformedVertices.add(new Vector3f(vertex).mulPosition(transformationMatrix));

        return transformedVertices;
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
    public void setRotation(Vector3f rotate) {
        this.rotate = rotate;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
