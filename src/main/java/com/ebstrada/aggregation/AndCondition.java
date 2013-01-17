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

    public boolean match(Selection selectionValues) {

	// check functions first
	for ( IConditionPart function: functions ) {
	    if ( function.match(selectionValues) == false ) {
		return false;
	    }
	}
	
	// check negations second
	for ( IConditionPart negation: negations ) {
	    if ( negation.match(selectionValues) == false ) {
		return false;
	    }
	}
	
	// check wildcards third
	for ( IConditionPart wildcard: wildcards ) {
	    if ( wildcard.match(selectionValues) == false ) {
		return false;
	    }
	}
	
	// check values third
	for ( IConditionPart value: values ) {
	    if ( value.match(selectionValues) == false ) {
		return false;
	    }
	}
	if ( selectionValues.size() != values.size() ) {
	    return false;
	}
	
	return true;

    }

}
