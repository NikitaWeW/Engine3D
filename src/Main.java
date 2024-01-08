import Engine.*;
import Models.*;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static float distance = 10;
    public static float i;
    public static float j = 0.01f;

    public static void main(String[] args) throws Exception {
        org.lwjgl.glfw.GLFW.glfwInit();

        System.out.println("Hello world!");
        Window windows = new Window(2000, 1000, "");

        Component component1 = new Cube();
        Component component2 = new Cube();

        windows.addRenderListener(() -> {
                glClearColor(0.7f, 0.9f, 1f, 0f);

                component1.getPosition().add(component1.getPosition().x >10 ? -20 : 0.05f, 0, 0);

                glColor3f(1, 0, 0);
                if(component1.checkColision(component2)) glColor3f(0, 1, 0);

                try { Thread.sleep(1); } 
                catch (InterruptedException e) { e.printStackTrace(); }
            });
        windows
            .addCloseListener(() -> glfwTerminate())
            .resizable(true)
            .addComponent(component1)
            .addComponent(component2)
            .getCamera()
                .setPosition(0, 0, distance)
                .setRotation(0, 0, 0);
    }
}
