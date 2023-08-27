package org.integratedmodelling.kim.api;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IObservable.ResolutionException;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

public interface IKimObservable extends IKimStatement {

    /**
     * @return the main concept
     */
    IKimConcept getMain();

    /**
     * @return the range
     */
    Range getRange();

    /**
     * @return the unit
     */
    String getUnit();

    /**
     * @return the currency
     */
    String getCurrency();

    /**
     * This will be null unless the k.IM definition has a 'named' clause. The resulting observables
     * will always have a name, so this is the only guaranteed way to obtain the user-defined formal
     * name.
     * 
     * @return the 'named' name
     */
    String getFormalName();

    /**
     * @return the literal value
     */
    Object getValue();

    /**
     * The default value if one is given. The optional state and resolution triggers are affected.
     * 
     * @return
     */
    Object getDefaultValue();

    /**
     * Resolution exceptions linked to the use of a stated default value.
     * 
     * @return
     */
    Collection<ResolutionException> getResolutionExceptions();

    /**
     * Value operators with their operands.
     * 
     * @return
     */
    List<Pair<ValueOperator, Object>> getValueOperators();

    /**
     * If the observable specification had an identifier (rather than a literal value) before an
     * 'as' clause introducing the semantics, this will return true and the {@link #getValue()}
     * method will return a string with the identifier's value. The interpretation of the identifier
     * is context-dependent as it may refer to a value previously defined in a 'define' statement,
     * or to an attribute to be looked up in a referenced resource.
     * 
     * @return true if identified by an attribute to be resolved
     */
    boolean hasAttributeIdentifier();

    /**
     * True if the 'optional' clause has been passed.
     * 
     * @return true if optional
     */
    boolean isOptional();

    /**
     * If the observable is tied to a predefined model, return the model fully qualified name here.
     * 
     * @return
     */
    String getModelReference();

    /**
     * If this returns anything other than null, we are looking at the observable of a non-semantic
     * model, and implementations will need to handle this properly, for example creating a
     * recognizable, unique concept of the returned type using the name set into modelReference, or
     * using completely independent logics.
     * 
     * @return
     */
    IArtifact.Type getNonSemanticType();

    /**
     * The canonical definition
     * 
     * @return
     */
    String getDefinition();

    /**
     * Return a descriptive name for this concept suitable for use as the name of a k.IM object. If
     * the concept comes from an observable specification with a 'named' clause, return the supplied
     * name instead.
     * 
     * @return the name for k.IM code
     */
    String getCodeName();

    /**
     * Generic observables have "any" prepended and specify the class including their children even
     * if the observable is concrete.
     * 
     * @deprecated now this is in concepts, the observable should know if it's a pattern and which
     * elements contribute to it.
     * 
     * @return
     */
    boolean isGeneric();

    /**
     * Globalized observables have "all" prepended and are used in classifiers and special
     * classification or expansion situations (not in actual semantics) to indicate that all levels
     * of the hierarchy should be considered.
     *
     * @deprecated now this is in concepts, the observable should know if it's a pattern and which
     * elements contribute to it.
     * 
     * @return
     */
    boolean isGlobal();

    /**
     * Exclusive observables have 'only' prepended and only match themselves, never a subclass, when
     * used for queries.
     *
     * @deprecated now this is in concepts, the observable should know if it's a pattern and which
     * elements contribute to it.
     *
     * @return
     */
    boolean isExclusive();

}
