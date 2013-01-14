package com.ebstrada.aggregation;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Selection extends ArrayList<String> {

    public Selection(String[] selection) {
	addAll(Arrays.asList(selection));
    }

}
