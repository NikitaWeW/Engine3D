import Engine.Window;
import Models.*;
import org.lwjgl.glfw.GLFW;
import org.joml.Vector3f;

public class Main {
    public static boolean b = true;
    public static void main(String[] args) {
        GLFW.glfwInit();

        System.out.println("Hello world!");
        Window windows = new Window(800, 600, "toaster");
        windows.addListener(event -> Main.b = false);

        Cube cube = new Cube();
        cube.getRotate().y = 45.0f;
        windows.addComponent(cube);

        Sphere sphere = new Sphere();
        sphere.getPos().x = 1.5f;
        windows.addComponent(sphere);

        System.out.println(cube.checkColision(sphere));
    }
}