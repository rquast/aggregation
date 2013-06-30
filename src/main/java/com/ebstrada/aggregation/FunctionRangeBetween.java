package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public class FunctionRangeBetween extends AbstractFunction {
    
    private ArrayList<Double> range;

    @Override
    public boolean match(Selection selectionValues) {
	if ( selectionValues.size() <= 0 ) {
	    return negated ? true: false;
	}
	for ( String str: selectionValues ) {
	    double value;
	    try {
		value = Double.parseDouble(str.trim());
	    } catch (Exception ex) {
		return negated ? true: false;
	    }
	    if ( value >= range.get(0) && value <= range.get(1) ) {
		continue;
	    } else {
		return negated ? true: false;
	    }
	}
	return negated ? false: true;
    }

    @Override
    public void parse(String conditionPartStr) throws InvalidRulePartException {
	conditionPartStr = conditionPartStr.trim();
	range = parseRangeFunctionParameter(conditionPartStr);
    }

}
