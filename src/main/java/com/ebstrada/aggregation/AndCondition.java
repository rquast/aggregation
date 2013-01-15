package com.ebstrada.aggregation;

import java.util.Arrays;
import java.util.List;

import com.ebstrada.aggregation.exception.ErrorFlagException;
import com.ebstrada.aggregation.exception.InvalidRulePartException;

public class AndCondition {
    
    private static final String BLANK_CONDITION = "blank";
    
    private static final String STRING_LENGTH_EQUALS_FUNCTION_NAME = "strleneq";

    private List<String> conditionValues;
    
    public void parse(String conditionStr) {
	conditionValues = Arrays.asList(conditionStr.split("\\,"));
    }

    public boolean match(Selection selectionValues) throws InvalidRulePartException {
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
	    String selectionValue, int selectionCount) throws InvalidRulePartException {
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
	    String selectionValue) throws InvalidRulePartException {
	String conditionName = conditionValue.substring(2, conditionValue.length() - 2).toLowerCase();
	if (conditionName.equals(BLANK_CONDITION)) {
	    if ( selectionValue == null || selectionValue.length() <= 0 ) {
		return true;
	    }
	} else if (conditionName.startsWith(STRING_LENGTH_EQUALS_FUNCTION_NAME)) {
	    int stringLength = parseIntFunctionParameter(conditionName);
	    if (stringLength == selectionValue.length()) {
		return true;
	    }
	} else {
	    throw new InvalidRulePartException();
	}
	return false;
    }
    
    private int parseIntFunctionParameter(String conditionName) throws InvalidRulePartException {
	String intStr = conditionName.substring(conditionName.indexOf('(') + 1, conditionName.length() - 1);
	try {
	    return Integer.parseInt(intStr);
	} catch (Exception ex) {
	    throw new InvalidRulePartException(ex);
	}
    }

}
