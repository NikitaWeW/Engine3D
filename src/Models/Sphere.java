package Models;

import Engine.Component;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Sphere extends Component {
    public float radius = 1.0f;
    public int gradation = 5;

    public final float PI = (float) Math.PI;
    @Override
    public void render() {
        glBegin(GL_TRIANGLE_STRIP);
        float x, y, z, alpha, beta;
        for (alpha = 0.0f; alpha < Math.PI; alpha += PI / gradation) {
            for (beta = 0.0f; beta < 2.01f * Math.PI; beta += PI / gradation) {
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha));
                z = (float) (radius * Math.cos(alpha));
                GL11.glVertex3f(x, y, z);
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha + PI / gradation));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha + PI / gradation));
                z = (float) (radius * Math.cos(alpha + PI / gradation));
                GL11.glVertex3f(x, y, z);
            }
        }
        glEnd();
    }
    @Override
    public ArrayList<Vector3f> getEdges() {
        return new ArrayList<Vector3f>() {{}};
    }
}
