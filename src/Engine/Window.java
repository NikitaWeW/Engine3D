package Engine;

import Engine.Event.Event;

import org.joml.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;

import java.lang.Math;
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
    private ArrayList<Light> lights = new ArrayList<>();
    private float fov = 45.0f;
    private boolean lighting = false;

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
        if (window == 0)
            throw new IllegalStateException("Failed to create a window");

        GLFW.glfwSwapInterval(1);

        while (!glfwWindowShouldClose(window)) {
            GLFW.glfwMakeContextCurrent(window);
            GL.createCapabilities();

            GL11.glViewport(0, 0, width, height);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glEnable(GL11.GL_DEPTH_TEST);

                if(lighting)
                    GL11.glEnable(GL_LIGHTING);

            for(Light light : lights) {
                Vector3f delta = new Vector3f(light.getPos()).sub(cam.getPosition());
                Matrix4f rotationMatrix = new Matrix4f()
                    .rotateX(cam.getRotation().x)
                    .rotateY(cam.getRotation().y)
                    .rotateZ(cam.getRotation().z);

                Vector3f rotatedVector = rotationMatrix.transformDirection(delta);
                Vector3f newObjectPosition = new Vector3f(cam.getPosition()).add(rotatedVector);

                
                Vector3f rotatedPosition = new Vector3f(newObjectPosition)
                    .rotateX(light.getRotate().x)
                    .rotateY(light.getRotate().y)
                    .rotateZ(light.getRotate().z);

                GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, new float[] {rotatedPosition.x, rotatedPosition.y, rotatedPosition.z, 1.0f});

                GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_CONSTANT_ATTENUATION, light.getAttenuationParameters().x);
                GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_LINEAR_ATTENUATION, light.getAttenuationParameters().y);
                GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_QUADRATIC_ATTENUATION, light.getAttenuationParameters().z);

                light.render();

            }
            for(Component c : components) {
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

                c.render();

                GL11.glPopMatrix();
            }

            if(lighting)
                GL11.glDisable(GL_LIGHTING);

            GLFW.glfwSwapBuffers(window);
            glfwPollEvents();
            update();
        }

        glfwDestroyWindow(window);
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
    public boolean getLighting() {
        return lighting;
    }
    public ArrayList<Light> getLights() {
        return lights;
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
    public void setLighting(boolean lighting) {
        this.lighting = lighting;
    }
    public void addLight(Light light) {
        lights.add(light);
    }
    public void removeLight(Light light) {
        lights.remove(light);
    }
}
