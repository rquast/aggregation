package com.ebstrada.aggregation;

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
    
    @Override
    public void setNegated(boolean negated) {
	this.negated = negated;
    }
    
}
