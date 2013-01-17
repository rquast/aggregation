package com.ebstrada.aggregation;

import com.ebstrada.aggregation.exception.InvalidRulePartException;

public interface IConditionPart {
    
    public boolean isNegated();
    
    public void setNegated(boolean negated);

    public boolean match(Selection selectionValues);
    
    public void parse(String conditionPartStr) throws InvalidRulePartException;
    
}
