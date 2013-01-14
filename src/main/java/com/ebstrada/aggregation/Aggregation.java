package com.ebstrada.aggregation;

import com.ebstrada.aggregation.exception.FlagException;
import com.ebstrada.aggregation.exception.NoMatchException;

public class Aggregation {
    
    private Rule rule;
    private Selection selection;
    
    public void setRule(Rule rule) {
	this.rule = rule;
    }

    public void setSelection(Selection selection) {
	this.selection = selection;
    }

    public double getAggregate() throws NoMatchException, FlagException {
	return rule.calculate(selection).getScore();
    }

    public Rule getRule() {
	return this.rule;
    }

    public Selection getSelection() {
	return this.selection;
    }

}
