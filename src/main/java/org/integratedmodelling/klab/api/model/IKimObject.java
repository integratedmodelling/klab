package org.integratedmodelling.klab.api.model;

import java.io.Serializable;

import org.integratedmodelling.kim.api.IKimStatement;

/**
 * A k.IM object is anything that was stated in k.IM. As a result, it can produce
 * the k.IM statement that corresponds to it.
 * 
 * @author Ferd
 *
 */
public interface IKimObject extends Serializable {
    
    IKimStatement getStatement();

}
