package nl.alterra.shared.datakind;

import java.util.LinkedHashSet;
import java.util.Set;

public class DataKind implements IDataKind {

	Set<Clazz> categories = new LinkedHashSet<>();
	IDataKind.LevelOfMeasurement levelOfMeasurement;
	Type type;
	String caption = "Classification"; // a ver
	
	public Set<Clazz> getClasses() {
		return categories;
	}

	public IDataKind.LevelOfMeasurement getLevelOfMeasurement() {
		return levelOfMeasurement;
	}

	public void setType(Type qualitative) {
		this.type = qualitative;
	}

	public Category addNew() {
		Category ret = new Category();
		categories.add(ret);
		return ret;
	}

	public int getClassCount() {
		return categories.size();
	}

	public Clazz findByValue(Object adminUnitValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public Type getType() {
		return type;
	}

	public String getCaption() {
		return caption;
	}

}
