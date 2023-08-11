package Engine;

import org.joml.*;

import java.lang.Math;

public class Camera {
    //private Vector3f position = new Vector3f(0, 0, 0);
    //private Vector3f rotation = new Vector3f(0, 0, 0);
    private Position position = new Position(0, 0, 0);

    public Position getPosition() {
        return position;
    }
}