import Engine.*;
import Models.*;

import org.joml.*;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static void main(String[] args) throws Exception {
        GLFW.glfwInit();

        System.out.println("Hello world!");
        Window windows = new Window(800, 600, "toaster");

        Cube cube = new Cube();
        cube.getPos().z = -10;
        windows.addComponent(cube);

        Cube cube2 = new Cube();
        cube2.getPos().x = 2.0f;
        cube2.getPos().y = 2.0f;
        cube2.getPos().z = -10;
        //cube2.getRotate().z = 45f;
        windows.addComponent(cube2);

        //System.out.println(cube.checkColision(cube2));
        Vector3f A = new Vector3f(1, 1, 0.0f);
        Vector3f B = new Vector3f(4, 5, 0.0f);
        Vector3f C = new Vector3f(4, 1, 0.0f);

        Vector3f P = new Vector3f(2.5f, 2.0f, -1.0f);
        Vector3f Q = new Vector3f(3.5f, 2.0f, -1.0f);
        Vector3f R = new Vector3f(3.5f, 2.0f, 1.0f);//true 

        A = new Vector3f(1.0f, 2.0f, 0.0f);
        B = new Vector3f(2.0f, 4.0f, 0.0f);
        C = new Vector3f(2.0f, 2.0f, 0.0f);

        P = new Vector3f(3.0f, 1.0f, 1.0f);
        Q = new Vector3f(5.0f, 3.0f, 1.0f);
        R = new Vector3f(5.0f, 1.0f, 1.0f);//false */

        System.out.println(Component.triTriIntersection(A, B, C, P, Q, R));
    }
}