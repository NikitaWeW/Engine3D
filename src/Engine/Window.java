package Engine;

import Engine.Event.Event;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;


import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Window extends Event implements Runnable {
    private long window;
    private int width;
    private int height;
    private Position pos = new Position();
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
                GL11.glFrustum(-1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 100.0f);

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
    public Position getPos() {
        return pos;
    }
    public String getTitle() {
        return title;
    }
    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setSize(int width, int height) {
        this.height = height;
        glfwSetWindowSize(window, width, height);
    }
    public void setPos(int x, int y) {
        pos.X(x);
        pos.Y(y);
        glfwSetWindowPos(window, x, y);
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

}
