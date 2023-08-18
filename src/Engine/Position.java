package Engine;

import org.joml.Vector3f;

import java.io.Serializable;

public class Position implements Serializable {
    private Vector3f pos = new Vector3f(0, 0, -10);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);
    
    public Position() {}
    public Position(float x, float y, float z) {
        pos.set(x, y, z);
    }
    public Position(float d) {
        pos.set(d);
    }

    public Position X(float x) {
        pos.x = x;
        return this;
    }
    public Position Y(float y) {
        pos.y = y;
        return this;
    }
    public Position Z(float z) {
        pos.z = z;
        return this;
    }

    public Position angleX(float x) {
        rotate.x = x;
        return this;
    }
    public Position angleY(float y) {
        rotate.y = y;
        return this;
    }
    public Position angleZ(float z) {
        rotate.z = z;
        return this;
    }

    public Position scaleX(float x) {
        scale.x = x;
        return this;
    }
    public Position scaleY(float y) {
        scale.y = y;
        return this;
    }
    public Position scaleZ(float z) {
        scale.z = z;
        return this;
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

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void setRotation(Vector3f rotation) {
        this.rotate = rotation;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
