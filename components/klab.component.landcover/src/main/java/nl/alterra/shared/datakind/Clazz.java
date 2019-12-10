package nl.alterra.shared.datakind;

public class Clazz {

	protected String caption;
	protected Integer value;

	public static class UndefinedClazz extends Clazz {

	}

	public String getValueAsString() {
		return value + "";
	}

	public String getCaption() {
		return caption;
	}

}
