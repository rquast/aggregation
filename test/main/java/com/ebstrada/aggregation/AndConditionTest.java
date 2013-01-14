package com.ebstrada.aggregation;

import static org.junit.Assert.*;

import org.junit.Test;

public class AndConditionTest {
    
    @Test
    public void testParse1() throws Exception {
	AndCondition conditionValue = new AndCondition();
	conditionValue.parse("!C");
    }
    
    @Test
    public void testMatchTrue1() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	conditionValue.parse("A");
	assertTrue(conditionValue.match(selection));
    }
    
    @Test
    public void testMatchFalse1() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"AB"});
	conditionValue.parse("A");
	assertFalse(conditionValue.match(selection));
    }
    
    @Test
    public void testMatchFalse2() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"A", "B"});
	conditionValue.parse("A");
	assertFalse(conditionValue.match(selection));
    }
    
    @Test
    public void testMatchFalse3() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	conditionValue.parse("B");
	assertFalse(conditionValue.match(selection));
    }
    
    // test for negations
    @Test
    public void testNegationIsTrue() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"B"});
	conditionValue.parse("!A");
	assertTrue(conditionValue.match(selection));
    }
    
    @Test
    public void testNegationIsFalse() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	conditionValue.parse("!A");
	assertFalse(conditionValue.match(selection));
    }
    
    @Test
    public void testTrueAndNegationTrue() throws Exception {
	AndCondition conditionValue = new AndCondition();
	Selection selection = new Selection(new String[]{"B", "A", "D"});
	conditionValue.parse("B,!CA");
	assertTrue(conditionValue.match(selection));
    }
    
}
