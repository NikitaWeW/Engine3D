package Engine;

import org.joml.Vector3f;
import org.joml.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class Window implements Runnable, java.io.Serializable {
    private long window;
    private int width;
    private int height;
    private boolean resizable = true;
    private Camera camera = new Camera();
    private String title;
    private ArrayList<Component> components = new ArrayList<>();
    private ArrayList<Light> lights = new ArrayList<>();
    private ArrayList<GUI> GUIs = new ArrayList<>();
    private ArrayList<Code> renderListeners = new ArrayList<>();
    private ArrayList<Code> componentRenderListeners = new ArrayList<>();
    private ArrayList<Code> lightRenderListeners = new ArrayList<>();
    private ArrayList<Code> closeListeners = new ArrayList<>();
    private boolean lighting = false;
    private boolean rendering = true;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        new Thread(this, "rendering").start();
    }
    public Window() { this(800, 600, ""); }

    @Override //main loop
    public void run() {
        window = glfwCreateWindow(width, height, title, 0, 0);

        glfwSwapInterval(0);
        
        camera.configureGlFrustum(width, height);

        while(!glfwWindowShouldClose(window)) { //main loop
            while(!rendering){} //just wait
            render();
        }
        
        for (Code closeListener : closeListeners) closeListener.doSomething();

        glfwDestroyWindow(window);
    }
    public void render() {
        glfwMakeContextCurrent(window);
        org.lwjgl.opengl.GL.createCapabilities();

        if(!resizable) glfwSetWindowSize(window, width, height);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        float[] glFrustrum = camera.getGlFrustum();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(glFrustrum[0], glFrustrum[1], glFrustrum[2], glFrustrum[3], glFrustrum[4], glFrustrum[5]);

        glEnable(GL_DEPTH_TEST);
        if(lighting) {
            glEnable(GL_LIGHTING);
            glEnable(GL_COLOR_MATERIAL);
        }

        for(Code renderListener : renderListeners) renderListener.doSomething();
        for(Component component : components) { //render all components
            glPushMatrix();

            component.onRender();

            Vector3f cameraRotation = camera.getRotation();
            Vector3f componentRotation = component.getRotation();
            Vector3f componentScale = component.getScale();

            for (Code renderListener : componentRenderListeners) renderListener.doSomething();

            //rotate camera
            Vector3f delta = new Vector3f(component.getPosition()).sub(camera.getPosition());

            glRotatef(-cameraRotation.x, 1, 0, 0);
            glRotatef(-cameraRotation.y, 0, 1, 0);
            glRotatef(-cameraRotation.z, 0, 0, 1);

            glTranslatef(delta.x, delta.y, delta.z);

            //transform component
            glRotatef(componentRotation.x, 1, 0, 0);
            glRotatef(componentRotation.y, 0, 1, 0);
            glRotatef(componentRotation.z, 0, 0, 1);

            glScalef(componentScale.x, componentScale.y, componentScale.z);

            component.render();

            glPopMatrix();
        }

        glDisable(GL_LIGHTING);
        glDisable(GL_COLOR_MATERIAL);

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    //getters
    public boolean isResizable() { return resizable; }
    public boolean isRendering() { return rendering; }
    public long getWindow() { return window; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getTitle() { return title; }
    public ArrayList<Component> getComponents() { return components; }
    public Camera getCamera() { return camera; }
    public boolean isLighting() { return lighting; }
    public ArrayList<Light> getLights() { return lights; }
    public ArrayList<Code> getRenderListeners() { return renderListeners; }
    public ArrayList<Code> getComponentRenderListeners() { return componentRenderListeners; }
    public ArrayList<Code> getLightRenderListeners() { return lightRenderListeners; }
    public ArrayList<Code> getCloseListeners() { return closeListeners; }
    public boolean isOpen() { return(glfwWindowShouldClose(window)); }
    
    //setters
    public Window resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }
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
        c.onAddingToTheWindow(this);
        return this;
    }
    public Window removeComponent(Component c) {
        components.remove(c);
        c.onAddingToTheWindow(null);
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
        light.onAddingToTheWindow(this);
        return this;
    }
    public Window removeLight(Light light) {
        lights.remove(light);
        light.onAddingToTheWindow(null);
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
    public Window addCloseListener(Code closeListener) {
        closeListeners.add(closeListener);
        return this;
    }
    public Window removeCloseListener(Code closeListener) {
        closeListeners.remove(closeListener);
        return this;
    }
    public Window addComponentRenderListener(Code componentRenderListener) {
        componentRenderListeners.add(componentRenderListener);
        return this;
    }
    public Window removeComponentRenderListener(Code componentRenderListener) {
        componentRenderListeners.remove(componentRenderListener);
        return this;
    }
    public Window addLightRenderListener(Code lightRenderListener) {
        lightRenderListeners.add(lightRenderListener);
        return this;
    }
    public Window removeLightRenderListener(Code lightRenderListener) {
        lightRenderListeners.remove(lightRenderListener);
        return this;
    }
}
