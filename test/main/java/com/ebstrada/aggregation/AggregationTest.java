package com.ebstrada.aggregation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ebstrada.aggregation.exception.FlagException;

@RunWith(JUnit4.class)
public class AggregationTest {

    private Aggregation aggregation;
    private static final Rule DEFAULT_RULE = new Rule();
    private static final Selection DEFAULT_SELECTION = new Selection(new String[]{"C"});
    private static final double DEFAULT_AGGREGATE = -1.0d;
    
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
	aggregation = new Aggregation();
	DEFAULT_RULE.parse("A?+1:B?+1:C?-1:+6");
    }

    @After
    public void tearDown() throws Exception {
	exception = ExpectedException.none();
	aggregation = null;
    }
    
    @Test
    public void testGetSetRule() {
	aggregation.setRule(DEFAULT_RULE);
	assertEquals(DEFAULT_RULE, aggregation.getRule());
    }
    
    @Test
    public void testGetSetSelection() {
	aggregation.setSelection(DEFAULT_SELECTION);
	assertSame(DEFAULT_SELECTION, aggregation.getSelection());
    }

    @Test
    public void testDefaultAggregate() throws Exception {
	aggregation.setRule(DEFAULT_RULE);
	aggregation.setSelection(DEFAULT_SELECTION);
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(DEFAULT_AGGREGATE, aggregate);
    }
    
    @Test
    public void testDefaultAggregate2() throws Exception {
	Rule rule = new Rule();
	rule.parse("a?1:0");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"A"}));
	double aggregate = aggregation.getAggregate();
	Assert.assertEquals(1.0d, aggregate);
    }
    
    @Test
    public void testUserSpecifiedFlagException() throws Exception {
	exception.expect(FlagException.class);
	Rule rule = new Rule();
	rule.parse("a?+1:b?!!error!!:!!blank!!?-6:2");
	aggregation.setRule(rule);
	aggregation.setSelection(new Selection(new String[]{"B"}));
	aggregation.getAggregate();
    }

}
