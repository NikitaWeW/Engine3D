package Engine;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.openal.*;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Sound {
    private int sourceId;
    private String path; //only .wav
    private Vector3f position = new Vector3f();

    public Sound(String path) {
        var openALDevice = alcOpenDevice((ByteBuffer) null);
        if (openALDevice == NULL) {
            throw new IllegalStateException("Failed to open OpenAL device");
        }
        ALCCapabilities alcCapabilities = ALC.createCapabilities(openALDevice);
        var openALContext = alcCreateContext(openALDevice, (IntBuffer) null);
        if (openALContext == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context");
        }
        alcMakeContextCurrent(openALContext);
        AL.createCapabilities(alcCapabilities);
        sourceId = org.lwjgl.openal.AL10.alGenSources();
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    public Vector3f getPosition() {
        return position;
    }
    public int getID() {
        return sourceId;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
