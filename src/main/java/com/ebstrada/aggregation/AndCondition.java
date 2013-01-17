package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public class AndCondition {
    
    private ArrayList<IConditionPart> values = new ArrayList<IConditionPart>();
    
    private ArrayList<IConditionPart> wildcards = new ArrayList<IConditionPart>();
    
    private ArrayList<IConditionPart> negations = new ArrayList<IConditionPart>();
    
    private ArrayList<IConditionPart> functions = new ArrayList<IConditionPart>();
    
    public void parse(String conditionStr) throws InvalidRulePartException {
	String[] conditionParts = conditionStr.split("\\,");
	for ( String conditionPart: conditionParts ) {
	    IConditionPart conditionPartObj = ConditionPartFactory.getConditionPart(conditionPart);
	    if ( conditionPartObj instanceof Value ) {
		if ( conditionPartObj.isNegated() ) {
		    negations.add(conditionPartObj);
		} else {
		    values.add(conditionPartObj);
		}
	    } else if ( conditionPartObj instanceof AbstractFunction ) {
		functions.add(conditionPartObj);
	    } else if ( conditionPartObj instanceof Wildcard ) {
		wildcards.add(conditionPartObj);
	    }
	}
    }
    
    public boolean matchNonValues(Selection selectionValues) {
	for (IConditionPart function: functions) {
	    if (function.match(selectionValues) == false) {
		return false;
	    }
	}
	for (IConditionPart negation: negations) {
	    if (negation.match(selectionValues) == false) {
		return false;
	    }
	}
	for (IConditionPart wildcard: wildcards) {
	    if (wildcard.match(selectionValues) == false) {
		return false;
	    }
	}
	return true;
    }

    public boolean match(Selection selectionValues) {
	if (values.size() == selectionValues.size()) {
	    for (IConditionPart value: values) {
		if (value.match(selectionValues) == false) {
		    return false;
		}
	    }
	    return matchNonValues(selectionValues);
	} else if (wildcards.size() > 0 || negations.size() > 0 || functions.size() > 0) {
	    return matchNonValues(selectionValues);
	} else {
	    return false;
	}
    }

}
