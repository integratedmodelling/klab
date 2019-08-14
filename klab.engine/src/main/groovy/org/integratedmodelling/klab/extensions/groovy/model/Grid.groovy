package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.observations.scale.space.IGrid

class Grid {

	IGrid grid;
	Binding binding;
	
	public Grid(IGrid grid, Binding binding) {
		this.binding = binding;
		this.grid = grid;
	}
	
	def getCellArea() {
		return grid.getCellArea(true);
	}
	
	def getCellWidth() {
		return grid.getCellWidth();
	}

	def getCellHeight() {
		return grid.getCellHeight();
	}
	
	def getCellCount() {
		return grid.getCellCount();
	}
	
	def getXCells() {
		return grid.getXCells();
	}
	
	def getYCells() {
		return grid.getXCells();
	}
	
}
