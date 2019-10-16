package org.integratedmodelling.klab.rest;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Match.Type;

/**
 * ACHTUNG need equals to work in order to work inside the IDE. For now using
 * simple object equality which is OK for the purposes. If equals() is
 * overridden, ensure it's flawless and fast.
 * 
 * @author Ferd
 *
 */
public class SearchMatch {

	public static enum TokenClass {
		TOKEN,
		TEXT,
		INTEGER,
		DOUBLE,
		BOOLEAN,
		UNIT,
		CURRENCY
	};
	
	public static enum States {
		FORTHCOMING,
		EXPERIMENTAL,
		NEW,
		STABLE,
		BETA,
	}
	
	private String name;
	private String id;
	private String description;
	private int index;
	private IKimConcept.Type mainSemanticType;
	private Set<IKimConcept.Type> semanticType = EnumSet.noneOf(IKimConcept.Type.class);
	private IIndexingService.Match.Type matchType;
	private TokenClass nextTokenClass = TokenClass.TOKEN;
	private String state;
	private String extendedDescription;
	// these correspond to beginning and end of concept definition in parenthesis.
	private boolean openGroup;
	private boolean closeGroup;

	public SearchMatch() {
	}

	/**
	 * Add a pre-defined match, which is set in groups and modified only by user history and
	 * preferences.
	 * 
	 * @param matchName
	 * @param matchDescription
	 * @param semanticType
	 * @param matchType
	 */
	public SearchMatch(String matchId, String matchName, String matchDescription, IKimConcept.Type semanticType) {
		this(matchId, matchName, matchDescription, semanticType, null, null);
	}
	
	/**
	 * Add a pre-defined match, which is set in groups and modified only by user history and
	 * preferences.
	 * 
	 * @param matchId
	 * @param matchName
	 * @param matchDescription
	 * @param semanticType
	 * @param state
	 * @param extendedDescription
	 */
	public SearchMatch(String matchId, String matchName, String matchDescription, IKimConcept.Type semanticType, String state, String extendedDescription) {
		this.mainSemanticType = semanticType;
		if (semanticType != null) {
			this.semanticType.add(semanticType);
		}
		this.matchType = semanticType == null ? Type.SEPARATOR : Type.PRESET_OBSERVABLE;
		this.id = matchId;
		this.name = matchName;
		this.description = matchDescription;
		this.setState(state);
		this.setExtendedDescription(extendedDescription);
	}

	/**
	 * Name to show users
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Official fully qualified ID for the URN
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Description, one to several lines in length
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Semantic type of match (one of the fundamental observable types). Can only be
	 * null in non-semantic URN matches, not the common user case.
	 * 
	 * @param semanticType
	 */
	public Set<IKimConcept.Type> getSemanticType() {
		return semanticType;
	}

	public void setSemanticType(Set<IKimConcept.Type> semanticType) {
		this.semanticType = semanticType;
	}

	/**
	 * Type of match.
	 * 
	 * @return
	 */
	public IIndexingService.Match.Type getMatchType() {
		return matchType;
	}

	public void setMatchType(IIndexingService.Match.Type matchType) {
		this.matchType = matchType;
	}

	public IKimConcept.Type getMainSemanticType() {
		return mainSemanticType;
	}

	public void setMainSemanticType(IKimConcept.Type mainSemanticType) {
		this.mainSemanticType = mainSemanticType;
	}

	public boolean isOpenGroup() {
		return openGroup;
	}

	public void setOpenGroup(boolean openGroup) {
		this.openGroup = openGroup;
	}

	public boolean isCloseGroup() {
		return closeGroup;
	}

	public void setCloseGroup(boolean closeGroup) {
		this.closeGroup = closeGroup;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TokenClass getNextTokenClass() {
		return nextTokenClass;
	}

	public void setNextTokenClass(TokenClass nextTokenClass) {
		this.nextTokenClass = nextTokenClass;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExtendedDescription() {
		return extendedDescription;
	}

	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

}
