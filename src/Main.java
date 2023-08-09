import Engine.Component;
import Engine.Window;
import Models.*;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Window windows = new Window(800, 600, "basics");
        windows.addListener(event -> GLFW.glfwTerminate());
        
        Component cube = new Component(new Cube());
        cube.pos().angleX(45);
        cube.pos().angleY(45);
        windows.addComponent(cube);

        //Component sphere = new Component(new Sphere());
        //sphere.pos().angleX(45.0f);
        //sphere.pos().angleY(45.0f);
        //sphere.pos().X(-10);
        //windows.addComponent(cube);
    }
}