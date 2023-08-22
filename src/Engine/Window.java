package Engine;

import Engine.Event.Event;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.io.Serializable;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window extends Event implements Runnable, Serializable {
    private long window;
    private int width;
    private int height;
    private Camera cam = new Camera();
    private float[] glFrustum = new float[] {-1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 100.0f};
    private String title;
    private ArrayList<Component> components = new ArrayList<>();
    private float fov = 45.0f;

    public Window(int width, int height, String title) throws IllegalStateException {
        this.width = width;
        this.height = height;
        this.title = title;
        new Thread(this).start();
    }

    @Override
public void run() {
    if (!GLFW.glfwInit())
        throw new IllegalStateException("Failed to initialize GLFW");

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(width, height, title, 0, 0);
    if (window == 0) {
        glfwTerminate();
        throw new IllegalStateException("Failed to create a window");
    }

    GLFW.glfwSwapInterval(1);

    while (!glfwWindowShouldClose(window)) {
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GL11.glViewport(0, 0, width, height);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        for (Component c : components) {
            float aspectRatio = (float) width / height;
            glFrustum[3] = glFrustum[4] * (float) Math.tan(Math.toRadians(fov / 2));
            glFrustum[2] = -glFrustum[3];
            glFrustum[1] = glFrustum[3] * aspectRatio;
            glFrustum[0] = -glFrustum[1];

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            glLoadIdentity();
            GL11.glFrustum(glFrustum[0], glFrustum[1], glFrustum[2], glFrustum[3], glFrustum[4], glFrustum[5]);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            glLoadIdentity();
            
            GL11.glTranslatef(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z);

            GL11.glRotatef(-cam.getRotation().x, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-cam.getRotation().y, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-cam.getRotation().z, 0.0f, 0.0f, 1.0f);

            Vector3f delta = new Vector3f(c.getPos()).sub(cam.getPosition());
            Matrix4f rotationMatrix = new Matrix4f()
                .rotateX(cam.getRotation().x)
                .rotateY(cam.getRotation().y)
                .rotateZ(cam.getRotation().z);

            Vector3f rotatedVector = rotationMatrix.transformDirection(delta);
            Vector3f newObjectPosition = new Vector3f(cam.getPosition()).add(rotatedVector);

            GL11.glPushMatrix();
            GL11.glTranslatef(newObjectPosition.x, newObjectPosition.y, newObjectPosition.z);

            GL11.glRotatef(c.getRotate().x, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(c.getRotate().y, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(c.getRotate().z, 0.0f, 0.0f, 1.0f);

            GL11.glScalef(c.getScale().x, c.getScale().y, c.getScale().z);

            c.draw();

            GL11.glPopMatrix();
        }
        GLFW.glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    update();
}

    public long getWindow() {
        return window;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getTitle() {
        return title;
    }
    public float[] getGlFrustum() {
        return glFrustum;
    }
    public ArrayList<Component> getComponents() {
        return components;
    }
    public Camera cam() {
        return cam;
    }
    public float getFov() {
        return fov;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window, width, height);
    }
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
    }
    public void addComponent(Component c) {
        components.add(c);
    }
    public void removeComponent(Component c) {
        components.remove(c);
    }
    public void setGlFrustum(float[] glFrustum) {
        if(glFrustum.length == 6)
            this.glFrustum = glFrustum;
    }
    public void setCam(Camera cam) {
        this.cam = cam;
    }
    public void setFov(float fov) {
        this.fov = fov;
    }
}
