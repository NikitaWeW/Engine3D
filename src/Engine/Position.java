package Engine;

import org.joml.Vector3f;

import java.io.Serializable;

public class Position implements Serializable {
    private Vector3f pos = new Vector3f(0, 0, 0);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(0, 0, 0);
    
    public Position() {}
    public Position(float x, float y, float z) {
        pos.set(x, y, z);
    }
    public Position(float d) {
        pos.set(d);
    }

    public void X(float x) {
        pos.x = x;
    }
    public void Y(float y) {
        pos.y = y;
    }
    public void Z(float z) {
        pos.z = z;
    }

    public void angleX(float x) {
        rotate.z = x;
    }
    public void angleY(float y) {
        rotate.y = y;
    }
    public void angleZ(float z) {
        rotate.z = z;
    }

    public void scaleX(float x) {
        scale.x = x;
    }
    public void scaleY(float y) {
        scale.y = y;
    }
    public void scaleZ(float z) {
        scale.z = z;
    }

    public float X() {
        return pos.x;
    }
    public float Y() {
        return pos.y;
    }
    public float Z() {
        return pos.z;
    }

    public float angleX() {
        return rotate.x;
    }
    public float angleY() {
        return rotate.y;
    }
    public float angleZ() {
        return rotate.z;
    }

    public float scaleX() {
        return scale.x;
    }
    public float scaleY() {
        return scale.y;
    }
    public float scaleZ() {
        return scale.z;
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
}
