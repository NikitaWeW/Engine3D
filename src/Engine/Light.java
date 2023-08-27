package Engine;

import org.joml.*;

public abstract class Light {
    private Vector3f pos = new Vector3f(0, 0, 0);
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private Vector4f intensity = new Vector4f(1, 1, 1, 0);
    private Vector3f attenuationParameters = new Vector3f();
    private int type;

    public abstract void render();

    public Light(int type) {
        this.type = type;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Vector3f getPos() {
        return pos;
    }
    public void setRotate(Vector3f rotate) {
        this.rotate = rotate;
    }
    public Vector3f getRotate() {
        return rotate;
    }
    public void setAttenuationParameters(Vector3f attenuationParameters) {
        this.attenuationParameters = attenuationParameters;
    }
    public Vector3f getAttenuationParameters() {
        return attenuationParameters;
    }
    public void setIntensity(Vector4f intensity) {
        this.intensity = intensity;
    }
    public Vector4f getIntensity() {
        return intensity;
    }
    public int getType() {
        return type;
    }
}
