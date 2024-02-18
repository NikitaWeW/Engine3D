import Engine.*;
import Models.*;

import static Engine.CollisionDetection.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;
import org.joml.Vector2f;

public class Main {
    public static float distance = 11;
    public static Vector3f rotation = new Vector3f();

    public static boolean moving = false;

    public static Vector3f A = new Vector3f(-1, 1, 0);
    public static Vector3f B = new Vector3f(1, -1, 0);

    public static Vector3f P = new Vector3f(-1, -1, 0);
    public static Vector3f Q = new Vector3f(1, 1, 0);
    
    public static Vector3f C = new Vector3f(0, -1, 1);
    public static Vector3f R = new Vector3f();


    public static void main(String[] args) throws Exception {
        org.lwjgl.glfw.GLFW.glfwInit();

        Window windows = new Window(2000, 1000, "");

        Segment seg1 = new Segment();
        seg1.A = A;
        seg1.B = B;

        Segment seg2 = new Segment();
        seg2.A = P;
        seg2.B = Q;

        windows.addRenderListener(() -> {
            rotation.add(0.0f, 0.1f, 0);
            float[] D = segmentSegmentIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
            glClearColor(0.09f, 0.09f, 0.09f, 0);
            glTranslatef(0, 0, -distance);
            boolean result = D != null;

            glDisable(GL_POINT_SMOOTH);
            glPointSize(100);
            glBegin(GL_POINTS);
                glColor3d(1, 0.1, 0.1);
                if(result) glColor3d(0.1, 1, 0.1);
                glVertex2d(-7.5, -3);
            glEnd();
            
            glRotatef(rotation.x, 1, 0, 0);
            glRotatef(rotation.y, 0, 1, 0);
            glRotatef(rotation.z, 0, 0, 1);

            glPointSize(10);
            glEnable(GL_POINT_SMOOTH);
            glBegin(GL_POINTS);
                glColor3d(1, 0.55, 0);
                if(result) glVertex2f(D[0], D[1]);
            glEnd();
            })
            .addRenderListener(() -> {
                try { Thread.sleep(1); } 
                catch (InterruptedException e) { e.printStackTrace(); }
            });
        windows
            .addCloseListener(() -> org.lwjgl.glfw.GLFW.glfwTerminate())
            .resizable(true)
            .setLighting(false)
            .addComponent(seg1)
            .addComponent(seg2)
            .getCamera()
                //.setPosition(0, 0, distance)
                .setRotation(0, 0, 0);
        
    }
}
