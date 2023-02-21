///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
// * authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
// * collaborative, integrated development of interoperable data and model components. For details,
// * see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms of the
// * Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any warranty; without
// * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
// * General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this program; if
// * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
// * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import org.integratedmodelling.klab.Namespaces;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IKnowledge;
//import org.integratedmodelling.klab.api.model.INamespace;
//
//import groovy.lang.GroovyObjectSupport;
//import groovy.transform.CompileStatic;
//
///**
// * 
// * @author ferdinando.villa
// *
// */
//@CompileStatic
//public abstract class Knowledge extends GroovyObjectSupport implements IKnowledge {
//
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Knowledge ? toString().equals(obj.toString()) : false;
//    }
//
//    @Override
//    public int hashCode() {
//        return toString().hashCode();
//    }
//
//    @Override
//    public IConcept getDomain() {
//        INamespace ns = Namespaces.INSTANCE.getNamespace(getNamespace());
//        return ns == null ? null : ns.getDomain();
//    }
//
//}
