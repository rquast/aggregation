package com.ebstrada.aggregation;

import java.util.ArrayList;

import com.ebstrada.aggregation.exception.ErrorFlagException;
import com.ebstrada.aggregation.exception.InvalidRulePartException;
import com.ebstrada.aggregation.exception.NoMatchException;

@SuppressWarnings("serial")
public class Rule extends ArrayList<RulePart> {

    public Rule() {
	super();
    }
    
    public Rule(String ruleStr) throws InvalidRulePartException {
	super();
	parse(ruleStr);
    }

    public void parse(String ruleStr) throws InvalidRulePartException {
	clear();
	String[] ruleStrArr = ruleStr.split("\\:");
	if (ruleStrArr != null && ruleStrArr.length > 0) {
	    for (String ruleStrPart : ruleStrArr) {
		RulePart rulePart = new RulePart();
		rulePart.parse(ruleStrPart);
		add(rulePart);
	    }
	} else {
	    RulePart rulePart = new RulePart();
	    rulePart.parse(ruleStr);
	    add(rulePart);
	}
	
    }
    
    public Result calculate(Selection selection) throws NoMatchException, ErrorFlagException {
	for (RulePart rulePart: this) {
	    if (rulePart.match(selection)) {
		return rulePart.getResult();
	    } 
	}
	throw new NoMatchException();
    }

}
