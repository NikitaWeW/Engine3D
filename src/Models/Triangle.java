package Models;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Triangle extends Engine.Component {
    public Vector3f A = new Vector3f();
    public Vector3f B = new Vector3f();
    public Vector3f C = new Vector3f();

    public Vector3f pointColor = new Vector3f(1);
    public Vector3f color = new Vector3f(0.7f);
    public float pointSize = 10;

    @Override
    public void render() {
        glColor3f(pointColor.x, pointColor.y, pointColor.z);
        glPointSize(pointSize);
        glBegin(GL_POINTS);
        glVertex3f(A.x, A.y, A.z);
        glVertex3f(B.x, B.y, B.z);
        glVertex3f(C.x, C.y, C.z);
        glEnd();

        glColor3f(color.x, color.y, color.z);
        super.render();
    }

    @Override
    public float[] getTriangles() {
        updateVertices();
        return super.getTriangles();
    }

    public void updateVertices() {
        setTriangles(new float[] {
            A.x, A.y, A.z,
            B.x, B.y, B.z,
            C.x, C.y, C.z
        });
    }
}
