package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.ErrorFlagException;
import com.ebstrada.aggregation.exception.InvalidRulePartException;

@SuppressWarnings("serial")
public class OrCondition extends ArrayList<AndCondition> {
    
    private ArrayList<AndCondition> andConditions = new ArrayList<AndCondition>();

    public void parse(String conditionStr) throws InvalidRulePartException {
	for (String andConditionStr: conditionStr.split("\\|")) {
	    AndCondition andCondition = new AndCondition();
	    andCondition.parse(andConditionStr);
	    andConditions.add(andCondition);
	}
    }

    public boolean match(Selection selection) throws ErrorFlagException, InvalidRulePartException {
	for (AndCondition andCondition: andConditions) {
	    if (andCondition.match(selection)) {
		return true;
	    }
	}
	return false;
    }
    
}
