package org.integratedmodelling.kim.api;

import java.util.List;
import java.util.Map;
import org.eclipse.xtext.util.Pair;

public interface IKimNamespace extends IKimStatement {

    String getName();

    boolean isPrivate();

    List<IKimNamespace> getImported();

    long getTimestamp();

    IKimProject getProject();
    
    List<Pair<String,String>> getOwlImports();

    Map<String, Object> getSymbolTable();

    boolean isInactive();

    boolean isScenario();	
}
