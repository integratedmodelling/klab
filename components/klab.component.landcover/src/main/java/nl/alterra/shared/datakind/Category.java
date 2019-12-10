package nl.alterra.shared.datakind;

public class Category extends Clazz {

	Symbol symbol = new Symbol();
	Integer value;
	
	public boolean includes(Number value) {
		// ? also check if null equality should return true
		return this.value != null && value.intValue() == this.value;
	}

	public void setValue(Integer code) {
		this.value = code;
	}

	public Integer getValue() {
		return value;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}

}
