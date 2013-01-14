package com.ebstrada.aggregation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ebstrada.aggregation.exception.FlagException;
import com.ebstrada.aggregation.exception.InvalidRulePartException;

@RunWith(JUnit4.class)
public class AndConditionTest {
    
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @After
    public void tearDown() throws Exception {
	exception = ExpectedException.none();
    }
    
    @Test
    public void testParse1() throws Exception {
	AndCondition andCondition = new AndCondition();
	andCondition.parse("!C");
    }
    
    @Test
    public void testMatchTrue1() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	andCondition.parse("A");
	assertTrue(andCondition.match(selection));
    }
    
    @Test
    public void testMatchFalse1() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"AB"});
	andCondition.parse("A");
	assertFalse(andCondition.match(selection));
    }
    
    @Test
    public void testMatchFalse2() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"A", "B"});
	andCondition.parse("A");
	assertFalse(andCondition.match(selection));
    }
    
    @Test
    public void testMatchFalse3() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	andCondition.parse("B");
	assertFalse(andCondition.match(selection));
    }
    
    // test for negations
    @Test
    public void testNegationIsTrue() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"B"});
	andCondition.parse("!A");
	assertTrue(andCondition.match(selection));
    }
    
    @Test
    public void testNegationIsFalse() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"A"});
	andCondition.parse("!A");
	assertFalse(andCondition.match(selection));
    }
    
    @Test
    public void testTrueAndNegationTrue() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"B", "A", "D"});
	andCondition.parse("B,!CA");
	assertTrue(andCondition.match(selection));
    }
    
    @Test
    public void testCheckConditionValueForFlag() throws Exception {
	AndCondition andCondition = new AndCondition();
	Selection selection = new Selection(new String[]{"B"});
	andCondition.parse("!!blank!!");
	assertFalse(andCondition.match(selection));
    }
    
}
