package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

@SuppressWarnings("serial")
public class RulePart extends ArrayList<OrCondition> {

    private Result result;
    
    public void parse(String ruleStr) throws InvalidRulePartException {
	clear();
	int ternaryOpOffset = ruleStr.indexOf('?');
	if (ternaryOpOffset > -1) {
	    OrCondition orCondition = new OrCondition();
	    if (ternaryOpOffset > 0) {
		String conditionStr = ruleStr.substring(0, ternaryOpOffset);
		orCondition.parse(conditionStr);
	    } else {
		orCondition.parse(null);
	    }
	    add(orCondition);
	}
	this.result = parseScoreString(ruleStr);
    }

    public boolean match(Selection selection) {
	if ( size() <= 0 ) {
	    return true;
	}
	for (OrCondition orCondition: this) {
	    if (orCondition.match(selection)) {
		return true;
	    } 
	}
	return false;
    }
    
    private Result parseScoreString(String string) throws InvalidRulePartException {
	int ternaryOpOffset = string.indexOf('?');
	String scoreString = string.substring(ternaryOpOffset + 1, string.length());
	double score;
	try {
	    score = Double.parseDouble(scoreString);
	} catch ( Exception ex ) {
	    InvalidRulePartException ire = new InvalidRulePartException(ex);
	    throw ire;
	}
	Result res = new Result();
	res.setScore(score);
	return res;
    }
    
    public Result getResult() {
	return this.result;
    }
    
}
