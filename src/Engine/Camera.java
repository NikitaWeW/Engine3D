package Engine;

import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);

    public Vector3f getPosition() {
        return position;
    }
    public Vector3f getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Camera() {}
    public Camera(Vector3f position, Vector3f rotation) {
        setPosition(position);
        setRotation(rotation);
    }
    public Camera(Vector3f position) {
        setPosition(position);
    }
    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }
    public Camera(float x, float y, float z, float rotationX, float rotationY, float rotationZ) {
        position = new Vector3f(x, y, z);
        rotation = new Vector3f(rotationX, rotationY, rotationZ);
    }
}