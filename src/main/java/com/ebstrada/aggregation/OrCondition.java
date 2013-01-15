package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.ErrorFlagException;
import com.ebstrada.aggregation.exception.InvalidRulePartException;

@SuppressWarnings("serial")
public class OrCondition extends ArrayList<AndCondition> {
    
    private ArrayList<AndCondition> andConditions = new ArrayList<AndCondition>();

    public void parse(String conditionStr) {
	for (String andValue: conditionStr.split("\\|")) {
	    AndCondition conditionValue = new AndCondition();
	    conditionValue.parse(andValue);
	    andConditions.add(conditionValue);
	}
    }

    public boolean match(Selection selection) throws ErrorFlagException, InvalidRulePartException {
	for (AndCondition andConditionValue: andConditions) {
	    if (andConditionValue.match(selection)) {
		return true;
	    }
	}
	return false;
    }
    
}
