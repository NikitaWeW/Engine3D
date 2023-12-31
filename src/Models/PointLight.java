package Models;

import static org.lwjgl.opengl.GL11.*;

import Engine.Window;

public class PointLight extends Engine.Light {
    public PointLight() {
        super(GL_LIGHT0);
    }

    @Override
    public void render() {
    }

    @Override
    public void onAddingToTheWindow(Window window) {}
}
