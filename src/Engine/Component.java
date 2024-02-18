package Engine;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static Engine.CollisionDetection.*;

public abstract class Component {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    private float[] triangles;

    public boolean checkAABBColision(Component component) {
        Vector3f[] AABB = findAABB3D(transform(getVertices()));
        Vector3f[] componentAABB = findAABB3D(component.transform(component.getVertices()));
        return checkAABBColision3D(AABB[0], AABB[1], componentAABB[0], componentAABB[1]);
    }
    public boolean checkColision(Component component) {
        if(!checkAABBColision(component)) return false;

        Vector3f[] triangles = toVector3f(this.triangles);
        Vector3f[] componentTriangles = toVector3f(component.triangles);

        for(int i = 0; i < triangles.length; i+=3)
            for(int j = 0; j < componentTriangles.length; j+=3)
                if(triTriIntersection3D(triangles[i], triangles[i+1], triangles[i+2], 
                    componentTriangles[j], componentTriangles[j+1], componentTriangles[j+2])) return true;
        return false;
    }
    
    public void render() {
        glBegin(GL_TRIANGLES);
        float[] triangles = getTriangles();
        for(int i = 0; i < triangles.length; i+=3) glVertex3f(triangles[i], triangles[i+1], triangles[i+2]);
        glEnd();
    }

    public void onAddingToTheWindow(Window window) {}
    public void onRender() {};

    public static float[] toFloat(Vector3f[] points) {
        float[] xyz = new float[points.length*3];
        for (int i = 0; i < points.length; i++) {
            xyz[i*3] = points[i].x;
            xyz[i*3+1] = points[i].y;
            xyz[i*3+2] = points[i].z;
        }
        return xyz;
    }
    public static Vector3f[] toVector3f(float[] points) {
        Vector3f[] xyz = new Vector3f[points.length/3];
        for (int i = 0; i < points.length; i+=3) {
            xyz[i/3] = new Vector3f(points[i], points[i+1], points[i+2]);
        }
        return xyz;
    }

    public Vector3f[] transform(Vector3f[] vertices) {
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(position)
            .rotateXYZ(rotation.x, rotation.y, rotation.z)
            .scale(scale);

        Vector3f[] transformedVertices = new Vector3f[vertices.length];
        for (int i = 0; i < vertices.length; i++) 
            transformedVertices[i] = vertices[i].mulPosition(transformationMatrix);
            
        return transformedVertices;
    }
    public float[] transform(float[] vertices) {
        Matrix4f transformationMatrix = new Matrix4f()
            .translate(position)
            .rotateXYZ(rotation.x, rotation.y, rotation.z)
            .scale(scale);

        float[] transformedVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; i+=3) {
            if(i+3 > vertices.length) break;
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
            .rotateX(rotation.x)
            .rotateY(rotation.y)
            .rotateZ(rotation.z)
            .translate(delta);

        position.mulPosition(rotationMatrix);
    }
    
    public Vector3f[] getVertices() { return toVector3f(triangles); }
    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() { return rotation; }
    public Vector3f getScale() { return scale; }
    public float[] getTriangles() { return triangles; } //this is used to get a mesh of the object split into triangles.

    public Component setTriangles(float[] triangles) {
        this.triangles = triangles;
        return this;
    } //this is used to set a mesh of the object split into triangles.

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
    public Component setRotation(Vector3f rotation) {
        this.rotation = rotation;
        return this;
    }
    public Component setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }
}
