package Engine;

import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.io.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class Window implements Runnable, Serializable {
    private long window;
    private int width;
    private int height;
    private Camera camera = new Camera();
    private String title;
    private ArrayList<Component> components = new ArrayList<>();
    private ArrayList<Light> lights = new ArrayList<>();
    private ArrayList<Code> renderListeners = new ArrayList<>();
    private boolean lighting = false;
    private boolean rendering = true;

    public Window(int width, int height, String title) throws IllegalStateException {
        this.width = width;
        this.height = height;
        this.title = title;
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (!GLFW.glfwInit()) throw new IllegalStateException("There is no GLFW init");

        window = glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) throw new IllegalStateException("Failed to create a window");

        GLFW.glfwSwapInterval(0);

        while(!glfwWindowShouldClose(window)) {
            while(!rendering){} //just wait

            GLFW.glfwMakeContextCurrent(window);
            GL.createCapabilities();

            GL11.glViewport(0, 0, width, height);
            camera.configureGlFrustum(width, height);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            if(lighting) GL11.glEnable(GL_LIGHTING);

            renderLights();
            renderComponents();

            GL11.glDisable(GL_LIGHTING);
            GLFW.glfwSwapBuffers(window);
            glfwPollEvents();
            for (Code renderListener : renderListeners) {
                renderListener.doSomething();
            }
        }
        glfwDestroyWindow(window);
    }

    public void renderComponents() {
        for(Component c : components) {

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            glLoadIdentity();
            GL11.glFrustum(camera.getGlFrustum()[0], camera.getGlFrustum()[1], camera.getGlFrustum()[2], camera.getGlFrustum()[3], camera.getGlFrustum()[4], camera.getGlFrustum()[5]);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            glLoadIdentity();

            GL11.glTranslatef(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);

            Vector3f delta = new Vector3f(c.getPos()).sub(camera.getPosition());
            Matrix4f rotationMatrix = new Matrix4f()
                .rotateX(camera.getRotation().x)
                .rotateY(camera.getRotation().y)
                .rotateZ(camera.getRotation().z);

            Vector3f rotatedVector = rotationMatrix.transformDirection(delta);
            Vector3f newObjectPosition = new Vector3f(camera.getPosition()).add(rotatedVector);
            
            GL11.glPushMatrix();
            GL11.glTranslatef(newObjectPosition.x, newObjectPosition.y, newObjectPosition.z);
            
            GL11.glRotatef(c.getRotate().x, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(c.getRotate().y, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(c.getRotate().z, 0.0f, 0.0f, 1.0f);
            
            GL11.glScalef(c.getScale().x, c.getScale().y, c.getScale().z);

            c.render();

            GL11.glPopMatrix();
        }
    }
    public void renderLights() {
        for(Light light : lights) {
            Vector3f delta = new Vector3f(light.getPos()).sub(camera.getPosition());
            Matrix4f rotationMatrix = new Matrix4f()
                .rotateX(camera.getRotation().x)
                .rotateY(camera.getRotation().y)
                .rotateZ(camera.getRotation().z);

            Vector3f rotatedVector = rotationMatrix.transformDirection(delta);
            Vector3f newObjectPosition = new Vector3f(camera.getPosition()).add(rotatedVector);

            Vector3f rotatedPosition = new Vector3f(newObjectPosition)
                .rotateX(light.getRotate().x)
                .rotateY(light.getRotate().y)
                .rotateZ(light.getRotate().z);

            GL11.glLightfv(light.getType(), GL11.GL_POSITION, new float[] {rotatedPosition.x, rotatedPosition.y, rotatedPosition.z, 1.0f});

            GL11.glLightf(light.getType(), GL11.GL_CONSTANT_ATTENUATION, light.getAttenuationParameters().x);
            GL11.glLightf(light.getType(), GL11.GL_LINEAR_ATTENUATION, light.getAttenuationParameters().y);
            GL11.glLightf(light.getType(), GL11.GL_QUADRATIC_ATTENUATION, light.getAttenuationParameters().z);
            GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, new float[] { light.getIntensity().x, light.getIntensity().y, light.getIntensity().z, light.getIntensity().w });

            GL11.glEnable(light.getType());

            light.render();
        }
    }


    //getters
    public boolean isRendering() {
        return rendering;
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
    public ArrayList<Component> getComponents() {
        return components;
    }
    public Camera getCamera() {
        return camera;
    }
    public boolean isLighting() {
        return lighting;
    }
    public ArrayList<Light> getLights() {
        return lights;
    }
    public ArrayList<Code> getRenderListener() {
        return renderListeners;
    }
    
    //setters
    public Window rendering(boolean rendering) {
        this.rendering = rendering;
        return this;
    }
    public Window setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window, width, height);
        return this;
    }
    public Window setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
        return this;
    }
    public Window addComponent(Component c) {
        components.add(c);
        return this;
    }
    public Window removeComponent(Component c) {
        components.remove(c);
        return this;
    }
    public Window setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }
    public Window setLighting(boolean lighting) {
        this.lighting = lighting;
        return this;
    }
    public Window addLight(Light light) {
        lights.add(light);
        return this;
    }
    public Window removeLight(Light light) {
        lights.remove(light);
        return this;
    }
    public Window addRenderListener(Code renderListener) {
        renderListeners.add(renderListener);
        return this;
    }
    public Window removeRenderListener(Code renderListener) {
        renderListeners.remove(renderListener);
        return this;
    }
}
