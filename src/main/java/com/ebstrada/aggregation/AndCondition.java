package com.ebstrada.aggregation;

import java.util.Arrays;
import java.util.List;

import com.ebstrada.aggregation.exception.FlagException;

public class AndCondition {

    private List<String> conditionValues;
    
    public void parse(String conditionStr) {
	conditionValues = Arrays.asList(conditionStr.split("\\,"));
    }

    public boolean match(Selection selectionValues) throws FlagException {
	for (String selectionValue: selectionValues) {
	    boolean match = false;
	    for (String conditionValue: conditionValues) {
		if (checkConditionValueForFlag(conditionValue, selectionValue)) {
		    match = true;
		    break;
		} else if (conditionValue.startsWith("!")) {
		    if (!(selectionValue.equalsIgnoreCase(conditionValue.replaceFirst("!", "")))) {
			match = true;
			break;
		    }
		} else {
		    if (selectionValue.equalsIgnoreCase(conditionValue)) {
			match = true;
			break;
		    }
		}
	    }
	    if (match == false) {
		return false;
	    }
	}
	return true;
    }

    private boolean checkConditionValueForFlag(String conditionValue,
	    String selectionValue) throws FlagException {
	
	if ( conditionValue.startsWith("!!") && conditionValue.endsWith("!!") ) {
	    if (conditionValue.equalsIgnoreCase("!!blank!!") 
		    && (selectionValue == null 
		    	|| selectionValue.trim().length() <= 0)) {
		return true;
	    }
	}
	
	return false;
    }

}
