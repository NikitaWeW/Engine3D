package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Matrix4f viewMatrix = new Matrix4f();

    private float moveSpeed = 0.1f;
    private float rotationSpeed = 0.5f;

    public void moveForward() {
        position.add(getForward().mul(moveSpeed));
    }

    public void moveBackward() {
        position.add(getForward().negate().mul(moveSpeed));
    }

    public void moveLeft() {
        position.add(getRight().negate().mul(moveSpeed));
    }

    public void moveRight() {
        position.add(getRight().mul(moveSpeed));
    }

    public void moveUp() {
        position.add(getUp().mul(moveSpeed));
    }

    public void moveDown() {
        position.add(getUp().negate().mul(moveSpeed));
    }

    public void rotate(float pitch, float yaw, float roll) {
        rotation.add(pitch * rotationSpeed, yaw * rotationSpeed, roll * rotationSpeed);
    }

    public void updateViewMatrix() {
        viewMatrix.identity();
        viewMatrix.rotateX((float) Math.toRadians(rotation.x));
        viewMatrix.rotateY((float) Math.toRadians(rotation.y));
        viewMatrix.rotateZ((float) Math.toRadians(rotation.z));
        viewMatrix.translate(-position.x, -position.y, -position.z);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    private Vector3f getForward() {
        return new Vector3f(
                (float) Math.sin(Math.toRadians(rotation.y)),
                0,
                (float) -Math.cos(Math.toRadians(rotation.y))
        ).normalize();
    }

    private Vector3f getRight() {
        return getForward().cross(new Vector3f(0, 1, 0)).normalize();
    }

    private Vector3f getUp() {
        return getRight().cross(getForward()).normalize();
    }

    public void onKeyPress(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_W:
                moveForward();
                break;
            case GLFW.GLFW_KEY_S:
                moveBackward();
                break;
            case GLFW.GLFW_KEY_A:
                moveLeft();
                break;
            case GLFW.GLFW_KEY_D:
                moveRight();
                break;
            case GLFW.GLFW_KEY_SPACE:
                moveUp();
                break;
            case GLFW.GLFW_KEY_LEFT_SHIFT:
                moveDown();
                break;
        }
    }

    public void onMouseMove(double xpos, double ypos) {
        // Реализуйте логику для вращения камеры по оси X и Y при движении мыши
    }
}
