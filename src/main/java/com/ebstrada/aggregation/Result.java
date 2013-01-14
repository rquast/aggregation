package com.ebstrada.aggregation;

import com.ebstrada.aggregation.exception.FlagException;

public class Result {
    
    private double score;
    
    private FlagException flagException;

    public double getScore() throws FlagException {
	if ( this.flagException != null ) {
	    throw this.flagException;
	}
        return score;
    }

    public void setScore(double score) {
	this.score = score;
    }

    public void setException(FlagException flagException) {
	this.flagException = flagException;
    }

}
