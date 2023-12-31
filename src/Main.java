import Engine.*;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

public class Main {
    public static float i = 0;
    public static boolean rotation = true;
    public static boolean moving = true;
    public static float distance = 10;

    public static Vector3f A = new Vector3f(-3, 0, 0);        
    public static Vector3f B = new Vector3f(2, 1.5f, 1.5f);
    public static Vector3f C = new Vector3f(0, 0, 0);
    public static Vector3f P = new Vector3f(0, 3, 0);
    public static Vector3f Q = new Vector3f(0, 0, 1);
    public static Vector3f R = new Vector3f(0, 2, 1);

    public static void main(String[] args) throws Exception {
        org.lwjgl.glfw.GLFW.glfwInit();

        System.out.println("Hello world!");
        Window windows = new Window(2000, 1000, "");

        windows.setLighting(false)
            //.addRenderListener(() -> windows.getCamera().getRotation().add(0.0f, 0.1f, 0.0f))
            .addRenderListener(() -> {
                glPushMatrix();

                glTranslatef(0, 0, -distance);
                glPushMatrix();
                glRotatef(i, 0, 1, 0);

                glBegin(GL_TRIANGLES);
                    glColor3d(1, 0, 0); glVertex3d(A.x, A.y, A.z);
                    glColor3d(0, 1, 0); glVertex3d(B.x, B.y, B.z);
                    glColor3d(0, 0, 1); glVertex3d(C.x, C.y, C.z);
                    
                    glColor3d(1, 0, 0); glVertex3d(P.x, P.y, P.z);
                    glColor3d(0, 1, 0); glVertex3d(Q.x, Q.y, Q.z);
                    glColor3d(0, 0, 1); glVertex3d(R.x, R.y, R.z);
                glEnd();
                glPopMatrix();

                glPointSize(100);
                glBegin(GL_POINTS);
                    glColor3f(1, 0, 0);
                    if(Component.triTriIntersection(A, B, C, P, Q, R)) glColor3f(0, 1, 0);
                    glVertex3f(-5, 0, 0);
                glEnd();

                glPopMatrix();
                
                if(moving) { if(Q.x <= 5) Q.x+=0.01f; else Q.x = -5; }
                if(rotation) i+=1;
                if(i >= 360) i-= 360;
                try { Thread.sleep(1); } 
                catch (InterruptedException e) { e.printStackTrace(); }
            })
            .addCloseListener(() -> System.out.println("Is it a bug?"))
            .resizable(true)
            .getCamera().setPosition(0, 0, -5);

        System.out.println(Component.triSegIntersection(A, B, C, P, Q));
    }
}
