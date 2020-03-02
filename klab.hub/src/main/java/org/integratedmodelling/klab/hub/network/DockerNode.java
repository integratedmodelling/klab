package org.integratedmodelling.klab.hub.network;

import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.sun.istack.NotNull;

@TypeAlias("Node")
public class DockerNode extends DockerConfiguration {

	@NotNull
	@DBRef
	private MongoNode node;
	
	private byte[] cert;

	public MongoNode getNode() {
		return node;
	}

	public void setNode(MongoNode node) {
		this.node = node;
	}
	
	public byte[] getCert() {
		return cert;
	}

	public void setCert(byte[] cert) {
		this.cert = cert;
	}
	
}
