/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.auth;

import java.util.Map;
import java.util.Set;

/**
 * A certificate defines a 'root' identity: a {@link IPartnerIdentity}, {@link INodeIdentity} or
 * {@link IUserIdentity}.
 * <p>
 * If no certificate file is found, implementations can create a default certificate with anonymous
 * identity, linked to a preferred worldview and enabling basic, local operations with no access to
 * the network. The same certificate may be used for testing.
 * <p>
 * When a certificate begins its lifetime, it should be already authenticated and its validity
 * should have been checked with {@link #isValid()} immediately after creation.
 * <p>
 *
 * @author ferdinando villa
 * @version $Id: $Id
 */
public interface ICertificate {

    public static enum Level {

        /**
         * Anonymous certificate can only authenticate with a locally running hub.
         */
        ANONYMOUS,
        /**
         * Legacy certificate can only be user level
         */
        LEGACY,
        /**
         * Individual user owns the engine (default).
         */
        USER,
        /**
         * Institutional engine. Can be linked to security settings.
         */
        INSTITUTIONAL,

        /**
         * Test certificate. Only free to connect to localhost test networks.
         */
        TEST
    }

    public static enum Type {
        /**
         * This certificate authorizes an engine, at one of the levels defined by {@link Level}.
         */
        ENGINE,
        /**
         * This certificate authorizes a node. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         * TODO substituted by LEGACY_NODE
         */
        NODE,
        /**
         * This certificate authorizes a node. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        LEGACY_NODE,
        /**
         * This certificate authorizes a Reasoner service. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        REASONER,
        /**
         * This certificate authorizes a Resolver service. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        RESOLVER,
        /**
         * This certificate authorizes a Resources service. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        RESOURCES,
        /**
         * This certificate authorizes a Runtime service. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        RUNTIME,
        /**
         * This certificate authorizes a hub. The only allowed level is {@link Level#INSTITUTIONAL}
         * or {@link Level#TEST}.
         */
        HUB,
        /**
         * This certificate authorizes a lever. The only allowed level is
         * {@link Level#INSTITUTIONAL} or {@link Level#TEST}.
         */
        LEVER

    }

    public static final String DEFAULT_ENGINE_CERTIFICATE_FILENAME = "klab.cert";
    public static final String DEFAULT_NODE_CERTIFICATE_FILENAME = "node.cert";
    public static final String DEFAULT_SERICE_CERTIFICATE_FILENAME = "service.cert";
    public static final String DEFAULT_SEMANTIC_SERVER_CERTIFICATE_FILENAME = "semantic.cert";
    public static final String DEFAULT_HUB_CERTIFICATE_FILENAME = "hub.cert";
    public static final String DEFAULT_LEVER_CERTIFICATE_FILENAME = "lever.cert";

    /*
     * Keys for user properties in certificates or for set operations.
     */
    public static final String KEY_EMAIL = "klab.user.email";
    public static final String KEY_USERNAME = "klab.username";
    public static final String KEY_AGREEMENT = "klab.agreement";
    public static final String KEY_NODENAME = "klab.nodename";
    public static final String KEY_SERVICENAME = "klab.servicename";
    public static final String KEY_LEVERNAME = "klab.levername";
    public static final String KEY_HUBNAME = "klab.hubname";
    public static final String KEY_URL = "klab.url";
    public static final String KEY_SIGNATURE = "klab.signature";
    public static final String KEY_PARTNER_HUB = "klab.partner.hub";
    public static final String KEY_PARTNER_NAME = "klab.partner.name";
    public static final String KEY_PARTNER_EMAIL = "klab.partner.email";
    public static final String KEY_CERTIFICATE = "klab.certificate";
    public static final String KEY_CERTIFICATE_TYPE = "klab.certificate.type";
    public static final String KEY_CERTIFICATE_LEVEL = "klab.certificate.level";

    /**
     * Create the worldview workspace for this identity and return it (unloaded and not
     * initialized). Only a hub may return null, signaling that it will handle nodes and engines
     * committed to different worldviews.
     *
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorldview} object. Can only be
     *         null in hub certificates.
     */
    String getWorldview();

    /**
     * The type of this certificate.
     * 
     * @return the type
     */
    Type getType();

    /**
     * The level of this certificate. Will mandatorily return {@link Level#INSTITUTIONAL} unless in
     * an engine certificate or in a test certificate.
     * 
     * @return the level
     */
    Level getLevel();

    /**
     * Validity may depend on expiration date and possibly upstream conditions after authentication,
     * such as having had a certificate invalidated by an administrator.
     *
     * If this returns true, the certificate exists, is readable and properly encrypted, and is
     * current.
     * 
     * If this returns false, {@link #getInvalidityCause} will contain the reason why.
     * 
     * @return true if everything is OK.
     */
    boolean isValid();

    /**
     * Returns why {@link #isValid()} returned false. Undefined otherwise.
     * 
     * @return a description of the cause for invalidity
     */
    String getInvalidityCause();

    /**
     * Return the named property on a valid certificate.
     * 
     * @param property
     * @return the value of the property, or null.
     */
    String getProperty(String property);

    /**
     * Each key in the map is the git URL of a worldview project, mapping to the set of group IDs
     * that have access to it.
     * 
     * @return
     */
    Map<String, Set<String>> getWorldviewRepositories(String worldview);
}
