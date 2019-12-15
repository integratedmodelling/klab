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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clazz other = (Clazz) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
