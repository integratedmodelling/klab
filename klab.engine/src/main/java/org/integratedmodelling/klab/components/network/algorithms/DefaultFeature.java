package org.integratedmodelling.klab.components.network.algorithms;

/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.locationtech.jts.geom.Geometry;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class DefaultFeature implements Feature {
	/*     */ protected Object id;
	/*     */ protected Geometry geom;
	/*     */ protected List<String> attrNames;
	/*     */ protected List attributes;

	/*     */
	/*     */ public DefaultFeature(Object id, Geometry geom) {
		/* 47 */ this(id, geom, (List) null, (List) null);
		/* 48 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public DefaultFeature(Object id, Geometry geom, List<String> attrNames,
			List<? extends Object> attributes) {
		/* 59 */ this.id = id;
		/* 60 */ this.geom = geom;
		/* 61 */ this.attrNames = attrNames == null ? Collections.EMPTY_LIST : attrNames;
		/* 62 */ this.attributes = attributes == null ? Collections.EMPTY_LIST : attributes;
		/* 63 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public DefaultFeature(Feature f) {
		/* 71 */ this(f, true);
		/* 72 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public DefaultFeature(Feature f, boolean dupData) {
		/* 80 */ this(f, dupData, false);
		/* 81 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public DefaultFeature(Feature f, boolean dupData, boolean dupNames) {
		/* 90 */ this.id = f.getId();
		/* 91 */ this.geom = f.getGeometry();
		/* 92 */ if (dupNames) {
			/* 93 */ this.attrNames = new ArrayList(f.getAttributeNames());
			/*     */ } else {
			/* 95 */ this.attrNames = f.getAttributeNames();
			/*     */ }
		/* 97 */ if (dupData) {
			/* 98 */ this.attributes = new ArrayList(this.attrNames.size());
			/* 99 */ for (int i = 0; i < this.attrNames.size(); ++i) {
				/* 100 */ this.attributes.add(f.getAttribute(i));
				/*     */ }
			/*     */ } else {
			/* 103 */ this.attributes = f.getAttributes();
			/*     */ }
		/* 105 */ }

	/*     */
	/*    */ public boolean equals(Object obj) {
		/* 37 */ if (obj == null)
			/* 38 */ return false;
		/*    */
		/* 40 */ if (getClass() != obj.getClass())
			/* 41 */ return false;
		/*    */
		/* 43 */ Feature other = (Feature) obj;
		/* 44 */ return (getId() == other.getId() || (getId() != null && getId().equals(other.getId())));
		/*    */ }

	/*    */
	/*    */
	/*    */ public int hashCode() {
		/* 49 */ int hash = 7;
		/* 50 */ hash = 61 * hash + ((getId() != null) ? getId().hashCode() : 0);
		/* 51 */ return hash;
		/*    */ }

	/*     */
	/*     */ public List<Object> getAttributes() {
		/* 109 */ return this.attributes;
		/*     */ }

	/*     */
	/*     */
	/*     */ public Object getAttribute(int ind) {
		/* 114 */ return this.attributes.get(ind);
		/*     */ }

	/*     */
	/*     */
	/*     */ public void setAttribute(int ind, Object val) {
		/* 119 */ this.attributes.set(ind, val);
		/* 120 */ }

	/*     */
	/*     */
	/*     */ public Object getAttribute(String name) {
		/* 124 */ int ind = this.attrNames.indexOf(name);
		/* 125 */ return ind > -1 ? this.attributes.get(ind) : null;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void setAttribute(String name, Object val) {
		/* 134 */ this.attributes.set(this.attrNames.indexOf(name), val);
		/* 135 */ }

	/*     */
	/*     */
	/*     */ public Class getAttributeType(int ind) {
		/* 139 */ return this.attributes.get(ind) == null ? Object.class : this.attributes.get(ind).getClass();
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public List<String> getAttributeNames() {
		/* 148 */ return this.attrNames;
		/*     */ }

	/*     */
	/*     */
	/*     */ public Geometry getGeometry() {
		/* 153 */ return this.geom;
		/*     */ }

	/*     */
	/*     */
	/*     */ public void setGeometry(Geometry g) {
		/* 158 */ this.geom = g;
		/* 159 */ }

	/*     */
	/*     */
	/*     */ public Object getId() {
		/* 163 */ return this.id;
		/*     */ }

	/*     */
	/*     */
	/*     */ public Class getIdType() {
		/* 168 */ return this.id.getClass();
		/*     */ }

	/*     */
	/*     */
	/*     */ public String toString() {
		/* 173 */ return this.id.toString();
		/*     */ }

	/*     */
	/*     */
	/*     */ public void addAttribute(String name, Object value) {
		/* 178 */ if (!this.attrNames.contains(name)) {
			/* 179 */ this.attrNames.add(name);
			/*     */ }
		/* 181 */ if (this.attributes.size() < this.attrNames.size()) {
			/* 182 */ this.attributes.add(value);
			/*     */ } else {
			/* 184 */ this.setAttribute(name, value);
			/*     */ }
		/* 186 */ }

	/*     */
	/*     */
	/*     */ public void removeAttribute(int ind) {
		/* 190 */ if (this.attrNames.size() == this.attributes.size()) {
			/* 191 */ this.attrNames.remove(ind);
			/*     */ }
		/*     */
		/* 194 */ this.attributes.remove(ind);
		/* 195 */ }

	/*     */
	/*     */ public static void addAttribute(String attr, Collection<? extends Feature> features,
			Object defaultVal) {
		/* 198 */ Iterator var3 = features.iterator();
		while (var3.hasNext()) {
			Feature f = (Feature) var3.next();
			/* 199 */ f.addAttribute(attr, defaultVal);
			/*     */ }
		/* 201 */ }

	/*     */
	/*     */ public static void removeAttribute(String attr, Collection<? extends Feature> features) {
		/* 204 */ int ind = ((Feature) features.iterator().next()).getAttributeNames().indexOf(attr);
		/* 205 */ Iterator var3 = features.iterator();
		while (var3.hasNext()) {
			Feature f = (Feature) var3.next();
			/* 206 */ f.removeAttribute(ind);
			/*     */ }
		/* 208 */ }
	/*     */ }
