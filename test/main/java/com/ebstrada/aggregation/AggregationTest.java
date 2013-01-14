package com.ebstrada.aggregation;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ebstrada.aggregation.exception.NoMatchException;

@RunWith(JUnit4.class)
public class AggregationTest {

    private Aggregation aggregation;
    private static final Rule DEFAULT_RULE = new Rule();
    private static final Selection DEFAULT_SELECTION = new Selection(new String[]{"C"});
    private static final double DEFAULT_AGGREGATE = -1.0d;

    @Before
    public void setUp() throws Exception {
	aggregation = new Aggregation();
	DEFAULT_RULE.parse("A?+1:B?+1:C?-1:+6");
    }

    @After
    public void tearDown() throws Exception {
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

}
