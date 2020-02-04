package org.integratedmodelling.klab.hub.network;

import java.util.Properties;

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
	
	private Properties properties;
	
	private int jvmMax = 2048;
	
	private int port = 8287;
	
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

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public int getJvmMax() {
		return jvmMax;
	}

	public void setJvmMax(int jvmMax) {
		this.jvmMax = jvmMax;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getmaxSizeMB() {
		return maxSizeMB;
	}

	public void setmaxSizeMB(int maxSizeMB) {
		this.maxSizeMB = maxSizeMB;
	}
	
}
