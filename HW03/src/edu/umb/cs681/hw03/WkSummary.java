package edu.umb.cs681.hw03;

public class WkSummary extends Summary {
	public WkSummary(double open, double high, double low, double close) {
        super(open, high, low, close);
    }
	
	public void update(DSummary dSummary) {
        setopen(dSummary.getopen());
        sethigh(dSummary.gethigh());
        setlow(dSummary.getlow());
        setclose(dSummary.getclose());
    }
}
