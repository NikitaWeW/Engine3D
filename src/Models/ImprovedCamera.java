package Models;

public class ImprovedCamera extends Engine.Camera {
    public float speed = 1.0f;

    public static final int FORWARD = 1;
    public static final int BACKWARD = -1;
    public static final int LEFT = 2;
    public static final int RIGHT = -2;
    public static final int UP = 3;
    public static final int DOWN = -3;

    public ImprovedCamera move(int direction) {
        float x = (float) Math.sin(Math.toRadians((double) getRotation().y)) * speed;
		float z = (float) Math.cos(Math.toRadians((double) getRotation().y)) * speed;
        switch(direction) {
            case FORWARD:
                getPosition().add(-x, 0, -z);
                break;

            case BACKWARD:
                getPosition().sub(-x, 0, -z);
                break;

            case LEFT:
                getPosition().add(-z, 0, x);
                break;
                
            case RIGHT:
                getPosition().sub(-z, 0, x);
                break;

            case UP:
                getPosition().add(0, -speed, 0);
                break;
                
            case DOWN:
                getPosition().sub(0, -speed, 0);
                break;
        }
        return this;
    }
}
