package Models;

import org.joml.Vector3f;

public class Cube extends Engine.Component {
    public float size = 1.0f;

    public Cube() {
        setTriangles(new float[] {
            // Front face
            -size, -size, -size,
            size, -size, -size,
            size, size, -size,

            -size, -size, -size,
            size, size, -size,
            -size, size, -size,

            // Back face
            -size, -size, size,
            size, -size, size,
            size, size, size,

            -size, -size, size,
            size, size, size,
            -size, size, size,

            // Top face
            -size, size, -size,
            size, size, -size,
            size, size, size,

            -size, size, -size,
            size, size, size,
            -size, size, size,

            // Bottom face
            size, -size, -size,
            -size, -size, -size,
            size, -size, size,

            -size, -size, -size,
            size, -size, size,
            -size, -size, size,

            // Left face
            -size, -size, size,
            -size, -size, -size,
            -size, size, -size,

            -size, -size, size,
            -size, size, -size,
            -size, size, size, 

            // Right face
            size, -size, -size,
            size, size, size,
            size, -size, size,

            size, -size, -size,
            size, size, size,
            size, size, -size});
    } 
    @Override
    public boolean checkColision(Engine.Component component) {
        if(component instanceof Cube && component.getRotation().equals(new Vector3f(0))) return checkAABBColision(component);
        return super.checkColision(component);
    }
}
