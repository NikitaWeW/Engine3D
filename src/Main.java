import Engine.*;
import Models.*;

import org.joml.*;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static void main(String[] args) throws Exception {
        GLFW.glfwInit();

        System.out.println("Hello world!");
        Window windows = new Window(800, 600, "toaster");
        windows.setLighting(true);

        Cube cube = new Cube();
        cube.getRotate().y = 45.0f;
        cube.getRotate().x = 45.0f;
        cube.getPos().z = -10;
        windows.addComponent(cube);

        Sphere sphere = new Sphere();
        sphere.getPos().x = 4.0f;
        sphere.getPos().z = -10;
        windows.addComponent(sphere);

        PointLight light = new PointLight();
        light.setPos(new Vector3f(4.0f, 0.0f, -5.0f));
        light.setIntensity(new Vector4f(10, 10, 10, 0));
        light.setAttenuationParameters(new Vector3f(1, 0, 0));
        windows.addLight(light);

        windows.addListener(event -> {
            windows.cam().getRotation().add(new Vector3f(0, 0.01f, 0));
        });
    }
}