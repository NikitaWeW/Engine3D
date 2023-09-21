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
    public static Vector3f[] findAABB(ArrayList<Vector3f> vertices) {
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
    
    public static boolean ccw(Vector3f A, Vector3f B, Vector3f C) {
        double crossProductX = (B.y - A.y) * (C.z - A.z) - (B.z - A.z) * (C.y - A.y);
        double crossProductY = (B.z - A.z) * (C.x - A.x) - (B.x - A.x) * (C.z - A.z);
        double crossProductZ = (B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x);

        return crossProductX > 0 || (crossProductX == 0 && (crossProductY > 0 || (crossProductY == 0 && crossProductZ > 0)));
    }
    public static boolean intersect(Vector3f A, Vector3f B, Vector3f C, Vector3f D) {
        return ccw(A, C, D) != ccw(B, C, D) && ccw(A, B, C) != ccw(A, B, D);
    }

    public boolean checkColision(Component component) {
        if(!checkAABBColision(findAABB(transformVertices())[0], 
            findAABB(transformVertices())[1], 
            findAABB(component.transformVertices())[0], 
            findAABB(component.transformVertices())[1])) return false;

            ArrayList<Vector3f> edges1 = transformEdges();
            ArrayList<Vector3f> edges2 = component.transformEdges();
        
            for (int i = 0; i < edges1.size(); i += 2) {
                Vector3f edge1Start = edges1.get(i);
                Vector3f edge1End = edges1.get(i + 1);
        
                for (int j = 0; j < edges2.size(); j += 2) {
                    Vector3f edge2Start = edges2.get(j);
                    Vector3f edge2End = edges2.get(j + 1);
        
                    if (intersect(edge1Start, edge1End, edge2Start, edge2End)) {
                        return true;
                    }
                }
            }

        return false;
    }

    public abstract void render();
    public abstract ArrayList<Vector3f> getEdges();

    public ArrayList<Vector3f> getVertices() {
        return new ArrayList<>() {{
            for(Vector3f point : getEdges()) {
                add(point);
            }
        }};
    }
    public ArrayList<Vector3f> transformVertices() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(pos)
            .rotateX((float) Math.toRadians(rotate.x))
            .rotateY((float) Math.toRadians(rotate.y))
            .rotateZ((float) Math.toRadians(rotate.z))
            .scale(scale);

        for (Vector3f vertex : getVertices()) {
            Vector3f transformedVertex = new Vector3f(vertex);
            transformedVertex.mulPosition(transformationMatrix);
            vertices.add(transformedVertex);
        }

        return vertices;
    }
    public ArrayList<Vector3f> transformEdges() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(pos)
            .rotateX((float) Math.toRadians(rotate.x))
            .rotateY((float) Math.toRadians(rotate.y))
            .rotateZ((float) Math.toRadians(rotate.z))
            .scale(scale);

        for (Vector3f vertex : getEdges()) {
            Vector3f transformedVertex = new Vector3f(vertex);
            transformedVertex.mulPosition(transformationMatrix);
            vertices.add(transformedVertex);
        }

        return vertices;
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
