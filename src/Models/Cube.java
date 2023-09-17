package Models;

import Engine.Component;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class Cube extends Component {
    public float size = 1.0f;
    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0.7f, 0.7f, 0.7f);
        for(Vector3f vertex : getVertices()) {
            GL11.glVertex3f(vertex.x, vertex.y, vertex.z);
        }
        GL11.glEnd();
    }

    @Override
    public ArrayList<Vector3f> getVertices() {
        return new ArrayList<Vector3f>() {{
            add(new Vector3f(-size, size, -size));
            add(new Vector3f(size, size, -size));
            add(new Vector3f(size, size, size));
            add(new Vector3f(-size, size, size));
            add(new Vector3f(-size, -size, -size));
            add(new Vector3f(-size, -size, size));
            add(new Vector3f(size, -size, size));
            add(new Vector3f(size, -size, -size));
            add(new Vector3f(-size, -size, size));
            add(new Vector3f(-size, size, size));
            add(new Vector3f(size, size, size));
            add(new Vector3f(size, -size, size));
            add(new Vector3f(-size, -size, -size));
            add(new Vector3f(size, -size, -size));
            add(new Vector3f(size, size, -size));
            add(new Vector3f(-size, size, -size));
            add(new Vector3f(-size, -size, -size));
            add(new Vector3f(-size, -size, size));
            add(new Vector3f(-size, size, size));
            add(new Vector3f(-size, size, -size));
            add(new Vector3f(size, -size, -size));
            add(new Vector3f(size, size, -size));
            add(new Vector3f(size, size, size));
            add(new Vector3f(size, -size, size));
        }};
    }
}
