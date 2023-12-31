package Models;

import Engine.Component;
import Engine.Window;

import org.joml.Vector3f;

public class Cube extends Component {
    public float size = 1.0f;

    @Override
    public Vector3f[] getTriangles() {
        return new Vector3f[] {
            // Front face
            new Vector3f(-size, -size, -size),
            new Vector3f(size, -size, -size),
            new Vector3f(size, size, -size),

            new Vector3f(-size, -size, -size),
            new Vector3f(size, size, -size),
            new Vector3f(-size, size, -size),

            // Back face
            new Vector3f(-size, -size, size),
            new Vector3f(size, -size, size),
            new Vector3f(size, size, size),

            new Vector3f(-size, -size, size),
            new Vector3f(size, size, size),
            new Vector3f(-size, size, size),

            // Top face
            new Vector3f(-size, size, -size),
            new Vector3f(size, size, -size),
            new Vector3f(size, size, size),

            new Vector3f(-size, size, -size),
            new Vector3f(size, size, size),
            new Vector3f(-size, size, size),

            // Bottom face
            new Vector3f(-size, -size, size),
            new Vector3f(-size, -size, -size),
            new Vector3f(size, -size, size),

            new Vector3f(-size, -size, size),
            new Vector3f(size, -size, size),
            new Vector3f(size, -size, -size),

            // Left face
            new Vector3f(-size, -size, size),
            new Vector3f(-size, -size, -size),
            new Vector3f(-size, size, -size),

            new Vector3f(-size, -size, size),
            new Vector3f(-size, size, -size),
            new Vector3f(-size, size, size),

            // Right face
            new Vector3f(size, -size, -size),
            new Vector3f(size, size, -size),
            new Vector3f(size, -size, size),

            new Vector3f(size, -size, -size),
            new Vector3f(size, -size, size),
            new Vector3f(size, size, size)
        };
    }

    @Override
    public void onAddingToTheWindow(Window window) {}
}
