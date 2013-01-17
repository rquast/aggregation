package com.ebstrada.aggregation;

public class Value implements IConditionPart {
    
    private String value;
    
    private boolean negated = false;

    public String getValue() {
        return value;
    }

    public void parse(String conditionPartStr) {
	conditionPartStr = conditionPartStr.trim();
	if (conditionPartStr.startsWith("!")) {
	    this.negated = true;
	    this.value = conditionPartStr.replaceFirst("!", "");
	} else {
	    this.value = conditionPartStr;
	}
    }

    @Override
    public boolean isNegated() {
	return this.negated;
    }

    @Override
    public void setNegated(boolean negated) {
	this.negated = negated;
    }

    @Override
    public boolean match(Selection selectionValues) {
	for (String selectionValue: selectionValues) {
	    if (value.trim().toLowerCase() == selectionValue.trim()) {
		if ( negated ) {
		    return false;
		} else {
		    return true;
		}
	    }
	}
	return false;
    }

}
