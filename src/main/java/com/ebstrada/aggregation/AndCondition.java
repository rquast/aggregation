package com.ebstrada.aggregation;

import java.util.Arrays;
import java.util.List;

import com.ebstrada.aggregation.exception.FlagException;

public class AndCondition {
    
    private static final String BLANK_CONDITION = "!!blank!!";

    private List<String> conditionValues;
    
    public void parse(String conditionStr) {
	conditionValues = Arrays.asList(conditionStr.split("\\,"));
    }

    public boolean match(Selection selectionValues) throws FlagException {
	if ( selectionValues == null || selectionValues.size() <= 0 ) {
	    for (String conditionValue: conditionValues) {
		if ( checkConditionValue(conditionValue, "") ) {
		    return true;
		}
	    }
	    return false;
	} else {
	    for (String selectionValue: selectionValues) {
		boolean match = false;
		for (String conditionValue: conditionValues) {
		    if ( checkConditionValue(conditionValue, selectionValue) ) {
			match = true;
			break;
		    }
		}
		if ( match == false ) {
		    return false;
		}
	    }
	    return true;
	}
    }
    
    private boolean checkConditionValue(String conditionValue, 
	    String selectionValue) throws FlagException {
	if ( conditionValue.startsWith("!!") && conditionValue.endsWith("!!") ) {
	    if ( checkConditionFlag(conditionValue, selectionValue) ) {
		return true;
	    }
	} else if (conditionValue.startsWith("!")) {
	    if (!(selectionValue.equalsIgnoreCase(conditionValue.replaceFirst("!", "")))) {
		return true;
	    }
	} else {
	    if (selectionValue.equalsIgnoreCase(conditionValue)) {
		return true;
	    }
	}
	return false;
    }

    private boolean checkConditionFlag(String conditionValue,
	    String selectionValue) throws FlagException {
	if (conditionValue.equalsIgnoreCase(BLANK_CONDITION)) {
	    if ( selectionValue == null || selectionValue.length() <= 0 ) {
		return true;
	    }
	}
	return false;
    }

}
