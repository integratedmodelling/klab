package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Definition;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimMetadata;

public class KActorsAction extends KActorStatement implements IKActorsAction {

	private IKActorsBehavior behavior;
	private String name;
	List<IKimAnnotation> annotations = new ArrayList<>();
	KActorsCodeBlock code;

	public KActorsAction(Definition definition, IKActorsBehavior parent) {
		this.behavior = parent;
		this.name = definition.getName();
		this.code = new KActorsCodeBlock(definition.getBody().getLists());
	}

//	@Override
//	public int getFirstLine() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getLastLine() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getFirstCharOffset() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getLastCharOffset() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List<IKimAnnotation> getAnnotations() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDeprecation() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isDeprecated() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public String getSourceCode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isErrors() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isWarnings() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public IKimMetadata getMetadata() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
