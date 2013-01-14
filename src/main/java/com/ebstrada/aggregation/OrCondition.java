package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.FlagException;

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

    public boolean match(Selection selection) throws FlagException {
	for (AndCondition andConditionValue: andConditions) {
	    if ( andConditionValue.match(selection) == false ) {
		return false;
	    }
	}
	return true;
    }
    
}
