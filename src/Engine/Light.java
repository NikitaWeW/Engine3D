package Engine;

import org.joml.*;

public abstract class Light {
    private int type;
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);

    public Light(int type) {this.type = type;} 

    public abstract void render();
    public abstract void onAddingToTheWindow(Window window);

     
    public int getType() { return type; } 
    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() {  return rotation; }

    public Light setType(int type) {
        this.type = type;
        return this;
    }
    public Light setPosition(Vector3f position) {
        this.position = position;
        return this;
    }
    public Light setRotation(Vector3f rotate) {
        this.rotation = rotate;
        return this;
    }
}
