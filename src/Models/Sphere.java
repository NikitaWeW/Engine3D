package Models;

import Engine.Component;
import Engine.Window;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Sphere extends Component {
    public float radius = 1.0f;
    public int gradation = 20;

    public final float PI = (float) Math.PI;
    @Override
    public Vector3f[] getTriangles() {
        ArrayList<Vector3f> triangles = new ArrayList<Vector3f>() {{
            float x, y, z, alpha, beta;
            for (alpha = 0.0f; alpha < Math.PI; alpha += PI / gradation) {
                for (beta = 0.0f; beta < 2.01f * Math.PI; beta += PI / gradation) {
                    x = (float) (radius * Math.cos(beta) * Math.sin(alpha));
                    y = (float) (radius * Math.sin(beta) * Math.sin(alpha));
                    z = (float) (radius * Math.cos(alpha));
                    add(new Vector3f(x, y, z));
                    x = (float) (radius * Math.cos(beta) * Math.sin(alpha + PI / gradation));
                    y = (float) (radius * Math.sin(beta) * Math.sin(alpha + PI / gradation));
                    z = (float) (radius * Math.cos(alpha + PI / gradation));
                    add(new Vector3f(x, y, z));
                }
            }
        }};
        return triangles.toArray(new Vector3f[triangles.size()]);
    }
    @Override
    public void onAddingToTheWindow(Window window) {}
}
