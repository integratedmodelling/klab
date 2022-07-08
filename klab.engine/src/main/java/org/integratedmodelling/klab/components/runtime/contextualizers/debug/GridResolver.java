package org.integratedmodelling.klab.components.runtime.contextualizers.debug;

import java.util.stream.StreamSupport;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class GridResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	enum Output {
		OFFSET,
		ROW,
		COLUMN
	}
	
	private Output output = Output.OFFSET;
	private Grid grid;
	
	public GridResolver() {
	}

	private GridResolver(IParameters<String> parameters, IContextualizationScope context) {
		if (parameters.contains("output")) {
			this.output = Output.valueOf(parameters.get("output", String.class).toUpperCase());
		}
	}


	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		this.grid = Space.extractGrid(target);

		StreamSupport.stream(grid.spliterator(context.getMonitor()), true).forEach((locator) -> {
//			 for (Cell locator : grid) {

			// if (context.getMonitor().isInterrupted()) {
			// break;
			// }

			double value = Double.NaN;
			
			switch (output) {
			case COLUMN:
				value = ((Cell)locator).getY();
				break;
			case OFFSET:
				value = ((Cell)locator).getOffsetInGrid();
				break;
			case ROW:
				value = ((Cell)locator).getX();
				break;
			default:
				break;
			
			}
			
			target.set(locator, value);

			// long cells = adder.longValue();
			// if (cells == 0 || (cells % 10000) == 0) {
			// context.getMonitor().info(adder.sum() + " cells done...");
			// }
			//
			// adder.add(1);

		});
//		 }

		return target;
	}

	@Override
	public GridResolver eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new GridResolver(Parameters.create(parameters), context);
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

}
