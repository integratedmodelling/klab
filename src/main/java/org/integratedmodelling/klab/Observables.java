package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IObservableService;

public enum Observables implements IObservableService {

	INSTANCE;

	@Override
	public Builder declare(IConcept main) {
		return new BuilderImpl(main);
	}

	class BuilderImpl implements Builder {
		
		private IConcept main;

		public BuilderImpl(IConcept main) {
			this.main = main;
		}

		/**
		 * 
		 * @param concept
		 * @return
		 */
		public Builder of(IConcept concept) {
			return this;
		}
		
		/**
		 * 
		 * @param concept
		 * @return
		 */
		public Builder within(IConcept concept) {
			return this;
		}
		
		/**
		 * Traits, roles or 'with' compresent
		 * @param concept
		 * @return
		 */
		public Builder with(IConcept... concept) {
			return this;
		}
		
		public Builder motivation(IConcept concept) {
			return this;
		}
		
		public Builder to(IConcept concept) {
			return this;
		}
		
		public Builder from(IConcept concept) {
			return this;
		}
		
		public IConcept build() {
			return null;
		}
		
	}
	

}
