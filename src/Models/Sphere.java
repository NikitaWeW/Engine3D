package Models;

import Engine.Component;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Sphere extends Component {
    public float radius = 1.0f;
    public int gradation = 100;
    public final float PI = (float) Math.PI;
    @Override
    public void render() {
            glBegin(GL_TRIANGLE_STRIP);
            for(Vector3f vertex : getVertices())
                glVertex3f(vertex.x, vertex.y, vertex.z);
            glEnd();
    }
    @Override
    public ArrayList<Vector3f> getVertices() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        float x, y, z, alpha, beta;
        for (alpha = 0.0f; alpha < Math.PI; alpha += PI / gradation) {
            for (beta = 0.0f; beta < 2.01f * Math.PI; beta += PI / gradation) {
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha));
                z = (float) (radius * Math.cos(alpha));
                vertices.add(new Vector3f(x, y, z));
                if(x == 2)
                System.out.println("!");
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha + PI / gradation));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha + PI / gradation));
                z = (float) (radius * Math.cos(alpha + PI / gradation));
                vertices.add(new Vector3f(x, y, z));
            }
        }
        return vertices;
    }
}
