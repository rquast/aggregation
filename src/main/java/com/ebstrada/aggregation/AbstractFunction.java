package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public abstract class AbstractFunction implements IConditionPart {
    
    protected boolean negated = false;
    
    @Override
    public boolean isNegated() {
	return this.negated;
    }
    
    public int parseIntFunctionParameter(String conditionName) throws InvalidRulePartException {
	String intStr = conditionName.substring(conditionName.indexOf('(') + 1, conditionName.length() - 1);
	try {
	    return Integer.parseInt(intStr);
	} catch (Exception ex) {
	    throw new InvalidRulePartException(ex);
	}
    }
    
    public ArrayList<Double> parseRangeFunctionParameter(String rangeString) throws InvalidRulePartException {

	String rangeStr = rangeString.substring(rangeString.indexOf('(') + 1, rangeString.length() - 1);
	String[] rangeParts = rangeStr.split("\\.\\.");

	if ( rangeParts == null || rangeParts.length != 2 ) {
	    throw new InvalidRulePartException();
	}

	try {
	    double low = Double.parseDouble(rangeParts[0].trim());
	    double high = Double.parseDouble(rangeParts[1].trim());
	    ArrayList<Double> range = new ArrayList<Double>();
	    range.add(low);
	    range.add(high);
	    return range;
	} catch (Exception ex) {
	    throw new InvalidRulePartException(ex);
	}

    }
    
    @Override
    public void setNegated(boolean negated) {
	this.negated = negated;
    }
    
}
