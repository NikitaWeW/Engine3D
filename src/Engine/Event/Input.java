package Engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    private static GLFWKeyCallback keyboard = new GLFWKeyCallback() {
        public void invoke(long window, int key, int scancode, int action, int mods) {
            keys[key] = (action != GLFW.GLFW_RELEASE);
        }};
    private static GLFWCursorPosCallback mouseMove = new GLFWCursorPosCallback() {
        public void invoke(long window, double xpos, double ypos) {
            mouseX = xpos;
            mouseY = ypos;
        }};
    private static GLFWMouseButtonCallback mouseButtons = new GLFWMouseButtonCallback() {
        public void invoke(long window, int button, int action, int mods) {
            buttons[button] = (action != GLFW.GLFW_RELEASE);
        }};
    private static GLFWScrollCallback mouseScroll = new GLFWScrollCallback() {
        public void invoke(long window, double offsetx, double offsety) {
            scrollX += offsetx;
            scrollY += offsety;
        }};

    private Input() {}

    public static boolean isKeyDown(int key) {
        return keys[key];
    }
    public static boolean isButtonDown(int button) {
        return buttons[button];
    }

    public static void destroy() {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

    public static double getMouseX() {
        return mouseX;
    }
    public static double getMouseY() {
        return mouseY;
    }
    public static double getScrollX() {
        return scrollX;
    }
    public static double getScrollY() {
        return scrollY;
    }
    public static GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }
    public static GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }
    public static GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }
    public static GLFWScrollCallback getMouseScrollCallback() {
        return mouseScroll;
    }
}
