package Engine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class GLAudio extends GLComponent {

	private String filename = "";
	private float volume = 1;
	private IntBuffer buffer = BufferUtils.createIntBuffer(1);
	private IntBuffer source = BufferUtils.createIntBuffer(1);
	private FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f })
			.rewind();
	private FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f })
			.rewind();
	private FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3)
			.put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	private FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3)
			.put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	private FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6)
			.put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f }).rewind();

	public GLAudio() {
		this.setName("audio");
		this.setupAL();
	}

	public GLAudio(String filename) {
		this.setName("audio");
		this.setFilename(filename);
		this.setupAL();
	}

	private void setupAL() {
		try {
			AL.create();
		} catch (LWJGLException le) {
			le.printStackTrace();
			return;
		}
		AL10.alGetError();
		if (loadFile(this.filename) == AL10.AL_FALSE) {
			System.out.println("Error loading data.");
			return;
		}
		setListenerValues();
	}

	public int loadFile(String newFilename) {
		AL10.alGenBuffers(buffer);
		WaveData waveFile = null;
		try {
			waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(newFilename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (waveFile != null) {
			AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
			waveFile.dispose();
			AL10.alGenSources(source);
			AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
			AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
			AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
			AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
			AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);
		}
		if (AL10.alGetError() != AL10.AL_NO_ERROR) {
			return AL10.AL_FALSE;
		} else {
			return AL10.AL_TRUE;
		}
	}

	private void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}

	public void Destroy() {
		AL10.alDeleteBuffers(buffer);
		AL10.alDeleteSources(source);
		AL.destroy();
	}

	public boolean isPlayingSound() {
		return AL10.alGetSourcei(source.get(0), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void play() {
		if (!this.isPlayingSound()) {
			AL10.alSourcef(source.get(0), AL10.AL_GAIN, this.volume);
			AL10.alSourcePlay(source.get(0));
		}
	}

	public void stop() {
		if (this.isPlayingSound()) {
			AL10.alSourceStop(source.get(0));
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}
}