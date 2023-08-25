package Models;

import org.lwjgl.opengl.GL11;

public class PointLight extends Engine.Light {
    @Override
    public void render() {
        GL11.glEnable(GL11.GL_LIGHT0);
    }
}
