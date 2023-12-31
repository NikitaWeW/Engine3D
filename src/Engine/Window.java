package Engine;

import org.joml.Vector3f;

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
    private ArrayList<Code> renderListeners = new ArrayList<>();
    private ArrayList<Code> componentRenderListeners = new ArrayList<>();
    private ArrayList<Code> lightRenderListeners = new ArrayList<>();
    private ArrayList<Code> closeListeners = new ArrayList<>();
    private boolean lighting = false;
    private boolean rendering = true;

    public Window(int width, int height, String title) throws IllegalStateException {
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

        while(!glfwWindowShouldClose(window)) {
            while(!rendering){} //just wait
            render();
        }
        
        for (Code closeListener : closeListeners) closeListener.doSomething();

        glfwDestroyWindow(window);
    }

    //render stuff
    public void renderComponent(Component component) {
        glPushMatrix();

        Vector3f cameraRotation = camera.getRotation();
        Vector3f componentRotation = component.getRotation();
        Vector3f componentScale = component.getScale();

        //rotate camera
        Vector3f delta = new Vector3f(camera.getPosition()).sub(component.getPosition());

        glRotatef(-cameraRotation.x, 1, 0, 0);
        glRotatef(-cameraRotation.y, 0, 1, 0);
        glRotatef(-cameraRotation.z, 0, 0, 1);

        glTranslatef(delta.x, delta.y, delta.z);

        //transform component
        glRotatef(componentRotation.x, 1, 0, 0);
        glRotatef(componentRotation.y, 0, 1, 0);
        glRotatef(componentRotation.z, 0, 0, 1);

        glScalef(componentScale.x, componentScale.y, componentScale.z);

        //calculate normals for every vertex of the component
        glEnableClientState(GL_NORMAL_ARRAY);

        float[] normals = new float[component.getTriangles().length*3]; 

        for(int i = 0; i < component.getTriangles().length; i+=3) {
            Vector3f A = component.getTriangles()[i];
            Vector3f B = component.getTriangles()[i+1];
            Vector3f C = component.getTriangles()[i+2];
        
            Vector3f AB = B.sub(A);
            Vector3f AC = C.sub(A);
        
            Vector3f BA = A.sub(B);
            Vector3f BC = C.sub(B);
        
            Vector3f CA = A.sub(C);
            Vector3f CB = B.sub(C);
        
            normals[i] = AB.cross(AC).normalize().x;
            normals[i+1] = AB.cross(AC).normalize().y;
            normals[i+2] = AB.cross(AC).normalize().z;
        
            normals[i+3] = BA.cross(BC).normalize().x;
            normals[i+4] = BA.cross(BC).normalize().y;
            normals[i+5] = BA.cross(BC).normalize().z;
        
            normals[i+6] = CA.cross(CB).normalize().x;
            normals[i+7] = CA.cross(CB).normalize().y;
            normals[i+8] = CA.cross(CB).normalize().z;
        }

        float[] transformedNormals = component.transform(normals);

        glNormalPointer(GL_FLOAT, 0, java.nio.FloatBuffer.wrap(transformedNormals));

        component.render();
        glDisableClientState(GL_NORMAL_ARRAY);

        glPopMatrix();
        }
    public void renderLight(Light light) {
        glPushMatrix();
        
        Vector3f cameraRotation = camera.getRotation();
        Vector3f lightRotation = light.getRotation();
        
        for (Code renderListener : renderListeners) renderListener.doSomething();
        
        Vector3f delta = new Vector3f(light.getPosition()).sub(camera.getPosition());
        
        glRotatef(cameraRotation.x, 1, 0, 0);
        glRotatef(cameraRotation.y, 0, 1, 0);
        glRotatef(cameraRotation.z, 0, 0, 1);

        glTranslatef(delta.x, delta.y, delta.z);
    
        glRotatef(lightRotation.x, 1.0f, 0.0f, 0.0f);
        glRotatef(lightRotation.y, 0.0f, 1.0f, 0.0f);
        glRotatef(lightRotation.z, 0.0f, 0.0f, 1.0f);

        glEnable(light.getType());
        light.render();

        glPopMatrix();
    }
    public void render() {
        glfwMakeContextCurrent(window);
        if(!resizable) glfwSetWindowSize(window, width, height);
        org.lwjgl.opengl.GL.createCapabilities();

        glViewport(0, 0, width, height);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glEnable(GL_DEPTH_TEST);
        if(lighting) { 
            glEnable(GL_LIGHTING);
            glEnable(GL_COLOR_MATERIAL);
        }

        float[] glFrustrum = camera.getGlFrustum();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(glFrustrum[0], glFrustrum[1], glFrustrum[2], glFrustrum[3], glFrustrum[4], glFrustrum[5]);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        for(Code renderListener : renderListeners) renderListener.doSomething();
        for(Light light : lights) renderLight(light);
        for(Component component : components) renderComponent(component);

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
