package Models;

import Engine.Component;
import org.lwjgl.opengl.GL11;

public class Cube extends Component {
    @Override
    public void draw() {
        float size = 1.0f;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0.7f, 0.7f, 0.7f);

        GL11.glVertex3f(-size, size, -size);
        GL11.glVertex3f(size, size, -size);
        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(-size, size, size);

        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(-size, -size, size);
        GL11.glVertex3f(size, -size, size);
        GL11.glVertex3f(size, -size, -size);

        GL11.glVertex3f(-size, -size, size);
        GL11.glVertex3f(-size, size, size);
        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, -size, size);

        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(size, -size, -size);
        GL11.glVertex3f(size, size, -size);
        GL11.glVertex3f(-size, size, -size);

        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(-size, -size, size);
        GL11.glVertex3f(-size, size, size);
        GL11.glVertex3f(-size, size, -size);

        GL11.glVertex3f(size, -size, -size);
        GL11.glVertex3f(size, size, -size);
        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, -size, size);

        GL11.glEnd();
    }
}
