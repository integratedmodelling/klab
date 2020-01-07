package org.integratedmodelling.klab;

import org.integratedmodelling.klab.common.Urns;

/**
 * Simple helper to decompose a URN into its constituents and access them with
 * proper semantics.
 * 
 * URN is formatted as <node name>:<originator>:<namespace>:<resource id>
 * 
 * @author Ferd
 *
 */
public class Urn {

	private String urn;
	private String[] tokens;

	/**
	 * Pass a valid URN string. For now does no validation.
	 * 
	 * @param urn
	 */
	public Urn(String urn) {
		if (urn.startsWith(Urns.KLAB_URN_PREFIX)) {
			urn = urn.substring(Urns.KLAB_URN_PREFIX.length());
		}
		this.urn = urn;
		this.tokens = urn.split(":");
	}

	/**
	 * Node name, mandatory in all URNs. In universal ones it will be "klab". In
	 * local ones, it will be "local".
	 * 
	 * @return the node name.
	 */
	public String getNodeName() {
		return tokens[0];
	}

	/**
	 * Whether the URN should be processed by the same engine that generates it.
	 * 
	 * @return true if local
	 */
	public boolean isLocal() {
		return getNodeName().equals("local");
	}

	/**
	 * Whether the URN can be processed by any node. In this case, the URN has no
	 * attached data and the catalog name is the ID of the adapter that will process
	 * it. If we don't have the adapter, we will choose a node among those that do,
	 * using the load factor or some other criterion.
	 * 
	 * @return true if universal.
	 */
	public boolean isUniversal() {
		return getNodeName().equals("klab");
	}

	/**
	 * Return the catalog for the resource. In local resources, this is the
	 * originator ID. In universal resources, this is the adapter ID. Never null.
	 * 
	 * @return the originator
	 */
	public String getCatalog() {
		return tokens[1];
	}

	/**
	 * Return the namespace of the resource.
	 */
	public String getNamespace() {
		return tokens.length > 2 ? tokens[2] : null;
	}

	/**
	 * Return the resource ID. Never null.
	 * 
	 * @return the resource id.
	 */
	public String getResourceId() {
		return tokens.length > 3 ? tokens[3] : null;
	}

	/**
	 * Return the version, if any.
	 * 
	 * @return
	 */
	public Version getVersion() {
		return tokens.length > 4 ? new Version(tokens[4]) : null;
	}

	/**
	 * Unmodified URN string.
	 * 
	 * @return the unmodified URN.
	 */
	public String getUrn() {
		return urn;
	}

	@Override
	public String toString() {
		return urn;
	}

}