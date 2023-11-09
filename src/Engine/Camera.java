package Engine;

import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private float fov = 45.0f;
    private float[] glFrustum = new float[] {-1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 100.0f};

    public Camera configureGlFrustum(int width, int height) {
        float aspectRatio = (float) width / height;
        glFrustum[3] = glFrustum[4] * (float) Math.tan(Math.toRadians(fov / 2));
        glFrustum[2] = -glFrustum[3];
        glFrustum[1] = glFrustum[3] * aspectRatio;
        glFrustum[0] = -glFrustum[1];
        return this;
    }

    public Vector3f getPosition() {
        return position;
    }
    public Vector3f getRotation() {
        return rotation;
    }
    public float[] getGlFrustum() {
        return glFrustum;
    }
    public float getFov() {
        return fov;
    }

    public Camera setPosition(Vector3f position) {
        this.position = position;
        return this;
    }
    public Camera setRotation(Vector3f rotation) {
        this.rotation = rotation;
        return this;
    }
    public Camera setGlFrustum(float[] glFrustum) {
        if(glFrustum.length == 6)
            this.glFrustum = glFrustum;
        return this;
    }
    public Camera setFov(float fov) {
        this.fov = fov;
        return this;
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