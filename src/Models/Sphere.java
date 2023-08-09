package Models;

import Engine.Drawable;

import static org.lwjgl.opengl.GL11.*;

public class Sphere implements Drawable {
    @Override
    public void draw() {
        float radius = 1.0f;
        int gradation = 100;
        final float PI = (float) Math.PI;
        float x, y, z, alpha, beta;
        for (alpha = 0.0f; alpha < Math.PI; alpha += PI / gradation) {
            glBegin(GL_TRIANGLE_STRIP);
            for (beta = 0.0f; beta < 2.01f * Math.PI; beta += PI / gradation) {
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha));
                z = (float) (radius * Math.cos(alpha));
                glTexCoord2f(beta / (2.0f * PI), alpha / PI);
                glVertex3f(x, y, z);
                x = (float) (radius * Math.cos(beta) * Math.sin(alpha + PI / gradation));
                y = (float) (radius * Math.sin(beta) * Math.sin(alpha + PI / gradation));
                z = (float) (radius * Math.cos(alpha + PI / gradation));
                glTexCoord2f(beta / (2.0f * PI), alpha / PI + 1.0f / gradation);
                glVertex3f(x, y, z);
            }
            glEnd();
        }
    }
}
