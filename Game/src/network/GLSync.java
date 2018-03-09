package network;

import java.time.Instant;
import java.util.Date;

import engine.GLComponent;
import engine.GLTransform;

public class GLSync extends GLComponent {
	public GLData data = null;
	public long previous = 1;
	public int syncTime = 1000;
	public boolean sync = false;
	public boolean syncNow = false;
	public boolean smartSync = false;

	public GLSync(network.GLData glData) {
		this.data = glData;
		this.setName("sync");
	}

	public void Run() {
		if (!smartSync) {
			long now = Instant.now().toEpochMilli();
			long seconds = (now - previous);
			if (seconds > syncTime) {
				previous = now;
				sync = true;
			}
			if (sync) {
				syncNow = true;
			}
		}
		Sync();
		sync = false;
	}

	public void Sync() {
		if (data.protocol == GLProtocol.TRANSFORM) {
			GLTransform transform = (GLTransform) this.getObject().getComponent("transform");
			if (transform != null) {
				GLSyncTransform syncTransform = (GLSyncTransform) data;

				if ((syncTransform.position.x != transform.getPosition().x
						|| syncTransform.position.y != transform.getPosition().y) && smartSync) {
					syncNow = true;
				}

				if (syncNow) {
					syncTransform.position = transform.getPosition();
				}
			}
		}
	}

	public boolean syncNow() {
		boolean syncTemp = this.syncNow;
		syncNow = false;
		return syncTemp;
	}
}
