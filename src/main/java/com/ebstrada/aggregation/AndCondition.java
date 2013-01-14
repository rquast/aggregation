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
		if (conditionValue.startsWith("!")) {
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

}
