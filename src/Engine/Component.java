package Engine;

import Engine.Event.Event;

public class Component extends Event {
    private Position pos = new Position();
    private Drawable drawable;
    private boolean anchored = true;
    private boolean colision = true;

    public Component(Drawable drawable) {
        this.drawable = drawable;
    }

    public Position pos() {
        return pos;
    }
    public void pos(Position pos) {
        this.pos = pos;
    }

    public Drawable drawable() {
        return drawable;
    }
    public void drawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isAnchored() {
        return anchored;
    }
    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    public boolean isColision() {
        return colision;
    }
    public void setColision(boolean colision) {
        this.colision = colision;
    }
}
