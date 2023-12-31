package Engine;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector2f;


public abstract class Component {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    public static boolean checkAABBColision(Vector3f min, Vector3f max, Vector3f componentMin, Vector3f componentMax) {
        if (min.x <= componentMax.x &&
            min.y <= componentMax.y &&
            min.z <= componentMax.z &&
            max.x >= componentMin.x &&
            max.y >= componentMin.y &&
            max.z >= componentMin.z) 
                return true;
        return false;
    }
    public boolean checkAABBColision(Component component) {
        return checkAABBColision(findAABB(transform(getVertices()))[0], findAABB(transform(getVertices()))[1], 
            findAABB(component.transform(component.getVertices()))[0], findAABB(component.transform(component.getVertices()))[1]);
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
    public static Vector2f[] findAABB(Vector2f[] vertices) {
        Vector2f max = new Vector2f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Vector2f min = new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

        for(Vector2f vertex : vertices) {
            if(vertex.x > max.x) max.x = vertex.x;
            if(vertex.y > max.y) max.y = vertex.y;

            if(vertex.x < min.x) min.x = vertex.x;
            if(vertex.y < min.y) min.y = vertex.y;
        }

        return new Vector2f[] {min, max};
    }

    private static float round(float value) { return (float) Math.round(value*10000)/10000; }
    public static float areaOfTheTriangle(Vector3f A, Vector3f B, Vector3f C) {
        float a = A.distance(B);
        float b = B.distance(C);
        float c = C.distance(A);

        float s = (a + b + c) / 2;

        return (float) Math.sqrt((double) s*(s - a)*(s - b)*(s - c));
    }
    public static boolean triSegIntersection(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q) {
        /* line - plane intersection
        |x-A.x      y-A.y       z-A.z  |
        |B.x-A.x    B.y-A.y     B.z-A.z| = 0 
        |C.x-A.x    C.y-A.y     C.z-A.z|

        t = (x-P.x)/(Q.x-P.x) = (y-P.y)/(Q.y-P.y) = (z-P.z)/(Q.z-P.z)

        x = t(Q.y-P.x)+P.x
        y = t(Q.y-P.y)+P.y
        z = t(Q.z-P.z)+P.z

        |t(Q.y-P.x)+P.x-A.x      t(Q.y-P.y)+P.y-A.y       t(Q.z-P.z)+P.z-A.z  |
        |B.x-A.x                 B.y-A.y                  B.z-A.z             | = 0 <---- determinant
        |C.x-A.x                 C.y-A.y                  C.z-A.z             |

        a = -(Px-Ax)*(By-Ay)*(Cz-Az) -
             (Py-Ay)*(Bz-Az)*(Cx-Ax) -
             (Pz-Az)*(Bx-Ax)*(Cy-Ay) +
             (Pz-Az)*(By-Ay)*(Cx-Ax) +
             (Px-Ax)*(Bz-Az)*(Cy-Ay) +
             (Py-Ay)*(Bx-Ax)*(Cz-Az);

        b = (Qx-Px)*(By-Ay)*(Cz-Az) +
            (Qy-Py)*(Bz-Az)*(Cx-Ax) +
            (Qz-Pz)*(Bx-Ax)*(Cy-Ay) -
            (Qz-Pz)*(By-Ay)*(Cx-Ax) -
            (Qx-Px)*(Bz-Az)*(Cy-Ay) -
            (Qy-Py)*(Bx-Ax)*(Cz-Az);

        a = b = 0 => ABC and PQ are in the same plane => true
        b = 0 => A plane and a line do not intersect => false

        t = a/b
        */

        //Axis Aligned Bounding Box
        Vector3f[] ABCAABB = findAABB(new Vector3f[] {A, B, C});
        Vector3f[] PQAABB = findAABB(new Vector3f[] {P, Q});
        if(!checkAABBColision(ABCAABB[0], ABCAABB[1], PQAABB[0], PQAABB[1])) return false; //no intersection

        float numerator = -(P.x-A.x)*(B.y-A.y)*(C.z-A.z) - //solve the equation
                           (P.y-A.y)*(B.z-A.z)*(C.x-A.x) -
                           (P.z-A.z)*(B.x-A.x)*(C.y-A.y) +
                           (P.z-A.z)*(B.y-A.y)*(C.x-A.x) +
                           (P.x-A.x)*(B.z-A.z)*(C.y-A.y) +
                           (P.y-A.y)*(B.x-A.x)*(C.z-A.z);

        float denominator = (Q.x-P.x)*(B.y-A.y)*(C.z-A.z) +
                            (Q.y-P.y)*(B.z-A.z)*(C.x-A.x) +
                            (Q.z-P.z)*(B.x-A.x)*(C.y-A.y) -
                            (Q.z-P.z)*(B.y-A.y)*(C.x-A.x) -
                            (Q.x-P.x)*(B.z-A.z)*(C.y-A.y) -
                            (Q.y-P.y)*(B.x-A.x)*(C.z-A.z);

        if(numerator == 0 && denominator == 0) return true;
        if(denominator == 0) return false;

        float t = numerator / denominator;
        
        Vector3f D = new Vector3f(t * (Q.x - P.x) + P.x, t * (Q.y - P.y) + P.y, t * (Q.z - P.z) + P.z); //intersection point

        if(round(D.distance(P) + D.distance(Q)) != round(P.distance(Q))) return false; //check if D belongs to the PQ segment
         
        if(round(areaOfTheTriangle(A, B, C)) == //check if D belongs to triangle ABC
            round(areaOfTheTriangle(A, B, D) + 
                areaOfTheTriangle(D, B, C) + 
                areaOfTheTriangle(A, D, C))) return true;

        return false;
    }
    public static boolean triTriIntersection(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q, Vector3f R) {
        //Axis Aligned Bounding Box
        Vector3f[] ABCAABB = findAABB(new Vector3f[] {A, B, C});
        Vector3f[] PQRAABB = findAABB(new Vector3f[] {P, Q, R});
        if(!checkAABBColision(ABCAABB[0], ABCAABB[1], PQRAABB[0], PQRAABB[1])) return false; //no intersection

        if(triSegIntersection(A, B, C, P, Q)) return true;
        if(triSegIntersection(A, B, C, Q, R)) return true;
        if(triSegIntersection(A, B, C, R, P)) return true;

        return false;
    }

    public boolean checkColision(Component component) {
        if(!checkAABBColision(component)) return false;

        Vector3f[] triangles = getTriangles();
        Vector3f[] componentTriangles = component.getTriangles();

        for(int i = 0; i < triangles.length; i+=3)
            for(int j = 0; j < componentTriangles.length; j+=3)
                if(triTriIntersection(triangles[i], triangles[i+1], triangles[i+2], 
                    componentTriangles[i], componentTriangles[i+1], componentTriangles[i+2])) return true;
        return false;
    } 
    
    public void render() {
        Vector3f[] triangles = getTriangles();

        glBegin(GL_TRIANGLES);
            for(int i = 0; i < triangles.length; i+=3) {
                glVertex3f(triangles[i].x, triangles[i].y, triangles[i].z);
                glVertex3f(triangles[i+1].x, triangles[i+1].y, triangles[i+1].z);
                glVertex3f(triangles[i+2].x, triangles[i+2].y, triangles[i+2].z);
            }
            
    }
    public abstract Vector3f[] getTriangles(); //this is used to get a mesh of the object split into triangles.
    public abstract void onAddingToTheWindow(Window window);

    public Vector3f[] transform(Vector3f[] vertices) {
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(position)
            .rotateXYZ(rotation.x, rotation.y, rotation.z)
            .scale(scale);

        Vector3f[] transformedVertices = new Vector3f[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            transformedVertices[i] = vertices[i];
            transformedVertices[i] = transformedVertices[i].mulPosition(transformationMatrix);
        }
            
        return transformedVertices;
    }
    public float[] transform(float[] vertices) {
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(position)
            .rotateXYZ(rotation.x, rotation.y, rotation.z)
            .scale(scale);

        float[] transformedVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; i+=3) {
            transformedVertices[i] = new Vector3f(vertices[i], vertices[i+1], vertices[i+2]).mulPosition(transformationMatrix).x;
            transformedVertices[i+1] = new Vector3f(vertices[i], vertices[i+1], vertices[i+2]).mulPosition(transformationMatrix).y;
            transformedVertices[i+2] = new Vector3f(vertices[i], vertices[i+1], vertices[i+2]).mulPosition(transformationMatrix).z;
        }

        return transformedVertices;
    }

    public void rotate(Vector3f point, Vector3f rotation) {
        Vector3f delta = new Vector3f(point).sub(position);
        Matrix4f rotationMatrix = new Matrix4f()
            .translate(point)
            .rotateX(-rotation.x)
            .rotateY(-rotation.y)
            .rotateZ(-rotation.z)
            .translate(delta);

        position.mulPosition(rotationMatrix);
    }
    
    public Vector3f[] getVertices() { return getTriangles(); }
    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() {  return rotation; }
    public Vector3f getScale() { return scale; }

    public Component setPosition(float x, float y, float z) {
        position.set(x, y, z);
        return this;
    }
    public Component setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
        return this;
    }
    public Component setScale(float x, float y, float z) {
        scale.set(x, y, z);
        return this;
    }
    public Component setPosition(Vector3f position) {
        this.position = position;
        return this;
    }
    public Component setRotation(Vector3f rotate) {
        this.rotation = rotate;
        return this;
    }
    public Component setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }
}
