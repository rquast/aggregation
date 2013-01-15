package com.ebstrada.aggregation;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ebstrada.aggregation.exception.FlagException;
import com.ebstrada.aggregation.exception.NoMatchException;

@RunWith(JUnit4.class)
public class AggregationTest {

    private Aggregation aggregation;
    
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
	aggregation = new Aggregation();
    }

    @After
    public void tearDown() throws Exception {
	exception = ExpectedException.none();
	aggregation = null;
    }
    
    @Test
    public void testDefaultAggregate() throws Exception {
	Rule rule = new Rule();
	rule.parse("A?+1");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"A"}));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(1.0d, aggregate);
    }
    
    @Test
    public void testComplexAggregate1() throws Exception {
	Rule rule = new Rule();
	rule.parse("a?1:C|b,A|!!blank!!?-6:2");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(null));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(-6.0d, aggregate);
    }
    
    @Test
    public void testComplexAggregate2() throws Exception {
	Rule rule = new Rule();
	rule.parse("!!blank!!|a?1:0");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(null));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(1.0d, aggregate);
    }
    
    @Test
    public void testUserSpecifiedFlagException1() throws Exception {
	exception.expect(FlagException.class);
	Rule rule = new Rule();
	rule.parse("a?+1:b?!!error!!:!!blank!!?-6:2");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"B"}));
	aggregation.getAggregate();
    }
    
    @Test
    public void testUserSpecifiedFlagException2() throws Exception {
	exception.expect(FlagException.class);
	Rule rule = new Rule();
	rule.parse("!!blank!!?!!error!!");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(null));
	aggregation.getAggregate();
    }
    
    @Test
    public void testAndOrCombinations1() throws Exception {
	Rule rule = new Rule();
	rule.parse("A,B?+1:B,C?+2:A,C|C,D?+3:+0");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"C", "D"}));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(3.0d, aggregate);
    }
    
    @Test
    public void testWildCards1() throws Exception {
	Rule rule = new Rule();
	rule.parse("'''?+3:''?+2:C,D?+3:+0");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"C", "D"}));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(2.0d, aggregate);
    }
    
    @Test
    public void testWildCards2() throws Exception {
	exception.expect(NoMatchException.class);
	Rule rule = new Rule();
	rule.parse("'''?+3");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"C", "D"}));
	aggregation.getAggregate();
    }

    @Test
    public void testWildCards3() throws Exception {
	exception.expect(NoMatchException.class);
	Rule rule = new Rule();
	rule.parse("'''?+3");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(null));
	aggregation.getAggregate();
    }
    
    @Test
    public void testWildCards4() throws Exception {
	exception.expect(NoMatchException.class);
	Rule rule = new Rule();
	rule.parse("'?+3");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{}));
	aggregation.getAggregate();
    }
    
    @Test
    public void testThrowFlagIfMoreThanOneSelected() throws Exception {
	exception.expect(FlagException.class);
	Rule rule = new Rule();
	rule.parse("''?!!error!!:B?+1:0");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"A", "B"}));
	aggregation.getAggregate();
    }
    
}
