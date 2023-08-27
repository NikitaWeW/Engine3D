package Models;

import org.lwjgl.opengl.GL11;

public class PointLight extends Engine.Light {
    public PointLight() {
        super(GL11.GL_LIGHT0);
    }

    @Override
    public void render() {
    }
}
