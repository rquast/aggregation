package com.ebstrada.aggregation;

import java.util.Arrays;
import java.util.List;

import com.ebstrada.aggregation.exception.ErrorFlagException;

public class AndCondition {
    
    private static final String BLANK_CONDITION = "!!blank!!";

    private List<String> conditionValues;
    
    public void parse(String conditionStr) {
	conditionValues = Arrays.asList(conditionStr.split("\\,"));
    }

    public boolean match(Selection selectionValues) {
	if ( selectionValues == null || selectionValues.size() <= 0 ) {
	    for (String conditionValue: conditionValues) {
		if ( checkConditionValue(conditionValue, "", 0) ) {
		    return true;
		}
	    }
	    return false;
	} else {
	    for (String selectionValue: selectionValues) {
		boolean match = false;
		for (String conditionValue: conditionValues) {
		    if ( checkConditionValue(conditionValue, selectionValue, selectionValues.size()) ) {
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
	    String selectionValue, int selectionCount) {
	if ( conditionValue.startsWith("!!!") && conditionValue.endsWith("!!") ) { // negated user flags
	    if ( !checkConditionFlag(conditionValue.replaceFirst("!!!", "!!"), selectionValue) ) {
		return true;
	    }
	} else if ( conditionValue.startsWith("!!") && conditionValue.endsWith("!!") ) { // user flags
	    if ( checkConditionFlag(conditionValue, selectionValue) ) {
		return true;
	    }
	} else if (conditionValue.startsWith("!")) { // negations
	    if (!(selectionValue.equalsIgnoreCase(conditionValue.replaceFirst("!", "")))) {
		return true;
	    }
	} else if (conditionValue.startsWith("'")) { // wildcards
	    int partCount = 0;
	    for (int i = 0; i < conditionValue.length(); ++i) {
		if (conditionValue.charAt(i) == '\'') {
		    ++partCount;
		}
	    }
	    if (partCount > 0) {
		if (selectionCount >= partCount) {
		    return true;
		}
	    }
	} else { // standard match
	    if (selectionValue.equalsIgnoreCase(conditionValue)) {
		return true;
	    }
	}
	return false;
    }

    private boolean checkConditionFlag(String conditionValue,
	    String selectionValue) {
	if (conditionValue.equalsIgnoreCase(BLANK_CONDITION)) {
	    if ( selectionValue == null || selectionValue.length() <= 0 ) {
		return true;
	    }
	}
	return false;
    }

}
