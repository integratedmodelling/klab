package org.integratedmodelling.klab.hub.controllers.dto;

import org.integratedmodelling.klab.hub.enums.FilterOperationEnum;

/**
 * <h2>FilterCondition</h2>
 *
 * @author kristina.sanchez
 * <p>
 * Description: Filter Condition Class
 */

public class FilterCondition {

    private String field;
    private FilterOperationEnum operator;
    private Object value;
        
    
    public FilterCondition(String field, FilterOperationEnum operator, Object value) {
        super();
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public FilterOperationEnum getOperator() {
        return operator;
    }
    public void setOperator(FilterOperationEnum operator) {
        this.operator = operator;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    
    

}