package Models;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

public class Segment extends Engine.Component {
    public Vector3f A = new Vector3f();
    public Vector3f B = new Vector3f();

    public Vector3f pointColor = new Vector3f(1);
    public Vector3f color = new Vector3f(0.7f);
    public float pointSize = 10;
    public float width = 10;

    @Override
    public void render() {
        glColor3f(pointColor.x, pointColor.y, pointColor.z);
        glPointSize(pointSize);
        glBegin(GL_POINTS);
        glVertex3f(A.x, A.y, A.z);
        glVertex3f(B.x, B.y, B.z);
        glEnd();

        glColor3f(color.x, color.y, color.z);
        glLineWidth(width);
        glBegin(GL_LINES);
        glVertex3f(A.x, A.y, A.z);
        glVertex3f(B.x, B.y, B.z);
        glEnd();
    }

    @Override
    public float[] getTriangles() { return null; }
}
