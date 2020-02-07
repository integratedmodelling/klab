package org.integratedmodelling.klab.hub.network;

import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.sun.istack.NotNull;

@TypeAlias("DockerNode")
public class DockerNode extends DockerConfiguration {

	@NotNull
	@DBRef
	private MongoNode node;
	
	@NotNull
	private String image;
	
	private byte[] cert;
	
	
	private int jvmMax = 2048;
	
	private int maxSizeMB = 128;

	public MongoNode getNode() {
		return node;
	}

	public void setNode(MongoNode node) {
		this.node = node;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public byte[] getCert() {
		return cert;
	}

	public void setCert(byte[] cert) {
		this.cert = cert;
	}

	public int getJvmMax() {
		return jvmMax;
	}

	public void setJvmMax(int jvmMax) {
		this.jvmMax = jvmMax;
	}

	public int getmaxSizeMB() {
		return maxSizeMB;
	}

	public void setmaxSizeMB(int maxSizeMB) {
		this.maxSizeMB = maxSizeMB;
	}
	
}
