package com.ebstrada.aggregation;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public class FunctionStrLenEq extends AbstractFunction {
    
    private String conditionName;
    
    private int stringLength;

    public void parse(String conditionPartStr) throws InvalidRulePartException {
	conditionPartStr = conditionPartStr.trim();
	this.conditionName = conditionPartStr.substring(0, conditionPartStr.indexOf('('));
	this.stringLength = parseIntFunctionParameter(conditionPartStr);
    }

    @Override
    public boolean match(Selection selectionValues) {
	if ( selectionValues.size() != 1 ) {
	    return negated ? true: false;
	}
	if (stringLength == selectionValues.get(0).length()) {
	    return negated ? false : true;
	} else {
	    return negated ? true: false;
	}
    }

}
