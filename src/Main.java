import Engine.Window;
import Models.*;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Window windows = new Window(800, 600, "basics");
        windows.addListener(event -> GLFW.glfwTerminate());

        Cube cube = new Cube();
        cube.pos().angleY(45f);
        windows.addComponent(cube);

        Sphere sphere = new Sphere();
        sphere.pos().X(1.5f);
        windows.addComponent(sphere);

    }
}