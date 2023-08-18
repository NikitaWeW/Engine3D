package Engine;

import org.joml.Vector3f;


public abstract class Component implements Drawable {
    private Vector3f pos = new Vector3f(0, 0, -10);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    public boolean checkColision(Component component) {
        return false;
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
