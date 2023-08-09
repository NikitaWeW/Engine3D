package Engine;

import Engine.Event.Event;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.w3c.dom.ls.LSOutput;


import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Window extends Event implements Runnable, Serializable {
    private long window;
    private int width;
    private int height;
    private float[] glFrustum = new float[] {-1.0f, 1.0f, -1.0f, 0.5f, 1.0f, 100.0f};
    private String title;
    private ArrayList<Component> components = new ArrayList<>();

    public Window(int width, int height, String title) throws IllegalStateException {
        this.width = width;
        this.height = height;
        this.title = title;
        new Thread(this).start();

    }

    @Override
    public void run() {
        if(!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if(window == 0) {
            glfwTerminate();
            throw new IllegalStateException("Failed to create a window");
        }
        GLFW.glfwShowWindow(window);

        while (!glfwWindowShouldClose(window)) {

            GLFW.glfwMakeContextCurrent(window);
            GL.createCapabilities();

            GL11.glViewport(0, 0, width, height);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glEnable(GL11.GL_DEPTH_TEST);

            for(Component c : components) {
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();
                GL11.glFrustum(glFrustum[0], glFrustum[1], glFrustum[2], glFrustum[3], glFrustum[4], glFrustum[5]);

                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();

                GL11.glTranslatef(c.pos().X(), c.pos().Y(), c.pos().Z());

                GL11.glScalef(c.pos().scaleX(), c.pos().scaleY(), c.pos().scaleZ());

                GL11.glRotatef(c.pos().angleX(), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(c.pos().angleY(), 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(c.pos().angleZ(), 0.0f, 0.0f, 1.0f);

                c.drawable().draw();

                GLFW.glfwSwapBuffers(window);
            }
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

    public void setSize(int width, int height) {
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
}
