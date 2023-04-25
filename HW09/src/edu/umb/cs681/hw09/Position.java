package edu.umb.cs681.hw09;

import java.util.ArrayList;
import java.util.List;

public record Position(double latitude,double longitude,double altitude) {
	public List<Double> coordinate() {
		List<Double> coorddinates = new ArrayList<Double>();
		coorddinates.add(latitude);
		coorddinates.add(longitude);
		coorddinates.add(altitude);
		return coorddinates;
    }

    public Position change(double newLat,double newLon,double newAlt) {
        return new Position(newLat,newLon,newAlt);
    }

    public boolean higherAltThan(Position anotherPosition) {
        return altitude > anotherPosition.altitude();
    }

    public boolean lowerAltThan(Position anotherPosition) {
        return altitude < anotherPosition.altitude();
    }

    public boolean northOf(Position anotherPosition) {
        return latitude > anotherPosition.latitude();
    }

    public boolean southOf(Position anotherPosition) {
        return latitude < anotherPosition.latitude();
    }
}
