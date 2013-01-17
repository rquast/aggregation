package com.ebstrada.aggregation;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public class ConditionPartFactory {

    public static IConditionPart getConditionPart(String conditionPartStr) throws InvalidRulePartException {
	String str = conditionPartStr.trim();
	if ((str.startsWith("!!!") || str.startsWith("!!")) && str.endsWith("!!")) {
	    return FunctionFactory.getFunction(str);
	} else {
	    Value value = new Value();
	    value.parse(str);
	    return value;
	}
    }
    
}
